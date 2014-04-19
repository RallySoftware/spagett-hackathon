package badges.core.events

import aQute.bnd.annotation.component.Activate
import aQute.bnd.annotation.component.Component
import aQute.bnd.annotation.component.Deactivate
import org.apache.felix.gogo.api.CommandSessionListener
import org.apache.felix.gogo.runtime.CommandProcessorImpl
import org.apache.felix.service.command.CommandProcessor
import org.apache.felix.service.command.CommandSession
import org.osgi.service.component.ComponentContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component(provide = [], immediate = true)
class SpagettCommandSessionListener implements CommandSessionListener {
    static final Logger log = LoggerFactory.getLogger(SpagettCommandSessionListener)
    SpagettEventPublisher spagettEventPublisher

    @Override
    void beforeExecute(CommandSession session, CharSequence charSequence) {

        def command = charSequence.toString()
        def user = session.get('USER').toString()

        // override multiline commands
        // these are the result of logins and perhaps other dubious events we don't want to publish
        if (command.contains('\n')) {
            command = 'login'
        }

        def properties = [
                user   : user,
                command: command
        ]
        spagettEventPublisher.publishEvent(SpagettEventConstants.EVENT_TYPE_COMMAND, properties)

        log.debug(properties.toMapString())
    }

    @Override
    void afterExecute(CommandSession session, CharSequence command, Exception ex) {

    }

    @Override
    void afterExecute(CommandSession session, CharSequence command, Object o) {

    }

    @Activate
    protected void activate(ComponentContext componentContext) {
        def bundleContext = componentContext.bundleContext
        def commandProcessor = bundleContext.getService(bundleContext.getServiceReference(CommandProcessor)) as CommandProcessorImpl
        commandProcessor.addListener(this)
        spagettEventPublisher = new SpagettEventPublisher(context: bundleContext)
        log.info('Added Spagett Listener')
    }

    @Deactivate
    protected void deactivate(ComponentContext componentContext) {
        def bundleContext = componentContext.bundleContext
        def commandProcessor = bundleContext.getService(bundleContext.getServiceReference(CommandProcessor)) as CommandProcessorImpl
        commandProcessor.removeListener(this)
        log.info('Removed Spagett Listener')
    }
}
