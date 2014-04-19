package badges.core.events

import aQute.bnd.annotation.component.Activate
import aQute.bnd.annotation.component.Component
import aQute.bnd.annotation.component.Reference
import badges.core.api.BadgeService
import badges.core.api.CommandBadge
import org.osgi.service.component.ComponentContext
import org.osgi.service.event.Event
import org.osgi.service.event.EventConstants
import org.osgi.service.event.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component(provide = [], immediate = true)
class CommandEventHandler implements EventHandler {
    static final Logger log = LoggerFactory.getLogger(CommandEventHandler)

    BadgeService badgeService

    @Reference
    void addBadgeService(BadgeService service) {
        badgeService = service
    }

    @Override
    void handleEvent(Event event) {
        String command = event.getProperty('command')
        String user = event.getProperty('user')

        log.debug("command: $command, user: $user")

        def unearned = badgeService.fetchUnearnedBadgesForUser(user)

        unearned.each { badge ->
            if (badge instanceof CommandBadge && command.matches(badge.command)) {
                badgeService.earnBadgeForUser(user, badge.title)
            }
        }

    }

    @Activate
    protected void activate(ComponentContext componentContext) {
        def bundleContext = componentContext.bundleContext
        Dictionary props = buildEventHandlerProps()
        bundleContext.registerService(EventHandler, this, props)
    }

    static def buildEventHandlerProps() {
        String[] topics = [SpagettEventConstants.SPAGETT_TOPIC].toArray(new String[0])
        Dictionary props = new Hashtable()
        props.put(EventConstants.EVENT_TOPIC, topics)
        props.put(EventConstants.EVENT_FILTER, "(event-type=${SpagettEventConstants.EVENT_TYPE_COMMAND})".toString())
        return props
    }
}
