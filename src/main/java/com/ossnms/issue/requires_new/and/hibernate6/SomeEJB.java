package com.ossnms.issue.requires_new.and.hibernate6;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SomeEJB {

    private static final Logger LOGGER = Logger.getLogger(SomeEJB.class.getSimpleName());

    @EJB
    private SomeEJB selfRef;

    @PersistenceContext
    private EntityManager em;

    private void dbCall() {
        em.createNativeQuery("SELECT 1 FROM DUAL", Long.class).getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void dbCallInNewTx() {
        dbCall();
    }

    public void someComplexStuff() {
        LOGGER.log(Level.INFO, "do someComplexStuff to trigger the error: 'IJ000152: Trying to return an unknown connection'");
        LOGGER.log(Level.INFO, "step1: db call in current tx");
        dbCall();
        LOGGER.log(Level.INFO, "step2:  db call in new tx");
        selfRef.dbCallInNewTx();
        LOGGER.log(Level.INFO, "someComplexStuff done");
    }

}
