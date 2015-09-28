package sg.atom.corex.ui.tonegod.controls;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.TouchInput;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import java.util.HashMap;
import sg.atom.corex.ui.tonegod.Command;
import sg.atom.corex.ui.tonegod.layouts.QuickLayouts;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.ElementManager;
import tonegod.gui.core.Screen;

/**
 * Button with Sprites.
 *
 * @author cuong.nguyen
 */
public class ExButtonAdapter extends ButtonAdapter {

    HashMap<Integer, Command> commands = new HashMap<Integer, Command>();

    public ExButtonAdapter(ElementManager screen) {
        super(screen, new Vector2f());
    }

    public ExButtonAdapter(ElementManager screen, String UID) {
        super(screen, UID, new Vector2f());
    }

    public ExButtonAdapter(ElementManager screen, String UID, Vector2f position, Vector2f dimensions, QuickLayouts layout, String spriteSheet, String normal, String down, String hover) {
        super(screen, UID, position, dimensions);
        layout.buttonSprite(this, spriteSheet, normal, down, hover);
    }

    public void decorate(Integer state, Command command) {
        commands.put(state, command);
    }

    @Override
    public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
        super.onButtonMouseLeftDown(evt, toggled);
        Command c = commands.get(MouseInput.BUTTON_LEFT);
        if (c != null) {
            c.execute((Screen) screen, this);
        }
    }

    public static class Builder extends ExElementBuilder {

        public Builder(ExButtonAdapter newButton, QuickLayouts layout) {
            super(newButton, layout);
        }

        public Builder onClick(Command command) {
            ((ExButtonAdapter) element).decorate(MouseInput.BUTTON_LEFT, command);
            return this;
        }

        public ExButtonAdapter getElement() {
            return (ExButtonAdapter) element;
        }
    }
}
