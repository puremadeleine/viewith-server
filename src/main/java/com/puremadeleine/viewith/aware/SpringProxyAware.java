package com.puremadeleine.viewith.aware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class SpringProxyAware<T> {
    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected T getProxy() {
        return (T) this.applicationContext.getBean(this.getClass());
    }
}