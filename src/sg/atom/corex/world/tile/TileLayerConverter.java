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
public interface TileLayerConverter {

    public void convert(TiledMap3D map3D, TileLayerDefinition layer, Node mapNode, Node mapObjectNode);
}
