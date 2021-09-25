package com.coding.creditsuisse.dao;

import com.coding.creditsuisse.entity.Event;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EventDaoImpl implements  EventDao{

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Event getEvent(String eventId) {
        Event evt=null;
        Session session = entityManager.unwrap(Session.class);
        Query query=session.createQuery("FROM Event ev where ev.eventId = :eventId");
        query.setParameter("eventId", eventId);
        List lst= query.list();
        if(null!=lst && !lst.isEmpty()) {
            evt = (Event) lst.get(0);
        }
        return evt;
    }

    @Override
    @Transactional
    public void save(Event event) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(event);
    }
}
