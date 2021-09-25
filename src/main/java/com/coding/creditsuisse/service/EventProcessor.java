package com.coding.creditsuisse.service;

import com.coding.creditsuisse.dao.EventDao;
import com.coding.creditsuisse.entity.Event;
import com.coding.creditsuisse.entity.ServerData;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EventProcessor implements  Runnable{

    @Autowired
    private EventDao eventDao;
    private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);

    // Queue capacity is random as of now but needs to have specifc value once we find out Read and Processor thread processing speed
    BlockingQueue<String> records= new ArrayBlockingQueue<>(1024);
    Map<String, ServerData> serverDataMap = new HashMap<>();

    //Flag for Event Processor to know if reading is completed
    boolean isReadCompleted = false;
    final String EVENT_FINISHED = "FINISHED";
    final long completionTimeAllowed =4;

    public void setReadCompleted(boolean readCompleted) {
        isReadCompleted = readCompleted;
    }

    @Override
    public void run() {

        while(true){
            String event = null;
            try {
                event = records.poll();

                if(event == null && isReadCompleted) {
                    logger.info("Event processor has processed the complete file!!");
                    break;
                }else if(event==null) {
                    logger.info("No data found for processing, will try after sometime");
                    Thread.sleep(1000); // Wait till data is available for processing
                    continue;
                }
            ServerData curData = getEventObject(event, ServerData.class);

            if(serverDataMap.get(curData.getId())==null){
                serverDataMap.put(curData.getId(),curData);
            }else{
                long duration =0L;
                ServerData oldData= serverDataMap.get(curData.getId());

                if(curData.getState().equals(EVENT_FINISHED))
                    duration = curData.getTimestamp() - oldData.getTimestamp();
                else
                    duration = oldData.getTimestamp() - curData.getTimestamp();

                Event eventCompleted=convertServerDataToEvent(curData,duration);
                eventDao.save(eventCompleted);
                serverDataMap.remove(curData.getId()); // remove entry from map to reduce memory footprint
            }

            } catch (Exception e) {
                logger.error("Error occurred while processing event data" + e.getMessage());
                e.printStackTrace();
                break; //We can set no. of retries and then only do break
            }
        }
    }

    private <T> T getEventObject(String stringObj, Class<T> classObj)
    {
        Gson gson = new Gson();
        return gson.fromJson(stringObj, classObj);
    }

    private Event convertServerDataToEvent(ServerData serverData, long duration){
        Event evt = new Event();
        evt.setEventId(serverData.getId());
        evt.setDuration(duration);
        evt.setType(serverData.getType());
        evt.setHost(serverData.getHost());

        if( duration > completionTimeAllowed)
            evt.setAlert(true);
        else
            evt.setAlert(false);
        return evt;
    }
}
