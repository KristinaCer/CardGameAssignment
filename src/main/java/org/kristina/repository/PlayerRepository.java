package org.kristina.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.kristina.DbDriver;
import org.kristina.model.GameResult;
import org.kristina.model.Player;
import org.kristina.model.PlayerType;

public class PlayerRepository {

	private final DbDriver driver;

	public PlayerRepository(DbDriver driver) {
		this.driver = driver;
	}

	/**
	 * 
	 * @param entry player to save
	 * @return saved player entry from the database
	 * @throws SQLException
	 */
	public Player save(Player entry) throws SQLException { // ???
		validate(entry);

		Connection con = driver.getConnection();
		try {

			PreparedStatement statement = con.prepareStatement("INSERT INTO  player (name, type) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, entry.name);
			statement.setString(2, entry.type.name());

			statement.executeUpdate();

			ResultSet result = statement.getGeneratedKeys();

		    if (!result.next()) {
		    	throw new IllegalStateException("Sql returned no rows");
		    }

			int id = result.getInt(1);

			return get(id);

		} finally {
			con.close();
		}
	}

	public Player get(int id) throws SQLException {
		if (id < 0) {
			throw new IllegalArgumentException("Id must be positive");
		}

		Connection con = driver.getConnection();

		try {

			PreparedStatement statement = con.prepareStatement("SELECT * FROM player WHERE id = ? ");

			statement.setInt(1, id);

			ResultSet result = statement.executeQuery();

			if (!result.next()) {
				throw new IllegalStateException("Game result with id '" + id + "' does not exist");
			}

			int resultId = result.getInt(1);
			String name = result.getString(2);
			PlayerType type = PlayerType.valueOf(result.getString(3));

			Player player = new Player(name, type);
			player.id = resultId;

			return player;
		} finally {
			con.close();
		}
	}

	private void validate(Player player) {
		if (player.name == null) {
			throw new IllegalArgumentException("Player must have a name");
		}
		if (player.type == null) {
			throw new IllegalArgumentException("Player must have a type");
		}
	}

}
