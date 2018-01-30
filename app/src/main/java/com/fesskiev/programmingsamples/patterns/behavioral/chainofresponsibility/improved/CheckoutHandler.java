package com.fesskiev.programmingsamples.patterns.behavioral.chainofresponsibility.improved;


public abstract class CheckoutHandler {

    protected CheckoutHandler nextHandler;

    public abstract void handlCheckout();


    public void setNextHandler(CheckoutHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
