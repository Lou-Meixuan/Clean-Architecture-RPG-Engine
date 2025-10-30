package Battle_System.UseCase.Battle;

public interface Battle_InputBoundary {

    void execute(Battle_InputData inputData);

    /**
     * Input Boundary for actions which are related to battle.
     */
    public interface LoginInputBoundary {

        /**
         * Executes the battle use case.
         * @param inputData the input battle data
         */
        void execute(Battle_InputData inputData);
    }
}
