package com.fesskiev.programmingsamples.patterns.creational.singleton.improved;

public class Client {

	public static void main(String [] args){
		
		Preferences.getInstance().helloSingleton();


        MyPreferences.getInstance().helloSingleton();
		
	}
	
}
