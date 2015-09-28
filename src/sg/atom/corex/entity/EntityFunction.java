/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.entity;

/**
 * Facade and Description (Common implementation) about the functioning of an
 * Entity, can be shared by multi Entities.
 *
 * <p>
 * It share the same abstract level with Component. You can look at it as
 * "Lightweight System" with single Aspect embed inside of Entity.</p>
 *
 *
 * @author atomix
 */
public abstract class EntityFunction {

    SpatialEntity lastTarget;
    // Basic properties
    int id;
    String name;
    String title;
    String icon;
    String info;

    public EntityFunction(String name, String title, String info) {
        this.name = name;
        this.title = title;
        this.info = info;
    }

    public EntityFunction(String name, String title, String icon, String info) {
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.info = info;
    }

    public abstract void invoke(SpatialEntity target);

    public void activeFunction() {
    }

    public void deactiveFunction() {

    }

    public void update(float tpf) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public Boolean isEnable(Object context) {
        return true;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
