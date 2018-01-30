package com.fesskiev.programmingsamples.patterns.structural.facade;

public class BillingSystem {

	public Bill createBill(Integer amount){

		// Let's assume some advanced logic happens here;
		return new Bill(amount);
		
	}
	
}
