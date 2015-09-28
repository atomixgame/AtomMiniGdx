/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

/**
 *
 * @author cuongnguyen
 */
public abstract class TargetedGameAction<T> extends GameAction {

    T target;

    public TargetedGameAction(int id, GameActor actor, ActionTrigger trigger) {
        super(id, actor, trigger);
    }

    public abstract boolean onInvokeTarget(T target);
}
