package sg.atom.corex.world.tiled;

import sg.atom.corex.world.terrain.TerrainType;
import sg.atom.corex.world.tile.TileSetDefinition;
import sg.atom.corex.world.map.MapStructure;
import sg.atom.corex.world.tile.TileLayerDefinition;
import com.google.common.io.BaseEncoding;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.math.Vector2f;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sg.atom.core.io.xml.DomUtils;
import sg.atom.corex.world.map.MapObjectGroup;
import sg.atom.corex.world.map.shape.EllipseMapObject;
import sg.atom.corex.world.map.shape.PolylineMapObject;
import sg.atom.corex.world.map.shape.RectangleMapObject;
import sg.atom.corex.world.tile.TileDefinition;

/**
 * Loads a TMX file.
 *
 * @author miguel
 *
 */
public class TMXLoader implements AssetLoader {

    private static Logger logger = Logger.getLogger(TMXLoader.class.getName());
    private MapStructure mapStructure;
    private String xmlPath;

    private static String makeUrl(final String filename) throws MalformedURLException {
        final String url;
        if ((filename.indexOf("://") > -1) || filename.startsWith("file:")) {
            url = filename;
        } else {
            url = (new File(filename)).toURI().toURL().toString();
        }
        return url;
    }

    private TileSetDefinition readTileSet(final Node domNode) throws Exception {
        final String name = DomUtils.getAttributeValue(domNode, "name");
        final int firstGid = DomUtils.getAttribute(domNode, "firstgid", 1);

        final TileSetDefinition set = new TileSetDefinition(name, firstGid);
        set.tileWidth = DomUtils.getAttribute(domNode, "tilewidth", 32);
        set.tileHeight = DomUtils.getAttribute(domNode, "tileheight", 32);
        final boolean hasTilesetImage = false;
        final NodeList children = domNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);

