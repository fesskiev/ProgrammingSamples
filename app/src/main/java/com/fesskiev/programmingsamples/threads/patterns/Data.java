package com.fesskiev.programmingsamples.threads.patterns;

public class Data {

    private int data;
    private boolean produce = true;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isProduce() {
        return produce;
    }

    public void setProduce(boolean produce) {
        this.produce = produce;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
