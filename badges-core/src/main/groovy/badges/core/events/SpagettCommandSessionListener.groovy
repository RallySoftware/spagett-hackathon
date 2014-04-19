package badges.core.events

import org.apache.felix.gogo.api.CommandSessionListener
import org.apache.felix.service.command.CommandSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by gbutt on 4/18/14.
 */
class SpagettCommandSessionListener implements CommandSessionListener {
    static final Logger log = LoggerFactory.getLogger(SpagettCommandSessionListener)

    @Override
    void beforeExecute(CommandSession session, CharSequence command) {

        log.info("Before Execute - Command: $command, User: ${session.get('USER')}")

    }

    @Override
    void afterExecute(CommandSession session, CharSequence command, Exception ex) {
        log.info("After Execute Exception: $command, ${ex.class.name}")
    }

    @Override
    void afterExecute(CommandSession session, CharSequence command, Object o) {
        log.info("After Execute Object: $command, ${o.class.name}")
    }
}
