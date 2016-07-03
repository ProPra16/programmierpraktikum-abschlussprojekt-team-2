package de.hhu.propra16.tddtrainer.events;

import com.google.common.eventbus.EventBus;

public class MyEventBus extends EventBus {
	
	private MyEventBus() {
     }

     private static final  MyEventBus INSTANCE = new MyEventBus();

     public static MyEventBus getInstance() {
        return INSTANCE;
     }


}
