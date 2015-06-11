package workshop.microservices.weblog.persistence;

/**
 * Common interface for transformations between two types..
 */
@FunctionalInterface
public interface Mapper<FROM, TO> {

    /**
     * Converts FROM to TO.
     *
     * @param f original object
     * @return converted object
     */
    TO map(FROM f);

}
