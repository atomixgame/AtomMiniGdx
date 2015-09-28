package sg.atom.corex.math;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility and fast math functions for 3D.
 *
 * <p>
 * <b>DONE </b><s>Merged with MathUtils.</s>
 *
 * <p>
 * FIXME: Replace with Common's Math and JScience!
 *
 * @author atomix
 */
public class AtomFastMath extends sg.atom.corex.math.MathUtils {

    public static Random random = new Random();

    // aditional 3d maths
    public static Vector3f getPenVec(Vector3f vec) {
        //Plane p = new Plane(vec, point.dot(vec));
        Vector3f upVec = Vector3f.UNIT_Y;
        if (vec.normalize().angleBetween(upVec) < 0.0001f) {
            upVec = Vector3f.UNIT_X;
        } else {
            upVec = Vector3f.UNIT_Y;
        }
        Vector3f penVec = vec.cross(upVec);

        return penVec;
    }

    public static Vector3f normalCal(Vector3f a, Vector3f b, Vector3f c) {
        return a.subtract(b).cross(a.subtract(c)).normalize();
    }

    public static Transform inverseTransform(Transform t) {
        Vector3f iT = t.getTranslation().negate();
        Quaternion iQ = t.getRotation().inverse();
        Vector3f iS = Vector3f.UNIT_XYZ.divide(t.getScale());
        return new Transform(iS, iQ, iS);

    }

    public static Transform subtractTransform(Transform t1, Transform t2) {
        return t1.clone().combineWithParent(inverseTransform(t2));
    }

    public static Vector3f randomVec3(Vector3f limit) {

        float x = random.nextFloat() * limit.x;
        float y = random.nextFloat() * limit.y;
        float z = random.nextFloat() * limit.z;
        return new Vector3f(x, 0, z);
    }

    public static Vector3f randomVec3(float min, float max) {
        float limit = min - max;
        float x = min + random.nextFloat() * limit;
        float y = min + random.nextFloat() * limit;
        float z = min + random.nextFloat() * limit;
        return new Vector3f(x, 0, z);
    }

//    public static Pair<Integer, Integer> randomIntPairGeneric(int min, int max) {
//        return new Pair<Integer, Integer>(FastMath.nextRandomInt(min, max), FastMath.nextRandomInt(min, max));
//    }
//
//    public static IntPair randomIntPair(int min, int max) {
//        return new IntPair(FastMath.nextRandomInt(min, max), FastMath.nextRandomInt(min, max));
//    }
    //Curves Math --------------------------------------------------------------
    public static ArrayList<Vector3f> interpolateCurve(Spline curve, int segs) {
        List<Vector3f> controlPoints = curve.getControlPoints();
        ArrayList<Vector3f> interPoints = new ArrayList<Vector3f>();
        for (int i = 0; i < controlPoints.size() - 1; i++) {
            interPoints.add(controlPoints.get(i).clone());
            for (int j = 1; j < segs; j++) {
                Vector3f newPoint = new Vector3f();
                curve.interpolate((float) j / segs, i, newPoint);
                interPoints.add(newPoint);
            }
        }
        interPoints.add(controlPoints.get(controlPoints.size() - 1).clone());
        return interPoints;
    }

    /**
     * Interpolate a curve and adding an offset with direction.
     *
     * @param curve
     * @param segs
     */
    public static void interpolateCurveOffest(Spline curve, int segs) {
    }
    //Surface Math--------------------------------------------------------------
    //Algebra--------------------------------------------------------------------
//    public static String inverseFunction(String functionText) {
//        return functionText;
//    }
//    
//    public static Number parse(String functionText){
//        return 0;
//    }
}
