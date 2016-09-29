package com.example.csaper6.quizapp;

/**
 * Created by csaper6 on 9/15/16.
 */
public class Question {
    private int questionId;
    private boolean isAnswerTrue;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }

    public Question(int questionId, boolean isAnswerTrue) {
        this.questionId = questionId;
        this.isAnswerTrue = isAnswerTrue;
    }

    /**
     * If answerGiven & isAnswerTrue match, it returns true
     * Otherwise, returns false
     *
     * @param answerGiven user's answer clicked
     * @return true if they got the question right
     * @return false if they got the question wrong
     */
    public boolean checkAnswer(boolean answerGiven){ return answerGiven == isAnswerTrue; }
}
