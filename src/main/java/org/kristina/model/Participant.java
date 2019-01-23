package org.kristina.model;

public class Participant {

	public Participant() {}
	
	public Participant(Player player, int roundsWon) {
		super();
		this.player = player;
		this.roundsWon = roundsWon;
	}
	public int id;
	public Player player;
	public GameResult game;
	public int roundsWon;
	
}
