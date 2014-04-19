package badges.core.activator

import badges.core.events.SpagettCommandSessionListener
import org.apache.felix.gogo.runtime.CommandProcessorImpl
import org.apache.felix.service.command.CommandProcessor
import org.osgi.framework.BundleActivator
import org.osgi.framework.BundleContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by gbutt on 4/16/14.
 */
class Activator implements BundleActivator {
    static final Logger log = LoggerFactory.getLogger(Activator)
    def listener = new SpagettCommandSessionListener()

    @Override
    void start(BundleContext context) throws Exception {
//        // OSGi Events: http://felix.apache.org/documentation/subprojects/apache-felix-event-admin.html
//        String[] topics = ['org/apache/felix/service/command/EXECUTING'].toArray(new String[0])
//        Dictionary props = new Hashtable()
//        props.put(EventConstants.EVENT_TOPIC, topics)
//        context.registerService(EventHandler.class.getName(), new SpagettEventHandler() , props)

        CommandProcessorImpl commandProcessor = context.getService(context.getServiceReference(CommandProcessor))
        commandProcessor.addListener(listener)
        log.info('Added Spagett Listener')
    }

    @Override
    void stop(BundleContext context) throws Exception {
        CommandProcessorImpl commandProcessor = context.getService(context.getServiceReference(CommandProcessor))
        commandProcessor.removeListener(listener)
        log.info('Removed Spagett Listener')
    }
}
