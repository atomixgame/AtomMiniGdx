/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.io.text;

import com.google.common.io.LineProcessor;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class ExTextLoader  implements AssetLoader {
    
    boolean firstLineOnly = true;
    String codec;
    LineProcessor<List<String>> lineProcessor;

    public ExTextLoader(String codec, LineProcessor<List<String>> lineProcessor) {
        this.codec = codec;
        this.lineProcessor = lineProcessor;
    }

    
    public Object load(AssetInfo assetInfo) throws IOException {
        return null;
    }

    
    
    
}
