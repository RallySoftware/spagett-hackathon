package badges.core.events

import org.osgi.service.event.Event
import org.osgi.service.event.EventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by gbutt on 4/18/14.
 */
class SpagettEventHandler implements EventHandler {
    static final Logger log = LoggerFactory.getLogger(SpagettEventHandler)

    public void handleEvent(Event event) {
        String command = event.getProperty('command')
        log.info("Spagett Event Handler: $command")
    }
}
