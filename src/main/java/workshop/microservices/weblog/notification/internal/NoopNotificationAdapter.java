package workshop.microservices.weblog.notification.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import workshop.microservices.weblog.Development;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.NotificationAdapter;

/**
 * Default (no operation) implemantion.
 */
@Development @Component
public class NoopNotificationAdapter implements NotificationAdapter {

    private static final Logger LOG = LoggerFactory.getLogger("workshop.microservices.notification");

    public NoopNotificationAdapter() {
        LOG.warn("Business logging deactivated!");
    }

    public void created(Article newEntry) {
        // Simply do nothing!
    }

    @Override
    public void edited(Article existingEntry) {
        // Simply do nothing!
    }
}
