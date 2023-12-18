package com.ossnms.issue.requires_new.and.hibernate6;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

@Singleton
@Startup
public class StartupTrigger {

    @EJB
    private SomeEJB someEjb;


    @PostConstruct
    void startup(){
        someEjb.someComplexStuff();
    }

}
