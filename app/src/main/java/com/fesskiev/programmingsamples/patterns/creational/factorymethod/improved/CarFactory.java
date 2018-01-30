package com.fesskiev.programmingsamples.patterns.creational.factorymethod.improved;

public class CarFactory extends TransportFactory{
	Transport create() {
		return new Car();
	}
}
