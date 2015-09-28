/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map.shape;

import com.jme3.math.Vector2f;
import java.util.List;
import sg.atom.corex.world.map.MapObject;

/**
 *
 * @author cuongnguyen
 */
public class PolylineMapObject extends MapObject {

    private final List<Vector2f> points;

    public PolylineMapObject(int x, int y, List<Vector2f> points) {
        super(x, y);
        this.points = points;
    }

    public List<Vector2f> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Polyline: ");
        sb.append(super.toString());
        sb.append(".Points: ");

        for (Vector2f p : points) {
            sb.append(p.toString());
            sb.append(",");
        }
        return sb.toString();
    }
}
