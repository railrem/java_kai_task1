package ru.kai.dao.impl;

import org.hibernate.Session;
import ru.kai.dao.CrudDao;
import ru.kai.models.Event;
import java.util.List;
import java.util.Optional;

public class EventDaoHibernateImpl implements CrudDao<Event> {

    private Session session;

    public EventDaoHibernateImpl(Session sessionFactory) {
        this.session = sessionFactory;
    }

    @Override
    public Optional<Event> find(Integer id) {
        Event event = session.createQuery("from Event event where event.id = " + id, Event.class).getSingleResult();
        return Optional.of(event);
    }

    @Override
    public Integer save(Event model) {
        return null;
    }

    @Override
    public void update(Event model) {

    }

    @Override
    public void delete(Integer id) {
        session.delete(find(id));
    }

    @Override
    public List<Event> findAll() {
        return session.createQuery("from Event event", Event.class).getResultList();
    }
}
