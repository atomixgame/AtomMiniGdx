package sg.atom.corex.geo;

import com.jme3.math.Vector3f;
import java.util.List;

/**
 *
 * @author DaiPhongPC
 */
public interface Line3f {
    List<Vector3f> getPoints();
    
    Vector3f getStartPoint();
    
    Vector3f getEndPoint();
    
    boolean isLoop();
}
