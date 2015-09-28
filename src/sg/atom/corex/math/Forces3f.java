/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.math;

import com.jme3.math.Vector3f;

/**
 * Math for forces.
 * 
 * @author CuongNguyen
 */
public class Forces3f {

    public static final Vector3f GLOBAL_UP = Vector3f.UNIT_Y;

    public static Vector3f linearVecTargeted(float time, float speed, Vector3f startPos, Vector3f targetPos) {
        float d = startPos.distance(targetPos);
        if (d != 0) {
            float amount = (time * speed) / d;
            return new Vector3f(0, 0, 0).interpolateLocal(startPos, targetPos, amount);
        } else {
            return new Vector3f(1, 1, 1);
        }
    }

    public static Vector3f linearVecTargetedDuration(float time, float duration, Vector3f startPos, Vector3f targetPos) {
        float d = startPos.distance(targetPos);
        if (d != 0) {
            return new Vector3f(0, 0, 0).interpolateLocal(startPos, targetPos, time / duration);
        } else {
            return new Vector3f(1, 1, 1);
        }
    }

    public static Vector3f linearVecDirected(float time, float speed, Vector3f startPos, Vector3f direction) {
        return startPos.clone().add(direction.normalize().mult(time * speed));
    }
    
    
}
