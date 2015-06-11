package workshop.microservices.weblog.core;

import java.util.List;

/**
 * Core service API.
 */
public interface BlogService {

    /**
     * Retrieve all published articles from underlying datastore.
     * Implementation must ensure a proper order, i.e. newest first.
     *
     * @return a list of articles, or an empty list
     */
    List<Article> index() throws BlogServiceException;

    /**
     * Retrieve a single article from the underlying datastore.
     *
     * @param articleId the article's unique id
     * @return the corresponding article
     */
    Article read(String articleId) throws BlogServiceException;

    /**
     * Create a new article and save it to the underlying datastore.
     *
     * @param author the author's login name
     * @param title the new article's title
     * @param content the new article's content
     * @return the created Article
     */
    Article publish(String author, String title, String content) throws BlogServiceException;

    /**
     * Modify an existing article in the underlying datastore.
     * Only title and content will be overwritten, the id remains unchanged regardless of a new title.
     *
     * @param articleId the article's unique id
     * @param editor the author's login name
     * @param title the article's new title
     * @param content the article's new content
     * @return the modified article
     */
    Article edit(String articleId, String editor, String title, String content) throws BlogServiceException;

}
