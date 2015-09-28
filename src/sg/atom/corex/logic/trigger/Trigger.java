/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.logic.trigger;

/**
 *
 * @author CuongNguyen
 */
public interface Trigger<T> {

    void active(T input);

    void deactive(T input);

    Object getData();
}
