package com.coding.creditsuisse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventReader implements  Runnable{

    private static final Logger logger = LoggerFactory.getLogger(EventReader.class);
    @Autowired
    EventProcessor evtProcessor;

    String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    void readEventsFromFile(){

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                evtProcessor.records.add(line);
            }
        } catch (IOException e) {
            evtProcessor.setReadCompleted(true);
            logger.error("Exception occurred while reading file: "+ e.getMessage());
            e.printStackTrace();
        }finally {
            evtProcessor.setReadCompleted(true);
            logger.info("Reader Thread has completed processing!!");
        }
    }

    @Override
    public void run() {
        Thread evtProcessorThread = new Thread(evtProcessor);
        evtProcessorThread.start();
        readEventsFromFile();
    }
}
