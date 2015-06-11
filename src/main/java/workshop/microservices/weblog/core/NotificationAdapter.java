package workshop.microservices.weblog.core;

/**
 * Represents business relevant events.
 */
public interface NotificationAdapter {

    /**
     * A new article was sucessfully published.
     *
     * @param newEntry the published article
     */
    void created(Article newEntry);

    /**
     * An already published article was modified.
     *
     * @param existingEntry
     */
    void edited(Article existingEntry);

}
