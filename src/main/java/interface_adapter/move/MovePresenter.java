package interface_adapter.move;

import entity.Monster;
import entity.User;
import interface_adapter.Battle.BattleState;
import interface_adapter.Battle.BattleViewModel;
import interface_adapter.ViewManagerModel;
import use_case.move.MoveOutputBoundary;
import use_case.move.MoveOutputData;

import javax.swing.*;

public class MovePresenter implements MoveOutputBoundary {

    private final MoveViewModel moveViewModel;
    private final BattleViewModel battleViewModel;
    private final ViewManagerModel viewManagerModel;


    public MovePresenter(ViewManagerModel viewManagerModel, MoveViewModel moveViewModel, BattleViewModel battleViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.moveViewModel = moveViewModel;
        this.battleViewModel = battleViewModel;
    }

    public void present(MoveOutputData moveOutputData) {
        MoveState moveState = moveViewModel.getState();

        moveState.setLeftButtonEnabled(moveOutputData.isCanMoveLeft());
        moveState.setRightButtonEnabled(moveOutputData.isCanMoveRight());
        moveState.setCurrentLocationName(moveOutputData.getCurrentLocationName());
        moveState.setMonster(moveOutputData.getMonster());
        moveState.setItem(moveOutputData.getItem());
        moveState.setItemPickupable(moveOutputData.getItem() != null);
        String linearMap = formatLinearMap(
                moveOutputData.getCurrentIndex(),
                moveOutputData.getMapSize()
        );
        moveState.setLinearMap(linearMap);

        ImageIcon mapImage = moveOutputData.getStaticMapImage();
        moveState.setStaticMapImage(mapImage);

        moveViewModel.firePropertyChange();
    }

    @Override
    public void switchToBattleView(User user, Monster monster) {
        BattleState battleState = battleViewModel.getState();

        // Reset battle state for new battle
        battleState.setUser(user);
        battleState.setMonster(monster);
        battleState.setNewBattleStarted(true);
        battleState.setBattleEnded(false);
        battleState.setUserWon(false);
        battleState.setBattleMessage("Battle is starting...");

        battleViewModel.firePropertyChange();

        viewManagerModel.setState(battleViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    private String formatLinearMap(int currentIndex, int mapSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mapSize; i++) {
            if (i == currentIndex) {
                sb.append("■");
            } else {
                sb.append("□");
            }

            if (i < mapSize - 1) {
                sb.append(" — ");
            }
        }
        return sb.toString();
    }
}
