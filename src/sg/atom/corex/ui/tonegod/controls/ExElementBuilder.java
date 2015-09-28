package sg.atom.corex.ui.tonegod.controls;

import sg.atom.corex.assets.sprite.SpriteSheet;
import sg.atom.corex.ui.tonegod.layouts.QLayout;
import sg.atom.corex.ui.tonegod.layouts.QuickLayouts;
import tonegod.gui.core.Element;

/**
 * Extended version of ElementBuilder with sprite and anim support.
 *
 * @author cuong.nguyen
 */
public class ExElementBuilder extends QLayout.LayoutBuilder {

    public ExElementBuilder(Element element, QuickLayouts layout) {
        super(element, layout);
    }

    @Override
    public QuickLayouts getLayout() {
        return (QuickLayouts) layout;
    }
}
