/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.math;

/**
 *
 * @author CuongNguyen
 */
public interface Interpolator<T> {

    T interpolate(T first, T next, float amount);
}
