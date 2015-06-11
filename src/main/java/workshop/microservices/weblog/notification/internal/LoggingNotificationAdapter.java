package workshop.microservices.weblog.notification.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.NotificationAdapter;

/**
 * Minimal implementation based on SLF4j API.
 */
@Component
public class LoggingNotificationAdapter implements NotificationAdapter {

    public static final Logger LOG = LoggerFactory.getLogger("workshop.tdd.weblog.BusinessNotification");

    @Override
    public void created(Article newArticle) {
        logArticle("A new article was published by '{}' with articleId '{}' and title '{}'", newArticle);
    }

    private void logArticle(String format, Article article) {
        LOG.info(format,
                article.getPublishedBy().getFullName(),
                article.getArticleId(),
                article.getTitle());
    }

    @Override
    public void edited(Article existingArticle) {
        logArticle("An existing article was modified by '{}' with articleId '{}' and title '{}'", existingArticle);
    }
}
