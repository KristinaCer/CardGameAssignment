package org.kristina;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbDriver {

    private final String uri;
    private final String username;
    private final String password;
    private final Connection connection;

    public DbDriver(String username, String password, String uri) throws SQLException, ClassNotFoundException {
        this.username = username;
        this.uri = uri;
        this.password = password;


        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not fine JDBC Driver");
            e.printStackTrace();
            throw e;
        }

        try {
            this.connection = DriverManager.getConnection(uri, username,password);
        } catch (SQLException e) {
            System.out.println("Could not fine JDBC Driver");
            e.printStackTrace();
            throw e;
        }

    }

    public Connection getConnection() {
        return connection;
    }
} 