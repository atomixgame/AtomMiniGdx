/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyen
 */
public interface GameActor {

    void onStage(StageManager stageManager);

    void onOutStage(StageManager stageManager);

    boolean isStaged();

    void act(int type, Object param);

    void addAction(ActionTrigger actionTrigger, GameAction action);
}
