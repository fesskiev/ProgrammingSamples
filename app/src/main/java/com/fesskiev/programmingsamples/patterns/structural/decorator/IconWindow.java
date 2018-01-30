package com.fesskiev.programmingsamples.patterns.structural.decorator;


public class IconWindow extends Window {

    @Override
    public void draw() {
        System.out.println("Draw Icon");
        super.draw();
    }
}
