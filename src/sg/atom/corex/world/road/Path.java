package sg.atom.corex.world.road;

import com.jme3.material.Material;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.corex.geo.Line3f;
import sg.atom.corex.world.curve.CurveHelper;

/**
 * A Path consist of N control points. Also can be decomposed to N-1 Segment.
 * Each segment or point can be denoted with attributes.
 *
 * @author CuongNguyen
 */
public class Path implements Line3f{

    public static final Logger logger = LoggerFactory.getLogger(Path.class);
    ArrayList<Vector3f> points;
    Spline spline;
    private Line3f polylineMapObject;

    public Path(Line3f polylineMapObject, float ratio, float groundHeight) {
        this.points = new ArrayList<Vector3f>();
        this.polylineMapObject = polylineMapObject;
        logger.info("" + polylineMapObject.toString());
        Vector3f start = polylineMapObject.getStartPoint();
        for (Vector3f mapPoint : polylineMapObject.getPoints()) {
            this.points.add(new Vector3f((start.x + mapPoint.x) * ratio, groundHeight, (start.y + mapPoint.y) * ratio));
        }
    }

    public Path(ArrayList<Vector3f> points) {
        this.points = points;
        spline = new Spline(Spline.SplineType.CatmullRom, points, 0.3f, false);
    }

    public ArrayList<Vector3f> getPoints() {
        return points;
    }

    public ArrayList<Vector3f> getCurvedPoints(int step) {
        ArrayList<Vector3f> interPoints = CurveHelper.interpolateCurve(spline, step);
        return interPoints;
    }

    public Line3f getPolylineMapObject() {
        return polylineMapObject;
    }

    public Vector3f getStart() {
        return points.get(0);
    }

    public Vector3f getEnd() {
        return points.get(points.size() - 1);
    }

    public Node createDebugNode(Material mat) {
        Node result = new Node("PathDebugNode");

        for (int i = 0; i < points.size() - 1; i++) {
            Geometry segmentGeo = new Geometry("segment" + i, new Line(points.get(i), points.get(i + 1)));
            segmentGeo.setMaterial(mat);
            result.attachChild(segmentGeo);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Vector3f v : points) {
            sb.append(v.toString());
        }
        return sb.toString();
    }

    public Vector3f nextPoint(Vector3f pos) {
        int index = points.indexOf(pos);
        if (index + 1 < points.size()) {
            return points.get(index + 1);
        } else {
            return null;
        }
    }

    @Override
    public Vector3f getStartPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector3f getEndPoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLoop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
