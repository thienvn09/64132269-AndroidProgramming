package thien.com.math_fun_project;

public class HinhItem {
    private int imageResId;
    private String word;
    private String[] answerChars;

    public HinhItem(int imageResId, String word, String[] answerChars) {
        this.imageResId = imageResId;
        this.word = word;
        this.answerChars = answerChars;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getWord() {
        return word;
    }

    public String[] getAnswerChars() {
        return answerChars;
    }
}