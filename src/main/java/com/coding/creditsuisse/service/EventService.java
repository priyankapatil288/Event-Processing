package com.coding.creditsuisse.service;

import com.coding.creditsuisse.entity.Event;

import java.util.List;

public interface EventService {

    public void processEvents(String filePath);
    public Event getEvent(String eventId);

}
