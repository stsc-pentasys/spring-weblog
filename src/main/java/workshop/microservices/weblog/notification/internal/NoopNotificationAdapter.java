package workshop.microservices.weblog.notification.internal;

import org.springframework.stereotype.Component;
import workshop.microservices.weblog.Production;
import workshop.microservices.weblog.core.Article;
import workshop.microservices.weblog.core.NotificationAdapter;

/**
 * Default (no operation) implemantion.
 */
@Production @Component
public class NoopNotificationAdapter implements NotificationAdapter {

    @Override
    public void created(Article newEntry) {
        // Simply do nothing!
    }

    @Override
    public void edited(Article existingEntry) {
        // Simply do nothing!
    }
}
