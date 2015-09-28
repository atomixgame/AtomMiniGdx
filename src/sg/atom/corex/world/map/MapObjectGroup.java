/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cuongnguyen
 */
public class MapObjectGroup extends MapLayer {

    List<MapObject> objects;

    public MapObjectGroup(int layerWidth, int layerHeight, String layerName) {
        super(layerWidth, layerHeight, layerName);
        objects = new ArrayList<MapObject>();
    }

    public List<MapObject> getObjects() {
        return objects;
    }

    public void addObject(MapObject mapObject) {
        this.objects.add(mapObject);
    }

    public MapObject getMapObjectByName(String name) {
        for (MapObject mapObject : objects) {
            if (mapObject.getName().equals(name)) {
                return mapObject;
            }
        }
        return null;
    }
}
