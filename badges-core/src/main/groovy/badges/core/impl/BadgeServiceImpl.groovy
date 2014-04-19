package badges.core.impl

import aQute.bnd.annotation.component.Activate
import aQute.bnd.annotation.component.Component
import aQute.bnd.annotation.component.Reference
import badges.core.api.Badge
import badges.core.api.BadgeService
import badges.core.api.BadgeStore
import badges.core.events.SpagettEventPublisher
import org.osgi.service.component.ComponentContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component(provide = [BadgeService], immediate = true)
class BadgeServiceImpl implements BadgeService {
    static final Logger log = LoggerFactory.getLogger(BadgeServiceImpl)
    private List<Badge> badgeRegistry = []
    private BadgeStore earnedBadgeStore
    SpagettEventPublisher publisher

    @Reference
    void addEarnedBadgeStore(BadgeStore badgeStore) {
        earnedBadgeStore = badgeStore
    }

    @Override
    Badge fetchBadge(String badgeName) {
        return badgeRegistry.find { it.title == badgeName }
    }

    @Override
    List<Badge> fetchAllRegisteredBadges() {
        return badgeRegistry
    }

    @Override
    Map<String, List<Badge>> fetchEarnedBadges() {
        return earnedBadgeStore.fetchEarnedBadges()
    }

    @Override
    List<Badge> fetchEarnedBadgesForUser(String user) {
        return earnedBadgeStore.get(user)
    }

    @Override
    List<Badge> fetchUnearnedBadgesForUser(String user) {
        def userEarnedBadges = fetchEarnedBadgesForUser(user)
        return badgeRegistry.findAll { !userEarnedBadges.contains(it) }
    }

    @Override
    void registerBadge(Badge badge) {
        badgeRegistry.add(badge)
        log.debug("Registered Badge: ${badge.title}")
    }

    @Override
    synchronized void earnBadgeForUser(String user, String badgeName) {
        def badge = badgeRegistry.find { it.title == badgeName }
        def userEarnedBadges = earnedBadgeStore.get(user)
        if (!userEarnedBadges.contains(badge)) {
            userEarnedBadges.add(badge)
            earnedBadgeStore.put(user, userEarnedBadges)
            publisher.publishEarnedBadgeEvent(user, badge.title)
        }
    }

    @Override
    synchronized void removeBadgeForUser(String user, String badgeName) {
        def badge = badgeRegistry.find { it.title == badgeName }
        def userEarnedBadges = earnedBadgeStore.get(user)
        if (userEarnedBadges.contains(badge)) {
            userEarnedBadges.remove(badge)
            earnedBadgeStore.put(user, userEarnedBadges)
            log.info("Removed $badgeName badge for user $user")
        }
    }

    @Activate
    protected void activate(ComponentContext componentContext) {
        def bundleContext = componentContext.bundleContext
        publisher = new SpagettEventPublisher(context: bundleContext)
    }
}
