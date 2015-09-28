package sg.atom.corex.world.tiled;

import com.google.common.io.Files;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import sg.atom.corex.world.tile.TileLayerDefinition;
import sg.atom.corex.world.map.MapStructure;
import sg.atom.corex.world.tile.TileLayerConverter;
import sg.atom.corex.world.tile.TileSetDefinition;

/**
 *
 * @author cuong.nguyen
 */
public class TiledMap3D extends Node {

    private final Logger logger = Logger.getLogger(TiledMap3D.class);
    protected ArrayList<Texture> textures;
    protected MapStructure map;
    protected Node mapNode;
    protected Node mapObjectNode;
    protected Node debugNode;
    protected AssetManager assetManager;
    protected HashMap<TileSetDefinition, Material> tileSetMats;
    protected float scaleTileRatio = 4;
    protected float realLayerGap = 0.01f;
    protected Node lookupModels;
    protected String tile3dModelPath;
    protected HashMap<String, TileLayerConverter> layerConverters;

    public TiledMap3D(AssetManager assetManager, MapStructure map) {
        super();
        this.assetManager = assetManager;
        this.map = map;
        //We should use a Pool?
        textures = new ArrayList<Texture>(10);
        tileSetMats = new HashMap<TileSetDefinition, Material>();
    }

    public void loadTextures(String mapDir) {
        //Load the textures first
        List<TileSetDefinition> tilesets = map.getTilesets();

        //assetManager.registerLocator(mapDir, FileLocator.class);
        for (TileSetDefinition set : tilesets) {

            String source = set.getSource();
            source = Files.simplifyPath(mapDir + "/" + source);
            source = source.replace("\\", "/");

            Texture loadedTexture = assetManager.loadTexture(source);
            //loadTexture.setWrap(Texture.WrapMode.Repeat);
            textures.add(loadedTexture);
            logger.info("Try to load texture: " + source);

        }
    }

    public Node createMap() {

        if (getTile3dModelPath() != null && lookupModels == null) {
            lookupModels = (Node) assetManager.loadModel(getTile3dModelPath());
        }
        if (isBatched()) {
            mapNode = new BatchNode("Batch");
        } else {
            mapNode = new Node("MapNode");
        }

        mapObjectNode = new Node("MapObjectNode");
        debugNode = new Node("DebugNode");
        //Load the layers
        List<TileLayerDefinition> layers = map.getLayers();

        for (final TileLayerDefinition layer : layers) {
            logger.info("LAYER name: " + layer.getName());
            int layerWidth = layer.getWidth();
            int layerHeight = layer.getHeight();

            if (!isIgnored(layer.getName())) {
                if (hasCustomConverter(layer.getName())) {
                    layerConverters.get(layer.getName()).convert(this, layer, mapNode, mapObjectNode);
                } else {
                    convertDefault(layer);
                }
            }
            mapNode.setQueueBucket(RenderQueue.Bucket.Transparent);
            if (isBatched()) {
                ((BatchNode) mapNode).batch();
            }
        }
        this.detachAllChildren();
        this.attachChild(mapNode);
        this.attachChild(mapObjectNode);
        this.attachChild(debugNode);

        mapNode.setShadowMode(RenderQueue.ShadowMode.Receive);
        return mapNode;
    }

    protected boolean isBatched() {
        return true;
    }

    protected List<String> getIgnoredLayers() {
        return null;
    }

    protected boolean isIgnored(String layerName) {
//        for (String ignoredLayerName : getIgnoredLayers()) {
//            if (ignoredLayerName.equalsIgnoreCase(layerName)) {
//                return true;
//            }
//        }
        return false;
    }

    protected boolean isIgnored(TileLayerDefinition layer) {
        return isIgnored(layer.getName());
    }

    // Transform : index -> TileSetDefinition
    public TileSetDefinition findTileSet(int index) {

        final List<TileSetDefinition> tilesets = map.getTilesets();
        for (final TileSetDefinition set : tilesets) {

            int first = set.getFirstGid();

            if (index >= first && index <= first + set.numOfTiles) {
                return set;
            }
        }
        return null;
    }

    // TRANSFORM ---------------------------------------------------------------
    public Vector3f transformToPosition(int x, int y, TileLayerDefinition layer) {
        List<TileLayerDefinition> layers = map.getLayers();
        int orderOfLayer = layers.lastIndexOf(layer);
        return new Vector3f(x * scaleTileRatio, (y + 1) * scaleTileRatio, realLayerGap * orderOfLayer);
    }
    // Mapping: TileSetDefinition + Position -> Spatial

