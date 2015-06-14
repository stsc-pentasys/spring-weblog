package workshop.microservices.weblog.core;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("nickName", nickName)
                .append("fullName", fullName)
                .append("emailAddress", emailAddress)
                .build();
    }
}
