package com.fesskiev.programmingsamples.patterns.structural.decorator;


public class ScrollbarWindow extends Window {

    @Override
    public void draw() {
        System.out.println("Draw scrollbar");
        super.draw();
    }
}
