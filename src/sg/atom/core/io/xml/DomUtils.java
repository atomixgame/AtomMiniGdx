/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author cuongnguyen
 */
public class DomUtils {

    public static String getAttributeValue(final Node node, final String attribname) {
        final NamedNodeMap attributes = node.getAttributes();
        String att = null;
        if (attributes != null) {
            final Node attribute = attributes.getNamedItem(attribname);
            if (attribute != null) {
                att = attribute.getNodeValue();
            }
        }
        return att;
    }

    public static int getAttribute(final Node node, final String attribname, final int def) {
        final String attr = getAttributeValue(node, attribname);
        if (attr != null) {
            return Integer.parseInt(attr);
        } else {
            return def;
        }
    }

    public static int[] getAttributeIntArray(final Node node, final String attribname) {
        final String attr = getAttributeValue(node, attribname);
        if (attr != null) {
            String[] splits = attr.split("[,\\s]");
            int[] result = new int[splits.length];
            for (int i = 0; i < splits.length; i++) {
                result[i] = Integer.valueOf(splits[i]);
            }
            return result;
        }
        return null;
    }
}
