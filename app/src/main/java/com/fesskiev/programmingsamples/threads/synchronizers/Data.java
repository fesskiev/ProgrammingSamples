package com.fesskiev.programmingsamples.threads.synchronizers;

import java.util.Deque;
import java.util.LinkedList;

public class Data<T> {

    private Deque<T> data = new LinkedList<>();

    public void addData(T d) {
        data.add(d);
    }

    public T getData() {
        return data.pop();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
