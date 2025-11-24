package interface_adapter.quiz;

import entity.Monster;
import entity.User;

/**
 * State object for the Quiz View.
 */
public class Quiz_State {
    private User user = null;
    private Monster monster = null;
    private boolean quizResult = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public boolean getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(boolean quizResult) {
        this.quizResult = quizResult;
    }
}
