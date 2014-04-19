package badges.core.api

/**
 * Created by gbutt on 4/19/14.
 */
public interface BadgeStore {
    Map<String, List<Badge>> fetchEarnedBadges()

    void put(String user, List<Badge> badge)

    List<Badge> get(String user)
}