/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io.text;

import com.google.common.base.Joiner;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class TextLoader implements AssetLoader {

    ArrayList<String> loadedLines = null;

    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        InputStream in = assetInfo.openStream();

        if (in != null) {
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(in));
            loadedLines = new ArrayList<String>();
            String line = bufRead.readLine();
            while (line != null) {
                loadedLines.add(line);
                line = bufRead.readLine();
            }
            if (!assetInfo.getKey().getExtension().equalsIgnoreCase("json")) {

                return loadedLines;
            } else {
                StringBuilder sb = new StringBuilder();
                for (String s : loadedLines) {
                    sb.append(s);
                }
                return sb.toString();
            }
        }
        return null;
    }
}
