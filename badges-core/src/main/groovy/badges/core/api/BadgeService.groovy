package badges.core.api

/**
 * Created by gbutt on 4/19/14.
 */
public interface BadgeService {
    Badge fetchBadge(String badgeName)

    List<Badge> fetchAllRegisteredBadges()

    Map<String, List<Badge>> fetchEarnedBadges()

    List<Badge> fetchEarnedBadgesForUser(String user)

    List<Badge> fetchUnearnedBadgesForUser(String user)

    void registerBadge(Badge badge)

    void earnBadgeForUser(String user, String badgeName)

    void removeBadgeForUser(String user, String badgeName)
}