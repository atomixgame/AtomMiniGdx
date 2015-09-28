/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.math;

import com.google.common.base.Converter;
import sg.atom.corex.math.Tweenf.Easing;

/**
 *
 * @author CuongNguyen
 */
public abstract class InterpolateFunction<T> extends Converter<T, Float> implements Interpolator<T> {

    protected Easing easing;

    public void setEasing(Easing easing) {
        this.easing = easing;
    }
    
    protected abstract float getDistance(T first,T next);

    public Easing getEasing() {
        return easing;
    }
    
    
}
