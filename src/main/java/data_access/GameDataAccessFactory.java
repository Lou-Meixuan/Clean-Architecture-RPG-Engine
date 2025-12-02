package data_access;

import use_case.Battle.BattleUserDataAccessInterface;
import use_case.move.MoveGameDataAccessInterface;
import use_case.openGame.OpenGameDataAccessInterface;
import use_case.quiz.QuizDataAccessInterface;
import use_case.show_results.ShowResultsGameDataAccessInterface;

/**
 * Factory interface for creating game data access objects.
 * Follows Abstract Factory pattern and Dependency Inversion Principle.
 */
public interface GameDataAccessFactory {
    /**
     * Creates and returns a data access object implementing all game data interfaces.
     *
     * @return A data access object for the game system
     */
    MoveGameDataAccessInterface createGameDataAccess();
}