            if (child.getNodeName().equalsIgnoreCase("image")) {
                if (hasTilesetImage) {
                    continue;
                }

                set.setSource(DomUtils.getAttributeValue(child, "source"));
                set.sourceWidth = DomUtils.getAttribute(child, "width", 100);
                set.sourceHeight = DomUtils.getAttribute(child, "height", 100);
                set.numY = set.sourceHeight / set.tileHeight;
                set.numX = set.sourceWidth / set.tileWidth;
                set.numOfTiles = set.numX * set.numY;

            } else if ("terraintypes".equals(child.getNodeName())) {
                set.addTerrainTypes(readTerrainTypes(child));
            } else if ("tile".equals(child.getNodeName())) {
                set.addLayer(readTileDef(child));
            }
        }

        return set;
    }

    /**
     * Reads properties from amongst the given children. When a "properties"
     * element is encountered, it recursively calls itself with the children of
     * this node. This function ensures backward compatibility with tmx version
     * 0.99a.
     *
     * @param children the children amongst which to find properties
     * @param props the properties object to set the properties of
     */
    private static void readProperties(final NodeList children, final Properties props) {
        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);
            if ("property".equalsIgnoreCase(child.getNodeName())) {
                props.setProperty(DomUtils.getAttributeValue(child, "name"),
                        DomUtils.getAttributeValue(child, "value"));
            } else if ("properties".equals(child.getNodeName())) {
                readProperties(child.getChildNodes(), props);
            }
        }
    }

    /**
     * Loads a map layer from a layer node.
     *
     * @param domNode
     * @return the layer definition for the node
     * @throws Exception
     */
    private TileLayerDefinition readLayer(final Node domNode) throws Exception {
        final int layerWidth = DomUtils.getAttribute(domNode, "width", mapStructure.width);
        final int layerHeight = DomUtils.getAttribute(domNode, "height", mapStructure.height);
        final String layerName = DomUtils.getAttributeValue(domNode, "name");
        final TileLayerDefinition layer = new TileLayerDefinition(layerWidth, layerHeight, layerName);

        final int offsetX = DomUtils.getAttribute(domNode, "x", 0);
        final int offsetY = DomUtils.getAttribute(domNode, "y", 0);

        if ((offsetX != 0) || (offsetY != 0)) {
            System.err.println("Severe error: maps has offset displacement");
        }

        for (Node child = domNode.getFirstChild(); child != null; child = child.getNextSibling()) {
            if ("data".equalsIgnoreCase(child.getNodeName())) {
                final String encoding = DomUtils.getAttributeValue(child, "encoding");

                if ((encoding != null) && "base64".equalsIgnoreCase(encoding)) {
                    final Node cdata = child.getFirstChild();
                    if (cdata != null) {
                        final char[] enc = cdata.getNodeValue().trim().toCharArray();
                        final byte[] dec = BaseEncoding.base64().decode(new String(enc));
                        final ByteArrayInputStream bais = new ByteArrayInputStream(
                                dec);
                        InputStream is;

                        final String comp = DomUtils.getAttributeValue(child, "compression");

                        if ("gzip".equalsIgnoreCase(comp)) {
                            is = new GZIPInputStream(bais);
                        } else if ("zlib".equalsIgnoreCase(comp)) {
                            is = new InflaterInputStream(bais);
                        } else {
                            is = bais;
                        }

                        final byte[] raw = layer.exposeRaw();
                        int offset = 0;

                        while (offset != raw.length) {
                            offset += is.read(raw, offset, raw.length - offset);
                        }

                        bais.close();
                    }
                }
            }
        }

        return layer;
    }

    private void buildMap(final Document doc) throws Exception {
        Node mapNode = doc.getDocumentElement();

        if (!"map".equals(mapNode.getNodeName())) {
            throw new Exception("Not a valid tmx map file.");
        }

        // Get the map dimensions and create the map
        final int mapWidth = DomUtils.getAttribute(mapNode, "width", 0);
        final int mapHeight = DomUtils.getAttribute(mapNode, "height", 0);

        if ((mapWidth > 0) && (mapHeight > 0)) {
            mapStructure = new MapStructure(mapWidth, mapHeight);
        }

        if (mapStructure == null) {
            throw new Exception("Couldn't locate map dimensions.");
        }
        mapStructure.tileWidth = DomUtils.getAttribute(mapNode, "tilewidth", 32);
        mapStructure.tileHeight = DomUtils.getAttribute(mapNode, "tileheight", 32);
        // Load the tilesets, properties, layers and objectgroups
        for (Node sibs = mapNode.getFirstChild(); sibs != null; sibs = sibs.getNextSibling()) {
            if ("tileset".equals(sibs.getNodeName())) {
                mapStructure.addTileset(readTileSet(sibs));
            } else if ("layer".equals(sibs.getNodeName())) {
                mapStructure.addLayer(readLayer(sibs));
            } else if ("objectgroup".equals(sibs.getNodeName())) {
                mapStructure.addObjectGroup(readObjectGroup(sibs));
            }
        }
    }

    private MapStructure readFromInputStream(final InputStream in) throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setExpandEntityReferences(false);

            // Xerces normally tries to retrieve the dtd, even when it's not used - and 
            // dies if it fails. 
            try {
                factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            } catch (final IllegalArgumentException e) {
                //logger.warning(e, e);
            }

            final DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(in, xmlPath);
        } catch (final SAXException e) {
            e.printStackTrace();
            throw new Exception("Error while parsing map file: " + e.toString());
        }

        buildMap(doc);
        return mapStructure;
    }

    public MapStructure readMap(final String filename) throws Exception {
        xmlPath = filename.substring(0,
                filename.lastIndexOf(File.separatorChar) + 1);

        InputStream is = getClass().getClassLoader().getResourceAsStream(
                filename);

        if (is == null) {
            final String xmlFile = makeUrl(filename);
            // xmlPath = makeUrl(xmlPath);

            final URL url = new URL(xmlFile);
            is = url.openStream();
        }

        // Wrap with GZIP decoder for .tmx.gz files
        if (filename.endsWith(".gz")) {
            is = new GZIPInputStream(is);
        }

        return readMap(is);
    }

    public MapStructure readMap(InputStream is) throws Exception {
        return readFromInputStream(is);
    }

    public static MapStructure load(final String filename) throws Exception {
        return new TMXLoader().readMap(filename);
    }

    public Object load(AssetInfo assetInfo) throws IOException {
        try {
            return readMap(assetInfo.openStream());
        } catch (Exception ex) {
            Logger.getLogger(TMXLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Can not load TMX file" + assetInfo.getKey().getName());
        }
    }

    private TerrainType readTerrainTypes(Node child) {
        return null;
    }

    private MapObjectGroup readObjectGroup(Node domNode) {
        final int layerWidth = DomUtils.getAttribute(domNode, "width", 0);
        final int layerHeight = DomUtils.getAttribute(domNode, "height", 0);
        final String layerName = DomUtils.getAttributeValue(domNode, "name");

        final MapObjectGroup layer = new MapObjectGroup(layerWidth, layerHeight, layerName);

        for (Node child = domNode.getFirstChild(); child != null; child = child.getNextSibling()) {
            final int x = DomUtils.getAttribute(child, "x", 0);
            final int y = DomUtils.getAttribute(child, "y", 0);
            if ("object".equalsIgnoreCase(child.getNodeName())) {
                if (child.hasChildNodes()) {
                    for (Node objectNodeData = child.getFirstChild(); objectNodeData != null; objectNodeData = objectNodeData.getNextSibling()) {
                        if ("ellipse".equalsIgnoreCase(objectNodeData.getNodeName())) {
                            final int width = DomUtils.getAttribute(child, "width", 0);
                            final int height = DomUtils.getAttribute(child, "height", 0);
                            EllipseMapObject ellipseMapObject = new EllipseMapObject(x, y, width, height);
                            layer.addObject(ellipseMapObject);
//                            logger.info(ellipseMapObject.toString());
                        } else if ("polyline".equalsIgnoreCase(objectNodeData.getNodeName())) {
                            int[] listCoord = DomUtils.getAttributeIntArray(objectNodeData, "points");
                            List<Vector2f> points = readPoints(listCoord);
                            PolylineMapObject polylineMapObject = new PolylineMapObject(x, y, points);
                            layer.addObject(polylineMapObject);
//                            logger.info(polylineMapObject.toString());
                        } else if ("polygon".equalsIgnoreCase(objectNodeData.getNodeName())) {
                            int[] listCoord = DomUtils.getAttributeIntArray(objectNodeData, "points");
                            List<Vector2f> points = readPoints(listCoord);
                            PolylineMapObject polylineMapObject = new PolylineMapObject(x, y, points);
                            layer.addObject(polylineMapObject);
//                            logger.info(polylineMapObject.toString());
                        } else {
                            logger.log(Level.INFO, "ObjectGroups has unsupported object type!" + objectNodeData.getNodeName() + objectNodeData.getNodeValue());
                        }
                    }
                } else {
                    final int width = DomUtils.getAttribute(child, "width", 0);
                    final int height = DomUtils.getAttribute(child, "height", 0);
                    RectangleMapObject rectangleMapObject = new RectangleMapObject(x, y, width, height);
                    layer.addObject(rectangleMapObject);
                    logger.info(rectangleMapObject.toString());
                }
            }
        }

        return layer;
    }

    private TileDefinition readTileDef(Node child) {
        return new TileDefinition();
    }

    private List<Vector2f> readPoints(int[] listCoord) {
        ArrayList<Vector2f> result = new ArrayList<Vector2f>(listCoord.length / 2);
        for (int i = 0; i < listCoord.length / 2; i++) {
            result.add(new Vector2f(listCoord[i * 2], listCoord[i * 2 + 1]));
        }
        return result;
    }
}
