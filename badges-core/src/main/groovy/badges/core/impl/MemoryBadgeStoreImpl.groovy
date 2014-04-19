package badges.core.impl

import aQute.bnd.annotation.component.Component
import badges.core.api.Badge
import badges.core.api.BadgeStore

@Component(provide = [BadgeStore])
class MemoryBadgeStoreImpl implements BadgeStore {

    Map<String, List<Badge>> earnedBadgeStore = [:]

    @Override
    Map<String, List<Badge>> fetchEarnedBadges() {
        return earnedBadgeStore ?: [:]
    }

    @Override
    void put(String user, List<Badge> badges) {
        earnedBadgeStore.put(user, badges)
    }

    @Override
    List<Badge> get(String user) {
        earnedBadgeStore.get(user) ?: []
    }
}
