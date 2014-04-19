package badges.core.commands

import badges.core.api.BadgeService
import badges.core.impl.CommandBadgeImpl
import org.apache.felix.gogo.commands.Argument
import org.apache.felix.gogo.commands.Command
import org.apache.karaf.shell.console.OsgiCommandSupport

import java.util.regex.Pattern

@Command(scope = "badge", name = "register", description = "Create a new badge for the command")
class RegisterCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "badge",
            description = "The name of the badge you want to create",
            required = true, multiValued = false)
    String badgeName = null;

    @Argument(index = 1, name = "command",
            description = "The command that will earn this badge",
            required = true, multiValued = false)
    String command = null;

    @Argument(index = 2, name = "force",
            description = "Force this badge to register",
            required = false, multiValued = false)
    Boolean force = false;

    @Override
    protected Object doExecute() throws Exception {

        def badgeService = super.getService(BadgeService, super.bundleContext.getServiceReference(BadgeService))

        def badge = badgeService.fetchBadge(badgeName)
        if (badge != null && !force) {
            println "The badge called '$badgeName' already exists"
            return null
        }

        badge = new CommandBadgeImpl(title: badgeName, command: Pattern.compile(command))
        badgeService.registerBadge(badge)
        println "New badge registered"
        return null
    }
}
