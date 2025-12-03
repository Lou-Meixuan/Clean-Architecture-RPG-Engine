package use_case.load_quiz;

public class LoadQuizInputData {
    private final int quizId;

    public LoadQuizInputData(int quizId) {
        this.quizId = quizId;
    }

    public int getQuizId() { return quizId; }
}
