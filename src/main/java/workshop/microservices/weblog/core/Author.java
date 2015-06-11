package workshop.microservices.weblog.core;

import java.io.Serializable;

/**
 * A registered author.
 */
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nickName;

    private final String fullName;

    private final String emailAddress;

    public Author(String nickName, String fullName, String emailAddress) {
        this.nickName = nickName;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    /**
     * @return the author's login name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @return the author's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return the author's email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }
}