    public Vector4f transformToUV(TileSetDefinition set, int gid) {
        int lgid = (gid - set.getFirstGid());
        int cx = lgid % set.numX;
        int cy = lgid / set.numX;

        float bleedEdge = 0.0004f;
        float u = ((float) cx) / set.numX + bleedEdge;
        float v = 1 - ((float) (cy + 1)) / set.numY + bleedEdge;
        float u1 = ((float) (cx + 1)) / set.numX - bleedEdge;
        float v1 = 1 - ((float) cy) / set.numY - bleedEdge;

        return new Vector4f(u, v, u1, v1);
    }

    public Material transformToMaterial(TileSetDefinition set) {
        List<TileSetDefinition> tilesets = map.getTilesets();
        Material mat;
        if (tileSetMats.get(set) == null) {
            int tileSetIndex = tilesets.indexOf(set);
            Texture tileTex = textures.get(tileSetIndex);
            mat = getTileMat(tileTex);
            tileSetMats.put(set, mat);
        } else {
            mat = tileSetMats.get(set);
        }

        return mat;
    }

    public Spatial transformToSpatial(Material mat, Vector3f pos, Vector4f uv) {
        Quad quad = new Quad(scaleTileRatio, scaleTileRatio);

        float u = uv.x;
        float v = uv.y;
        float u1 = uv.z;
        float v1 = uv.w;

        // Set the UV
        FloatBuffer floatBuffer;
        boolean flipCoords = false;
        if (flipCoords) {
            floatBuffer = BufferUtils.createFloatBuffer(u, v1, u1, v1, u1, v, u, v);
        } else {
            floatBuffer = BufferUtils.createFloatBuffer(u, v, u1, v, u1, v1, u, v1);
        }

        quad.setBuffer(VertexBuffer.Type.TexCoord, 2, floatBuffer);

        Geometry tileGeo = new Geometry("Tile" + pos.y + " " + pos.x, quad);
        tileGeo.setQueueBucket(RenderQueue.Bucket.Transparent);
        tileGeo.setMaterial(mat);
        tileGeo.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI * 3 / 2, new Vector3f(1, 0, 0)));
        tileGeo.setLocalTranslation(pos.x, pos.z, pos.y);

        return tileGeo;
    }

    public Material getTileMat(Texture tileTex) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mat.getAdditionalRenderState().setAlphaFallOff(0.1f);
        mat.getAdditionalRenderState().setPolyOffset(0.01f, 0.01f);
        mat.setTexture("ColorMap", tileTex);
        return mat;
    }

    public MapStructure getMap() {
        return map;
    }

    public Node getMapNode() {
        return mapNode;
    }

    public ArrayList<Texture> getTextures() {
        return textures;
    }

    public void setScaleTileRatio(float realTileSize) {
        this.scaleTileRatio = realTileSize;
    }

    public float getScaleTileRatio() {
        return scaleTileRatio;
    }

    public void setTile3dModelPath(String tile3dModelPath) {
        this.tile3dModelPath = tile3dModelPath;
    }

    public String getTile3dModelPath() {
        return tile3dModelPath;
    }

    public Node getLookupModels() {
        return lookupModels;
    }

    public void setLookupModels(Node lookupModels) {
        this.lookupModels = lookupModels;
    }

    public Spatial transformFromLookup(TileSetDefinition set, int gid) {
        return new Node();
    }

    private void convertDefault(final TileLayerDefinition layer) {
        int layerWidth = layer.getWidth();
        int layerHeight = layer.getHeight();
        for (int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < layerWidth; x++) {
                //FIXME: Tile is indicate by an Integer index!
                final int gid = layer.getTileAt(x, y);

                if (gid != 0) {
                    TileSetDefinition set = findTileSet(gid);

                    if (set != null) {

                        Vector3f pos = transformToPosition(x, y, layer);
                        Spatial tileGeo = null;
                        if (lookupModels == null) {
                            Vector4f uv = transformToUV(set, gid);
                            Material mat = transformToMaterial(set);
                            tileGeo = transformToSpatial(mat, pos, uv);
                        } else {
                            tileGeo = transformFromLookup(set, gid);
                        }
//                                logger.info("Add a tile at" + x + " " + y);
                        mapNode.attachChild(tileGeo);
                    } else {
                        logger.info("Set is null!");
                    }
                }
            }
        }
        // FIXME: Other option beside of Batch!
        // Batch it
    }

    public boolean hasCustomConverter(String name) {
        return layerConverters.containsKey(name);
    }

    public HashMap<String, TileLayerConverter> getLayerConverters() {
        return layerConverters;
    }

    public void setLayerConverters(HashMap<String, TileLayerConverter> layerConverters) {
        this.layerConverters = layerConverters;
    }

    public Node getDebugNode() {
        return debugNode;
    }

    public float getScaleObjectRatio() {
        return scaleTileRatio / map.getTileWidth();
    }

    public Vector3f getTileWorldPos(int x, int y) {
        return new Vector3f(getScaleTileRatio() * (x + 0.5f), 0, getScaleTileRatio() * (y + 0.5f));
    }
}
