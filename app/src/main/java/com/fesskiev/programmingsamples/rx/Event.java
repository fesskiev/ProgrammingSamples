package com.fesskiev.programmingsamples.rx;


public class Event {

    private long tag;

    public Event(long tag) {
        this.tag = tag;
    }

    public long getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Event{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
