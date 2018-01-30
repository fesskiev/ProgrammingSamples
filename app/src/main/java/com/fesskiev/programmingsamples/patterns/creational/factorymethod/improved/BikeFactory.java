package com.fesskiev.programmingsamples.patterns.creational.factorymethod.improved;

public class BikeFactory extends TransportFactory{
	Transport create() {
		return new Bike();
	}
}
