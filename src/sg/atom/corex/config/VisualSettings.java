package sg.atom.corex.config;

import static sg.atom.corex.config.QualitySetting.*;

/**
 * Common implementation for visual settings with detection.
 *
 * @author atomix
 */
public class VisualSettings {
    // Render

    public QualitySetting visualQuality = NORMAL;
    // Enviroments
    public QualitySetting enviromentQuality = NORMAL;
    // Effect
    public QualitySetting effectQuality = NORMAL;
    // Shader
    public String shaderVersion = "GL1.5";
    public QualitySetting shaderQuality = NORMAL;


    public VisualSettings() {


    }

    public void isSupported(String shaderCapacity) {
    }
//
//    public AbstractConfiguration getConfiguration() throws ConfigurationException {
//        return new null;
//    }
}
