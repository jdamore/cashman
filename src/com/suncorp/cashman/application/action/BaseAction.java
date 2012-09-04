package com.suncorp.cashman.application.action;

import com.opensymphony.xwork2.ActionSupport;
import com.suncorp.cashman.infrastructure.spring.SpringAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseAction extends ActionSupport {

    protected ApplicationContext context;

    public BaseAction() {
        this.context = new ClassPathXmlApplicationContext(SpringAppContext.PATH);
    }
}
