package workshop.microservices.weblog.resource;

/**
 * Represents a new or modified entry.
 */
public class ArticleRequest {

    private String nickName;
    private String title;
    private String content;

    /**
     * Necessary for JSON deserialization.
     */
    public ArticleRequest() {
    }

    /**
     * Create a new request.
     *
     * @param nickName the author's user id
     * @param title the new entry's title
     * @param content its content
     */
    public ArticleRequest(String nickName, String title, String content) {
        this.nickName = nickName;
        this.title = title;
        this.content = content;
    }

    /**
     * @return the author's login name
     */
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return the article's title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the article's content
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
