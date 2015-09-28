package sg.atom.corex.world.map;

import java.util.LinkedList;
import java.util.List;
import sg.atom.corex.world.terrain.TerrainType;
import sg.atom.corex.world.tile.TileLayerDefinition;
import sg.atom.corex.world.tile.TileSetDefinition;

/**
 * General MapStructure, interchangable with Guava's Table.
 *
 * @author cuong.nguyen
 *
 */
public class MapStructure {

    String name;
    String originPath;
    /**
     * Width of the map.
     */
    public int width;
    /**
     * Height of the map.
     */
    public int height;
    /**
     * List of tilesets that this map contains.
     */
    public int tileWidth;
    public int tileHeight;

    public List<TileSetDefinition> tilesets;
    /**
     * List of layers this map contains.
     */
    public List<TileLayerDefinition> layers;
    public List<MapObjectGroup> objectGroups;

    /**
     * Constructor.
     *
     * @param w the width of the map
     * @param h the height of the map.
     */
    public MapStructure(final int w, final int h) {
        width = w;
        height = h;
        tilesets = new LinkedList<TileSetDefinition>();
        layers = new LinkedList<TileLayerDefinition>();
        objectGroups = new LinkedList<MapObjectGroup>();
    }

    /**
     * Adds a new tileset to the map.
     *
     * @param set new tileset
     */
    public void addTileset(final TileSetDefinition set) {
        tilesets.add(set);
    }

    /**
     * Adds a new layer to the map.
     *
     * @param layer new layer
     */
    public void addLayer(final TileLayerDefinition layer) {
        layer.setMap(this);
        layers.add(layer);
    }

    /**
     * Returns a list of the tilesets this map contains.
     *
     * @return a list of the tilesets this map contains.
     */
    public List<TileSetDefinition> getTilesets() {
        return tilesets;
    }

    /**
     * Returns a list of the layers this map contains.
     *
     * @return a list of the layers this map contains.
     */
    public List<TileLayerDefinition> getLayers() {
        return layers;
    }

    /**
     * Return true if the layer with given name exists.
     *
     * @param layername the layer name
     * @return true if it exists.
     */
    public boolean hasLayer(final String layername) {
        return getLayer(layername) != null;
    }

    /**
     * Returns the layer whose name is layer name or null.
     *
     * @param layername the layer name
     * @return the layer object or null if it doesnt' exists
     */
    public TileLayerDefinition getLayer(final String layername) {
        for (final TileLayerDefinition layer : layers) {
            if (layername.equals(layer.getName())) {
                return layer;
            }
        }

        return null;
    }

    /**
     * Build all layers data.
     */
    public void build() {
        for (final TileLayerDefinition layer : layers) {
            layer.build();
        }
    }

    public void dispose() {
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public void addObjectGroup(MapObjectGroup objectGroup) {
        this.objectGroups.add(objectGroup);
    }

    public List<MapObjectGroup> getObjectGroups() {
        return objectGroups;
    }

    public MapObjectGroup getObjectGroupByName(String name) {
        for (MapObjectGroup mapObjectGroup : objectGroups) {
            if (mapObjectGroup.getName().equals(name)) {
                return mapObjectGroup;
            }
        }
        return null;
    }
}
