package com.suncorp.cashman.infrastructure;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Utility for obtaining the different Spring contextes.
 *
 * @author jean.damore
 * @since 22-Aug-2011
 */
public class SpringContextFactory {

    public static final String SPRING_APP_CONTEXT_DEV = "spring-context-dev-xml";
    public static final String SPRING_APP_CONTEXT_TEST = "spring-context-test-xml";
    public static final String SPRING_APP_CONTEXT_PROD = "spring-context-prod-xml";

    public enum Context { DEV, TEST, PROD }

    public static XmlBeanFactory getXmlBeanFactory(Context context) {

        ClassPathResource res;
        switch(context) {
            case DEV:   res = new ClassPathResource(SPRING_APP_CONTEXT_DEV);
            case TEST:  res = new ClassPathResource(SPRING_APP_CONTEXT_TEST);
            case PROD:  res = new ClassPathResource(SPRING_APP_CONTEXT_PROD);
            default:    res = new ClassPathResource(SPRING_APP_CONTEXT_TEST);
        }
        return new XmlBeanFactory(res);
    }
}
