/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.math;

/**
 *
 * @author CuongNguyen
 */
public class Vector2i {

    public int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Vector2i) {
            return equals((Vector2i) obj);
        }
        return false;
    }

    public boolean equals(Vector2i p) {
        if (x == p.x && y == p.y) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
