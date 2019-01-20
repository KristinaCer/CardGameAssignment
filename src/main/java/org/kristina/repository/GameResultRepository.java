package org.kristina.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.kristina.DbDriver;
import org.kristina.model.GameResult;
import org.kristina.model.PlayerType;

public class GameResultRepository {
	
	private final DbDriver driver;
 
	public GameResultRepository(DbDriver driver) {
		this.driver = driver;
	}
	
	public GameResult save(GameResult results) throws SQLException { 
		validate(results);
		
	    PreparedStatement statement = driver.getConnection().prepareStatement(
				"INSERT INTO  game_result (winner,numberOfRounds,statistics) VALUES (?, ?, ?)"
	    		, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, results.winner.name());
		statement.setInt(2, results.numberOfRounds);
		statement.setString(3, results.statisticsAsString());
		
		statement.executeUpdate();
		 
		ResultSet result = statement.getGeneratedKeys();  
		result.next();
		
		int id = result.getInt(1); 
		
		return get(id);
	}
	
	public GameResult get(int id) throws SQLException {
		if (id < 0) {
			throw new IllegalArgumentException("Id must be positive");
		}
		
		 PreparedStatement statement = driver.getConnection().prepareStatement(
				 "SELECT * FROM game_result WHERE id = ? ");
		 
		 statement.setInt(1, id);
		 
		 ResultSet result = statement.executeQuery();
		 
		 if (!result.next()) {
			 throw new IllegalStateException("Game result with id '"+id+"' does not exist");
		 }
		 
		 int resultId = result.getInt(1 );
		 PlayerType winner = PlayerType.valueOf(result.getString(2));
		 int numberOfRounds = result.getInt(3);
		 String statistics = result.getString(4);
		  
		 GameResult gameResult = new GameResult(winner, numberOfRounds, toStatisticsArray(statistics));
		 gameResult.id = resultId;
		 
		 return gameResult;
	}
	
	
	private void validate(GameResult results) {
		if (results.numberOfRounds < 1) {
			throw new IllegalArgumentException("Game must have at least one round");
		}
		if (results.winner == null) {
			throw new IllegalArgumentException("Game must have a defined winner");
		}
		if (results.winStatistics == null || results.winStatistics.length < 2) {
			throw new IllegalArgumentException("Game must have at least two players");
		}
	}
	
	 
	private int[] toStatisticsArray(String arg) {
		String[] split = arg.split(" ");
		int[] result = new int[split.length];
		
		for(int i = 0; i < split.length ; i++) {
			result[i] = Integer.parseInt( split[i]);
		}
		
		return result;
	} 
	
}
