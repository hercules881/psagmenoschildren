package Database;

/**
 * Created by h.lionakis on 13/11/2015.
 */
public class Answer {

    private int answerId;
    private int questionId;
    private String text;

    public int getIsValidAnswer() {
        return isValidAnswer;
    }

    public void setIsValidAnswer(int isValidAnswer) {
        this.isValidAnswer = isValidAnswer;
    }

    private int isValidAnswer;

    public Answer(int answerId, int questionId, String text, int isValidAnswer) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.text = text;
        this.isValidAnswer = isValidAnswer;
    }
    public Answer() {

    }
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
