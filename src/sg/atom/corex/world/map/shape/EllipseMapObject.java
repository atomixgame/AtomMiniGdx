/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map.shape;

import com.jme3.math.Vector2f;
import sg.atom.corex.world.map.MapObject;

/**
 *
 * @author cuongnguyen
 */
public class EllipseMapObject extends MapObject {

    public EllipseMapObject(int x, int y, int width, int height) {
        super(x, y);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Ellipse: ");
        sb.append(super.toString());

        return sb.toString();
    }
}
