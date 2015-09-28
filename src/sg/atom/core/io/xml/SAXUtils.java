/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io.xml;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import static com.jme3.util.xml.SAXUtil.parseFloat;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 *
 * @author CuongNguyen
 */
public class SAXUtils {

    /**
     * Parses an integer from a string, if the string is null returns def.
     *
     * @param i The string to parse
     * @param def The default value if the string is null
     * @return
     * @throws SAXException
     */
    public static int parseInt(String i, int def) {
        if (i == null) {
            return def;
        } else {
            try {
                return Integer.parseInt(i);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    public static int parseInt(String i) {
        if (i == null) {
            return 0;
        } else {
            try {
                return Integer.parseInt(i);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }

    }

    public static float parseFloat(String f, float def) {
        if (f == null) {
            return def;
        } else {
            try {
                return Float.parseFloat(f);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    public static float parseFloat(String f) {
        if (f == null) {
            return 0;
        } else {
            try {
                return Float.parseFloat(f);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    public static boolean parseBool(String bool, boolean def) {
        if (bool == null || bool.equals("")) {
            return def;
        } else {
            return Boolean.valueOf(bool);
        }
        //else
        //else
        //    throw new SAXException("Expected a boolean, got'"+bool+"'");
    }

    public static String parseString(String str, String def) {
        if (str == null) {
            return def;
        } else {
            return str;
        }
    }

    public static String parseString(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static Vector3f parseVector3(Attributes attribs) {
        float x = parseFloat(attribs.getValue("x"));
        float y = parseFloat(attribs.getValue("y"));
        float z = parseFloat(attribs.getValue("z"));
        return new Vector3f(x, y, z);
    }

    public static ColorRGBA parseColor(Attributes attribs) {
        float r = parseFloat(attribs.getValue("r"));
        float g = parseFloat(attribs.getValue("g"));
        float b = parseFloat(attribs.getValue("b"));
        return new ColorRGBA(r, g, b, 1f);
    }

    public static int readInt(Node node, String attributeName, int def) {
        try {
            return Integer.parseInt(node.getAttributes().getNamedItem(attributeName).getNodeValue());
        } catch (Exception ex) {
            return def;
        }
    }

    public static float readFloat(Node node, String attributeName, float def) {
        try {
            return Float.parseFloat(node.getAttributes().getNamedItem(attributeName).getNodeValue());
        } catch (Exception ex) {
            return def;
        }
    }
}
