package workshop.microservices.weblog.core;

/**
 * Creates IDs for business entities.
 */
public interface IdNormalizer {

    /**
     * Transforms the given title into a uniform ID.
     * Format depends on the implementation.
     *
     * @param title the original string
     * @return a normalized version
     */
    String normalizeTitle(String title);

}
