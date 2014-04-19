package badges.core.commands

import badges.core.api.BadgeService
import org.apache.felix.gogo.commands.Argument
import org.apache.felix.gogo.commands.Command
import org.apache.karaf.shell.console.OsgiCommandSupport

@Command(scope = "badge", name = "earn", description = "Earns a badge, the easy way")
class EarnCommand extends OsgiCommandSupport {

    @Argument(index = 0, name = "badge",
            description = "The name of the badge you want to 'earn'",
            required = true, multiValued = false)
    String badgeName = null;

    @Override
    protected Object doExecute() throws Exception {
        def badgeService = super.getService(BadgeService, super.bundleContext.getServiceReference(BadgeService))
        def badge = badgeService.fetchBadge(badgeName)
        if (badge == null) {
            println "There is no badge called '$badgeName'"
            return null
        }
        def user = session.get('USER').toString()
        badgeService.earnBadgeForUser(user, badgeName)
    }
}
