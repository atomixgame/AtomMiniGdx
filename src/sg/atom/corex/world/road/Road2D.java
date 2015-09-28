package sg.atom.corex.world.road;

import java.util.ArrayList;

/**
 * 2D Shape of the road;
 *
 * @author CuongNguyen
 */
public class Road2D {

    public Path path;
    public float width;
    public int type;
    public ArrayList<Float> widths;
    public ArrayList<Lane> lanes;
    //int numOfLanes;
    //Additional info
    public int pavementType;

    public Road2D(Path newPath) {
        this.path = newPath;
    }

    public void setPath(Path newPath) {
        this.path = newPath;
    }

    public Path getPath() {
        return path;
    }
}
