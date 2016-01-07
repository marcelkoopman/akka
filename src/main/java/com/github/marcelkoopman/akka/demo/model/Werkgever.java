package com.github.marcelkoopman.akka.demo.model;

public class Werkgever {

	private final String naam;
	
	public String getNaam() {
		return naam;
	}

	public Werkgever(String naam) {
		this.naam = naam;
	}	
	
	@Override
	public String toString() {
		return this.naam;
	}
}
