package badges.mbean.impl

import javax.management.MBeanServer
import javax.management.ObjectName
import java.lang.management.ManagementFactory

class MBeanRegister {
    String name = "badges.mbean:type=Provider"

    void init() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName(name);
        mbs.registerMBean(new ProviderMBeanImpl(), name);
    }

    void destroy() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.unregisterMBean(name)
    }
}
