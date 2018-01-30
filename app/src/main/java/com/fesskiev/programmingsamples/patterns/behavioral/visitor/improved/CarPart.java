package com.fesskiev.programmingsamples.patterns.behavioral.visitor.improved;


public abstract class CarPart {

    void acceptCarPartVisitor(CarPartVisitor visitor){
        visitor.visit(this);
    }

}
