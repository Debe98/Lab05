package it.polito.tdp.anagrammi.model;

import java.util.*;

public class Parola {
	StringBuffer parola;

	
	public void addLettera (char c) {
		parola.append(c);
	}
	
	public void removeLettera (char c) {
		parola.deleteCharAt(parola.length()-1);
	}
	
	@Override
	public String toString() {
		return parola.toString();
	}
	
	
	
	
}
