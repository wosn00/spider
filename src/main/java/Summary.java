public class Summary {
    private String course;
    private String chapter;
    private String title;
    private String answer;
    private String keyword;

    public Summary(String title, String course, String chapter, String answer, String keyword) {
        this.title = title;
        this.course = course;
        this.chapter = chapter;
        this.answer = answer;
        this.keyword = keyword;
    }

    public Summary() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}