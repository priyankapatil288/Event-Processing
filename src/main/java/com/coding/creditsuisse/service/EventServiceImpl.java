package com.coding.creditsuisse.service;

import com.coding.creditsuisse.dao.EventDao;
import com.coding.creditsuisse.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public void processEvents(String filePath) {
        EventReader evtReader = getEventReader();
        evtReader.setFilePath(filePath);
        Thread evtReaderThread = new Thread(evtReader);
        evtReaderThread.start();
    }

    @Override
    public Event getEvent(String eventId) {
        return eventDao.getEvent(eventId);
    }

    @Lookup
    public EventReader getEventReader(){
        return null;
    }


}
