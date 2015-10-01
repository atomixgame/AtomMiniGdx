package sg.atom.corex.config;

/**
 *
 * @author CuongNguyen
 */
public enum DeviceSettings {

    Phone(800, 480), PC(1024, 768);

    private DeviceSettings(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public boolean landscape;
    public int width;
    public int height;

    public int getWidth() {
        if (landscape) {
            return width;
        } else {
            return height;
        }
    }

    public int getHeight() {
        if (landscape) {
            return height;
        } else {
            return width;
        }
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public boolean isLandscape() {
        return landscape;
    }

}
