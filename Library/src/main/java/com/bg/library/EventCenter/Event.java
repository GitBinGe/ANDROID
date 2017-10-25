package com.bg.library.EventCenter;


/**
 * Created by BinGe on 2016/12/6.
 */

public class Event {

    public EventData data;

    public String event;

    public Event() {
        this(null);
    }

    public Event(String event) {
        this(event, new Object());
    }

    public Event(String event, Object data) {
        this(event, new EventData(data));
    }

    public Event(String event, EventData data) {
        this.event = event;
        this.data = data;
    }

    @Override
    public String toString() {
        return event + " : " + data;
    }
}
