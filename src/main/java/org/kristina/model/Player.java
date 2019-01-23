package org.kristina.model;

public class Player {

	public Player(String name, PlayerType type) {
		super();
		this.name = name;
		this.type = type;
	}
	
	public int id;
	public String name;
	public PlayerType type;
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", type=" + type + "]";
	} 
	
}
