package sg.atom.corex.ui.tonegod.layouts;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import sg.atom.corex.ui.tonegod.controls.ExElementBuilder;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import sg.atom.corex.assets.GlobalAssetCache;
import sg.atom.corex.assets.sprite.SpriteSheet;
import sg.atom.corex.ui.tonegod.controls.ExButtonAdapter;
import sg.atom.corex.ui.tonegod.layouts.QLayout.LayoutBuilder;
import tonegod.gui.controls.buttons.Button;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;
import tonegod.gui.core.layouts.LayoutHint;

/**
 * Advanced version of QLayout with new experiments. //
 * layout.button("btnStart", "Start") // .onClick(new Command() { // public void
 * execulte(Screen screen, Element element) { // toState(InGameState.class); //
 * } // }).size(50, 50).pos(100, 100).set();
 *
 * @author cuong.nguyen
 */
public class QuickLayouts extends QLayout {

    private final AssetManager assetManager;
    private final GlobalAssetCache assetCache;
    public int GRID_X = 6;
    public int GRID_Y = 2;
    public int CELL_SIZE_X = 120;
    public int CELL_SIZE_Y = 120;
    public int GAP_X = 10;
    public int GAP_Y = 10;

    public QuickLayouts(Screen screen, GlobalAssetCache assetCache) {
        super(screen);

        this.assetManager = screen.getApplication().getAssetManager();
        this.assetCache = assetCache;
    }

    public ExButtonAdapter.Builder button(String id, String text) {
        ExButtonAdapter newButton = new ExButtonAdapter(screen, id);
        newButton.setText(text);
//            ids.put(id, image);
        ExButtonAdapter.Builder builder = new ExButtonAdapter.Builder(newButton, QuickLayouts.this);
        return builder;
    }

    public ExElementBuilder image(String id, String path) {
        Element newImage = new Element(screen, id, Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, path);
//            ids.put(id, image);
        ExElementBuilder builder = new ExElementBuilder(newImage, QuickLayouts.this);
        return builder;
    }

    public ExElementBuilder sprite(String id, String spriteSheetName, String spriteName) {
        Element newImage = new Element(screen, id, Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, null);
        sprite(newImage, spriteSheetName, spriteName);
//            ids.put(id, image);
        ExElementBuilder builder = new ExElementBuilder(newImage, QuickLayouts.this);
        return builder;
    }

    public Element sprite(Element element, String spriteSheetName, String spriteName) {
        SpriteSheet spriteSheet = assetCache.getSpriteSheets().get(spriteSheetName);
        element.setTextureAtlasImage(spriteSheet.getTexture(), spriteSheet.getSprite(spriteName).toTonegodGUIFormat());
        return element;
    }

    public GroupBuilder group(LayoutBuilder... elements) {
        Element newGroup = new Element(screen, "", Vector2f.ZERO, Vector2f.ZERO, Vector4f.ZERO, null);
        //FIXME: Make a real group attributes
        return new GroupBuilder(elements, newGroup);
    }

    public static Button buttonDisplay(Button btnNew, String hover, String down) {
        btnNew.setButtonHoverInfo(hover,
                ColorRGBA.White);
        btnNew.setButtonPressedInfo(down,
                ColorRGBA.White);
        return btnNew;
    }

    public static Button buttonDisplay(Button btnNew, String hover, ColorRGBA hoverColor, String down, ColorRGBA downColor) {
        btnNew.setButtonHoverInfo(hover, hoverColor);
        btnNew.setButtonPressedInfo(down, downColor);
        return btnNew;
    }

    public Button buttonSprite(Button btnNew, String spriteSheetName, String spriteName) {
        SpriteSheet spriteSheet = assetCache.getSpriteSheets().get(spriteSheetName);
        btnNew.setTextureAtlasImage(spriteSheet.getTexture(), spriteSheet.getSprite(spriteName).toTonegodGUIFormat());
        btnNew.setButtonHoverInfo(spriteSheet.getSprite(spriteName).toTonegodGUIFormat(), ColorRGBA.White);
        btnNew.setButtonPressedInfo(spriteSheet.getSprite(spriteName).toTonegodGUIFormat(), ColorRGBA.White);
        return btnNew;
    }

    public Button buttonSprite(Button btnNew, String spriteSheetName, String spriteName, String downImg, String hoverImg) {
        SpriteSheet spriteSheet = assetCache.getSpriteSheets().get(spriteSheetName);
        btnNew.setTextureAtlasImage(spriteSheet.getTexture(), spriteSheet.getSprite(spriteName).toTonegodGUIFormat());
        btnNew.setButtonHoverInfo(spriteSheet.getSprite(downImg).toTonegodGUIFormat(), ColorRGBA.White);
        btnNew.setButtonPressedInfo(spriteSheet.getSprite(hoverImg).toTonegodGUIFormat(), ColorRGBA.White);
        return btnNew;
    }

    public void origin(Element element, LayoutHint.Align align) {
        if (align == LayoutHint.Align.center) {
            float x = element.getPosition().x;
            float y = element.getPosition().y;
            float w = element.getWidth();
            float h = element.getHeight();
            element.setPosition(x - w / 2, y - h / 2);
        }
    }

    public class GroupBuilder extends ListLayoutBuilder {

        private LayoutBuilder groupElementBuilder;

        public GroupBuilder(LayoutBuilder[] elements, Element element) {
            super(elements, QuickLayouts.this);
            this.groupElementBuilder = new LayoutBuilder(element, QuickLayouts.this);
            this.addTo(element);
        }

        public LayoutBuilder panel() {
            return groupElementBuilder;
        }

        @Override
        public void set() {
            this.groupElementBuilder.fill();
            this.groupElementBuilder.set();
            super.set();
        }
    }
}
