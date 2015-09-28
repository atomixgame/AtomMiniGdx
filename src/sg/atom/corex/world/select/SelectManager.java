/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.world.select;

import com.google.common.base.Predicate;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.entity.SpatialEntityControl;
import sg.atom.corex.spatial.SceneGraphUtils;

/**
 *
 * @author cuongnguyen
 */
public class SelectManager extends AbstractManager implements RawInputListener {

    Predicate<Spatial> filter;
    List<SpatialEntity> selectedItems;
    SpatialEntity currentSelectItem;
    List<SelectListener> selectListeners;
    boolean hasSelectedItem;
    boolean isMultiselect;
    Camera cam;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.cam = getApp().getCamera();
        getApp().getInputManager().addRawInputListener(this);
        selectListeners = new ArrayList<SelectListener>();
    }

    public Ray createRay(Camera cam) {
        Vector3f origin = cam.getWorldCoordinates(app.getInputManager().getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(app.getInputManager().getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        return new Ray(origin, direction);
    }

    public Ray createRay(Camera cam, Vector2f screenPos) {
        Vector3f origin = cam.getWorldCoordinates(screenPos, 0.0f);
        Vector3f direction = cam.getWorldCoordinates(screenPos, 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        return new Ray(origin, direction);
    }

    private Collidable getCollidable() {
        return app.getWorldManager().getWorldNode();
    }

    public void doShoot(Ray ray) {
        CollisionResults collisionResults = new CollisionResults();
        getCollidable().collideWith(ray, collisionResults);

        if (collisionResults.size() > 0) {
            Geometry target = collisionResults.getClosestCollision().getGeometry();
            SpatialEntity entity = getEntityFromGeometry(target);
            if (entity != null && entity.isSelectable()) {
                this.currentSelectItem = entity;
//                System.out.println("select entity" + entity.getIid());
                notifyListeners(entity);
            } else {
                notifyListeners(null);
            }

        }

    }

    public void doShoot() {
        doShoot(createRay(cam));
    }

    public void addSelectListener(SelectListener selectListener) {
        selectListeners.add(selectListener);
    }

    public void beginInput() {
    }

    public void endInput() {
    }

    public void onJoyAxisEvent(JoyAxisEvent evt) {
    }

    public void onJoyButtonEvent(JoyButtonEvent evt) {
    }

    public void onMouseMotionEvent(MouseMotionEvent evt) {
    }

    public void onMouseButtonEvent(MouseButtonEvent evt) {
        if (isEnabled()) {
            if (evt.isPressed() && evt.getButtonIndex() == MouseInput.BUTTON_LEFT) {
                doShoot(new Vector2f(evt.getX(), evt.getY()));
            }
        }
    }

    public void onKeyEvent(KeyInputEvent evt) {
    }

    public void onTouchEvent(TouchEvent evt) {
        if (isEnabled()) {
            doShoot(new Vector2f(evt.getX(), evt.getY()));
        }
    }

    private SpatialEntity getEntityFromGeometry(Geometry target) {
        SpatialEntityControl spatialEntityControl = SceneGraphUtils.findControlTravelUp(target, SpatialEntityControl.class);
        if (spatialEntityControl == null) {
            return null;
        } else {
            return spatialEntityControl.getEntity();
        }
    }

    private void doShoot(Vector2f screenPos) {
        System.out.println("Click " + screenPos);
        doShoot(createRay(cam, screenPos));
    }

    private void notifyListeners(SpatialEntity entity) {
        if (entity == null) {
            for (SelectListener sl : selectListeners) {
                sl.onDeselectAll();
            }
        } else {
            for (SelectListener sl : selectListeners) {
                sl.onSelectSpatialEntity(entity);
            }
        }
    }

    public static interface SelectListener {

        void onSelectSpatialEntity(SpatialEntity spatialEntity);

        void onDeselectAll();
    }

    public Predicate<Spatial> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<Spatial> filter) {
        this.filter = filter;
    }

    public List<SpatialEntity> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<SpatialEntity> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public SpatialEntity getCurrentSelectItem() {
        return currentSelectItem;
    }

    public void setCurrentSelectItem(SpatialEntity currentSelectItem) {
        this.currentSelectItem = currentSelectItem;
    }

    public List<SelectListener> getSelectListeners() {
        return selectListeners;
    }

    public void setSelectListeners(List<SelectListener> selectListeners) {
        this.selectListeners = selectListeners;
    }

    public boolean isHasSelectedItem() {
        return hasSelectedItem;
    }

    public void setHasSelectedItem(boolean hasSelectedItem) {
        this.hasSelectedItem = hasSelectedItem;
    }

    public boolean isIsMultiselect() {
        return isMultiselect;
    }

    public void setIsMultiselect(boolean isMultiselect) {
        this.isMultiselect = isMultiselect;
    }

}
