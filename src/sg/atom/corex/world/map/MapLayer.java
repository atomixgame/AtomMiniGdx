/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

/**
 *
 * @author cuongnguyen
 */
public abstract class MapLayer {

    /**
     * Width of the layer that SHOULD be the same that the width of the map.
     */
    protected int width;
    /**
     * Height of the layer that SHOULD be the same that the height of the map.
     */
    protected int height;
    protected String name;

    public MapLayer(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    /**
     * Returns the width of the layer.
     *
     * @return the layer's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the layer.
     *
     * @return the layer's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the name of the layer.
     *
     * @param layerName the name of the layer
     */
    public void setName(final String layerName) {
        name = layerName;
    }

    /**
     * Returns the name of the layer.
     *
     * @return the layer's name
     */
    public String getName() {
        return name;
    }
}
