/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io.xml;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author CuongNguyen
 */
public class DOMLoader implements AssetLoader {

    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream in = assetInfo.openStream();
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            dbf.setCoalescing(true);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setValidating(false);
            doc = dbf.newDocumentBuilder().parse(in);
        } catch (Exception se) {
        }
        return doc;
    }
}
