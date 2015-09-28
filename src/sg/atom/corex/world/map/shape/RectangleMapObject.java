/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map.shape;

import sg.atom.corex.world.map.MapObject;

/**
 *
 * @author cuongnguyen
 */
public class RectangleMapObject extends MapObject {

    public RectangleMapObject(int x, int y, int width, int height) {
        super(x, y, width, height);

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Rectangle: ");
        sb.append(super.toString());

        return sb.toString();
    }
}
