package com.example.batchprocessing;

public class Person {

	public Person(String name, String owner, String date) {
		super();
		this.name = name;
		this.owner = owner;
		this.date = date;
	}
	private String name;
	private String owner;
	private String date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", owner=" + owner + ", date=" + date + "]";
	}
	
}
