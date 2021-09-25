package com.coding.creditsuisse.controller;

import com.coding.creditsuisse.entity.Event;
import com.coding.creditsuisse.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    EventService eventService;

    @PostMapping("/filepath" )
    public ResponseEntity<String> readFilePathForEventProcessing(@RequestParam("filePath") String filePath){
        logger.info("File path for processing: "+ filePath);
        eventService.processEvents(filePath);
       return new ResponseEntity<>("Processing has started", HttpStatus.ACCEPTED);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable("eventId") String eventId){
        logger.info("Event data requested for event id: "+ eventId);
        Event evt = eventService.getEvent(eventId);
        return new ResponseEntity<>(evt, HttpStatus.OK);
    }



}
