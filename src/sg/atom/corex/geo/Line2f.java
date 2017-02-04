package sg.atom.corex.geo;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.util.List;

/**
 *
 * @author DaiPhongPC
 */
public interface Line2f {
    List<Vector2f> getPoints();
    
    Vector2f getStartPoint();
    
    Vector2f getEndPoint();
    
    boolean isLoop();
}
