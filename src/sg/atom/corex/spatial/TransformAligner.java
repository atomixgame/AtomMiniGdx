/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.spatial;

import com.jme3.math.Transform;

/**
 * TransformAligner align transform with event-based mechanisms.
 *
 * @author cuong.nguyenmanh2
 */
public interface TransformAligner {

    void align(Transform[] tranforms);
}
