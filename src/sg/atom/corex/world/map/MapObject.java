/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.map;

import com.jme3.math.Vector2f;

/**
 *
 * @author cuongnguyen
 */
public abstract class MapObject {

    protected String name;
    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;
    protected Vector2f position;
    protected Vector2f size;
    protected Vector2f center;

    public MapObject(float x, float y) {
        this.x = x;
        this.y = y;
        this.position = new Vector2f(x, y);
        this.size = new Vector2f(0, 0);
        this.center = new Vector2f(x, y);
    }

    public MapObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.position = new Vector2f(x, y);
        this.size = new Vector2f(width, height);
        this.center = new Vector2f(x + width / 2, y + height / 2);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
//    public abstract Vector2f getPoints();

    public Vector2f getPosition() {
        return position;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setPosition(Vector2f position) {
        this.position = position;

        this.x = position.x;
        this.y = position.y;
    }

    public void setSize(Vector2f size) {
        this.size = size;
        this.width = size.x;
        this.height = size.y;
    }

    public Vector2f getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("x:");
        sb.append(x);
        sb.append("y:");
        sb.append(y);
        sb.append("width:");
        sb.append(width);
        sb.append("height:");
        sb.append(height);
        return sb.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
