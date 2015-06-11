package workshop.microservices.weblog.core;

import static workshop.microservices.weblog.TestData.*;

/**
 * Creates author test data.
 */
public class AuthorBuilder {

    private String nickName;

    private String fullName;

    private String emailAddress;

    public AuthorBuilder withNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public AuthorBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public AuthorBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public Author build() {
        return new Author(nickName, fullName, emailAddress);
    }

    public static AuthorBuilder defaultAuthor() {
        return new AuthorBuilder()
                .withNickName(NICK_NAME)
                .withFullName(FULL_NAME)
                .withEmailAddress(EMAIL_ADDRESS);
    }
}
