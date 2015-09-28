package sg.atom.corex.spatial;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;
import sg.atom.core.AtomMain;

/**
 * Base class for game objects that are interacting in game, and in general can
 * move and can be destroyed. Not to be used for terrain except for things that
 * can be destroyed. For automaticaly updating them, add them to to Game with
 * addAgent().
 *
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public abstract class PhysicalSpatialControl extends AbstractControl {

    protected AtomMain app;
    /**
     * Mass of GameObject.
     */
    protected float mass;
    /**
     * GameObject acceleration speed.
     */
    protected Vector3f acceleration;
    /**
     * Current move speed of GameObject.
     */
    protected float moveSpeed;
    /**
     * Maximum move speed of GameObject
     */
    protected float maxMoveSpeed;
    /**
     * Maximum force that can be applied to this GameObject.
     */
    protected float maxForce;
    /**
     * Rotation speed of GameObject.
     */
    protected float rotationSpeed;
    protected float activeTime = 0;

    public void init(AtomMain app) {
        this.app = app;
    }

    @Override
    protected void controlUpdate(float tpf) {
        activeTime += tpf;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        if (maxMoveSpeed < moveSpeed) {
            this.maxMoveSpeed = moveSpeed;
        }
        this.moveSpeed = moveSpeed;
    }

    public float getMaxForce() {
        return maxForce;
    }

    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;
    }

    public Quaternion getLocalRotation() {
        return spatial.getLocalRotation();
    }

    public void setLocalRotation(Quaternion rotation) {
        spatial.setLocalRotation(rotation);
    }

    /**
     *
     * @return local translation of agent
     */
    public Vector3f getLocalTranslation() {
        return spatial.getLocalTranslation();
    }

    /**
     *
     * @param position local translation of agent
     */
    public void setLocalTranslation(Vector3f position) {
        this.spatial.setLocalTranslation(position);
    }

    /**
     * Setting local translation of agent
     *
     * @param x x translation
     * @param y y translation
     * @param z z translation
     */
    public void setLocalTranslation(float x, float y, float z) {
        this.spatial.setLocalTranslation(x, y, z);
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getMaxMoveSpeed() {
        return maxMoveSpeed;
    }

    public void setMaxMoveSpeed(float maxMoveSpeed) {
        this.maxMoveSpeed = maxMoveSpeed;
    }

    public AtomMain getApp() {
        return app;
    }

    public void setApp(AtomMain app) {
        this.app = app;
    }

    public float getActiveTime() {
        return activeTime;
    }

}
