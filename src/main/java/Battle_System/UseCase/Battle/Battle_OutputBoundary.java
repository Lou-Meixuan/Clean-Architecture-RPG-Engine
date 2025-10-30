package Battle_System.UseCase.Battle;

public interface Battle_OutputBoundary {
    /**
     * Prepares the Flee view for the Battle Use Case.
     * @param outputData the output data
     */
    void prepareFleeView(Battle_OutputData outputData);

    /**
     * Prepares the Battle view for the Battle Use Case.
     * @param outputData the output data
     */
    void prepareBattleView(Battle_OutputData outputData);
}
