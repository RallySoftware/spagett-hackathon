package badges.core.events

import aQute.bnd.annotation.component.Activate
import aQute.bnd.annotation.component.Component
import org.osgi.service.component.ComponentContext
import org.osgi.service.event.Event
import org.osgi.service.event.EventConstants
import org.osgi.service.event.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component(provide = [], immediate = true)
class EarnedBadgeEventHandler implements EventHandler {
    static final Logger log = LoggerFactory.getLogger(EarnedBadgeEventHandler)

    public void handleEvent(Event event) {
        String badgeName = event.getProperty('badge')
        String user = event.getProperty('user')
        log.info("User $user just earned the $badgeName badge!")
    }

    @Activate
    protected void activate(ComponentContext componentContext) {
        def bundleContext = componentContext.bundleContext
        Dictionary props = buildEventHandlerProps()
        bundleContext.registerService(EventHandler, this, props)
    }

    static Dictionary buildEventHandlerProps() {
        String[] topics = [SpagettEventConstants.SPAGETT_TOPIC].toArray(new String[0])
        Dictionary props = new Hashtable()
        props.put(EventConstants.EVENT_TOPIC, topics)
        props.put(EventConstants.EVENT_FILTER, "(event-type=${SpagettEventConstants.EVENT_TYPE_EARNED_BADGE})".toString())
        return props
    }
}
