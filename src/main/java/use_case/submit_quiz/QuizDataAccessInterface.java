package use_case.submit_quiz;

import entity.Quiz;

public interface QuizDataAccessInterface {
    Quiz findById(int quizId);
    void save(Quiz quiz);
}
