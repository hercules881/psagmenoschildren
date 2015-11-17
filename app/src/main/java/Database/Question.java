package Database;

/**
 * Created by h.lionakis on 13/11/2015.
 */
public class Question {
    public Question(int questionId, String text, String category) {
        this.questionId = questionId;
        this.text = text;
        Category = category;
    }

    public Question() {
    }

    private int questionId;
    private String text;
    private String Category;

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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

}
