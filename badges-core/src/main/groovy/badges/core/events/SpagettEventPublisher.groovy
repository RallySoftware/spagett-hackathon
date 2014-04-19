package badges.core.events

import badges.core.api.Badge
import org.osgi.framework.BundleContext
import org.osgi.service.event.Event
import org.osgi.service.event.EventAdmin

class SpagettEventPublisher {
    BundleContext context

    void publishEvent(String eventType, Map<String, String> props) {
        Dictionary properties = new Hashtable();
        properties.put("event-type", eventType)
        props.each { k, v ->
            properties.put(k, v)
        }
        EventAdmin eventAdmin = (EventAdmin) context.getService(context.getServiceReference(EventAdmin));
        Event event = new Event(SpagettEventConstants.SPAGETT_TOPIC, properties);
        eventAdmin.postEvent(event);
    }

    void publishEarnedBadgeEvent(String user, Badge badge) {
        publishEarnedBadgeEvent(user, badge.title)
    }

    void publishEarnedBadgeEvent(String user, String badgeName) {
        def props = [
                badge: badgeName,
                user : user
        ]
        publishEvent(SpagettEventConstants.EVENT_TYPE_EARNED_BADGE, props)
    }
}
