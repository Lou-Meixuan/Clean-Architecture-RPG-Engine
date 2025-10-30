package Battle_System.UseCase.Battle;

import Battle_System.Entity.Monster;
import Battle_System.Entity.User;

public class Battle_Interactor implements Battle_InputBoundary {
    private final Battle_InputBoundary battleInputBoundary;
    private final Battle_InputData battleInputData;

    public Battle_Interactor(Battle_InputBoundary battleInputBoundary, Battle_InputData battleInputData) {
        this.battleInputBoundary = battleInputBoundary;
        this.battleInputData = battleInputData;
    }

    @Override
    public void execute(Battle_InputData inputData) {
        final User user = inputData.getUser();
        final Monster monster = inputData.getMonster();
    }
}
