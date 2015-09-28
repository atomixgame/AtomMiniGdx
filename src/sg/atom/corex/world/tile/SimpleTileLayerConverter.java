/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.tile;

import com.jme3.scene.Node;
import sg.atom.corex.world.tiled.TiledMap3D;

/**
 *
 * @author cuongnguyen
 */
public class SimpleTileLayerConverter implements TileLayerConverter {

    public void convert(TiledMap3D map3D, TileLayerDefinition layer, Node mapNode, Node mapObjectNode) {
        int layerWidth = layer.getWidth();
        int layerHeight = layer.getHeight();

        for (int y = 0; y < layerHeight; y++) {
            for (int x = 0; x < layerWidth; x++) {
                final int gid = layer.getTileAt(x, y);
                if (gid != 0) {
                    eachTile(map3D, gid, x, y, layer, layerWidth, layerHeight, mapNode, mapObjectNode);
                }
            }
        }
    }

    public void eachTile(TiledMap3D map3D, TileDefinition tileDefinition, int x, int y, int layerWidth, int layerHeight, Node mapNode, Node mapObjectNode) {
    }

    public void eachTile(TiledMap3D map3D, int gid, int x, int y, TileLayerDefinition layer, int layerWidth, int layerHeight, Node mapNode, Node mapObjectNode) {
    }
}
