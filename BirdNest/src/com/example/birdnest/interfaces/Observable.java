package com.example.birdnest.interfaces;

public interface Observable 
{
	public void notifyObservers(Observer o);
	public void register(Observer obs);
	public void unRegister(Observer obs);
}
