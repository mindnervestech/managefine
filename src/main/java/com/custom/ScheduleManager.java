package com.custom;

import static org.joda.time.Seconds.secondsBetween;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;

public class ScheduleManager {

	private static Map<Long,List<Cancellable>> scheduleMap = new HashMap<Long,List<Cancellable>>();
	
	public static void reSchedule(Map<Long,List<DateTime>> _notificationMap){
		
		for(Long _key:_notificationMap.keySet()){
			reSchedule(_notificationMap.get(_key),_key);
		}
	}
	
	public static void reSchedule(List<DateTime> plannedTimes,Long forId){
		
		DateTime now = new DateTime();
		
		List<Cancellable> jobs = scheduleMap.get(forId);
        
        if(jobs !=null){
        	for(Cancellable job:jobs){
        		job.cancel();
        	}
        	scheduleMap.remove(forId);
        }else{
        	jobs = new ArrayList<Cancellable>();
        }
        
        ActorSystem system = ActorSystem.create();
        for(DateTime plannedTime:plannedTimes){
        	final int min = plannedTime.getMinuteOfHour();
	        DateTime nextRun = (now.isAfter(plannedTime))
	                ? plannedTime.plusWeeks(1)
	                : plannedTime;
	                
	        
	        Long nextRunInSeconds = (long) secondsBetween(now, nextRun).getSeconds();
	        Cancellable cancellable =  system.scheduler().schedule(
	                Duration.create(nextRunInSeconds, TimeUnit.SECONDS),
	                Duration.create(10, TimeUnit.SECONDS) ,
	                new Runnable() {
	                    public void run() {
	                    	// Get Email address of all the user who have unsubmitted time sheet for all past week from now for particular company id.
	                    }
	                }, system.dispatcher()
	        );
	        jobs.add(cancellable);
        }
        scheduleMap.put(forId, jobs);
        
                        
		
	}

}
