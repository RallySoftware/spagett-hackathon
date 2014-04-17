package badges.mbean.impl

import badges.mbean.api.ProviderMBean

import javax.management.NotCompliantMBeanException
import javax.management.StandardMBean

class ProviderMBeanImpl extends StandardMBean implements ProviderMBean {

    ProviderMBeanImpl() throws NotCompliantMBeanException {
        super(ProviderMBean.class)
    }

    @Override
    String getData() {
        return "These are not the droids you're looking for."
    }

}
