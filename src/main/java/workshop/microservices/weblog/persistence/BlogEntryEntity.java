package workshop.microservices.weblog.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Database representation of an blog article.
 */
@Entity
@Table(name = "WL_BLOG_ENTRY")
public class BlogEntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long objectId;

    private Long version;

    private String entryId;

    private String title;

    private String content;

    private UserEntity author;

    private Date created;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(unique = true, nullable = false)
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false, length = 1000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
