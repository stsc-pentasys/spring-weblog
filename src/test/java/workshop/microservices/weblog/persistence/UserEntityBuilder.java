package workshop.microservices.weblog.persistence;

import static workshop.microservices.weblog.TestData.*;

/**
 * Creates test data.
 */
public class UserEntityBuilder {

    private Long objectId;

    private Long version;

    private String userId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    public UserEntityBuilder withObjectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public UserEntityBuilder andVersion(Long version) {
        this.version = version;
        return this;
    }

    public UserEntityBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntityBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserEntity build() {
        UserEntity entity = new UserEntity();
        entity.setObjectId(objectId);
        entity.setVersion(version);
        entity.setUserId(userId);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setEmailAddress(emailAddress);
        return entity;
    }

    public static UserEntityBuilder defaultUserEntity() {
        return new UserEntityBuilder()
                .withObjectId(1L)
                .andVersion(1L)
                .withUserId(NICK_NAME)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withEmailAddress(EMAIL_ADDRESS);
    }
}
