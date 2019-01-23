package org.kristina.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.kristina.DbDriver;
import org.kristina.model.GameResult;
import org.kristina.model.Participant;
import org.kristina.model.PlayerType;

public class GameResultRepository {

	private final DbDriver driver;

	public GameResultRepository(DbDriver driver) {
		this.driver = driver;
	}
  
	public int getLargestNumberOfRounds() throws SQLException { 
		Connection con = driver.getConnection();
		try {
			
			ResultSet result = con.prepareStatement("SELECT max(numberOfRounds) FROM game_result").executeQuery();
			
		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    }
		    
		    return result.getInt(1);
			
		} finally {
			con.close();
		}
	}
	
	public int getNumberOfDraws() throws SQLException { 
		Connection con = driver.getConnection();
		try {
			
			ResultSet result = con.prepareStatement("SELECT count(*) FROM game_result WHERE winner IS NULL").executeQuery();
			
		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    }
		    
		    return result.getInt(1);
			
		} finally {
			con.close();
		}
	}
	
	public int getWinsByPlayerType(PlayerType type) throws SQLException {
		Connection con = driver.getConnection();
		try {
			
			PreparedStatement statement = con.prepareStatement("SELECT count(*) FROM game_result as G JOIN PLAYER AS P ON G.winner = P.id WHERE p.type = ?");
					 
			statement.setString(1, type.name());
			
			ResultSet result = statement.executeQuery(); 
			
		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    }
		    
		    return result.getInt(1);
			
		} finally {
			con.close();
		}
	}
	
	public int totalGames() throws SQLException {
		Connection con = driver.getConnection();
		try {
			
			ResultSet result = con.prepareStatement("SELECT count(*) FROM game_result").executeQuery();
			
		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    }
		    
		    return result.getInt(1);
			
		} finally {
			con.close();
		}
	}
	
	/**
	 * 
	 * @param results
	 * @return
	 * @throws SQLException
	 * 
	 *                      Save method allows create an object (?) and save it to
	 *                      the data base.
	 */

	public int save(GameResult game) throws SQLException {
		validate(game);

		int id;
		Connection con = driver.getConnection();
		try {
			con.setAutoCommit(false);

			PreparedStatement createGameStatement = con.prepareStatement(
					"INSERT INTO  game_result (winner,numberOfRounds) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			// Null means a draw
			if (game.winner != null) {
			    createGameStatement.setInt(1, game.winner.id);
			} else {
				createGameStatement.setNull(1, Types.INTEGER);
			}
			createGameStatement.setInt(2, game.numberOfRounds); 

			createGameStatement.executeUpdate();

			ResultSet result = createGameStatement.getGeneratedKeys();

		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    } 
			id = result.getInt(1);
			
			for (Participant participant : game.participants) {

				PreparedStatement createParticipantStatement = con.prepareStatement(
						"INSERT INTO  participation (roundsWon,playerId,gameId) VALUES (?, ?, ?)" );
				createParticipantStatement.setInt(1, participant.roundsWon); 
			    createParticipantStatement.setInt(2, participant.player.id);  
				createParticipantStatement.setInt(3, id); 
				createParticipantStatement.executeUpdate();
			}
			
			con.commit();

			return id;
			
		} catch (Exception e) {
			con.rollback();
			throw e;
		} finally {
			con.close();
		} 
 
	} 

	private void validate(GameResult results) {
		if (results.numberOfRounds < 1) {
			throw new IllegalArgumentException("Game must have at least one round");
		} 
		if (results.participants == null || results.participants.size() < 2) {
			throw new IllegalArgumentException("Game must have at least two players");
		}
	}

}
