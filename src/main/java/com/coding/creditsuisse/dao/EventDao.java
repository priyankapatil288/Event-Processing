package com.coding.creditsuisse.dao;

import com.coding.creditsuisse.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventDao {

    public Event getEvent(String eventId);

    public void save(Event evt);

}
