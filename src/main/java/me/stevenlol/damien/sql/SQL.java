package me.stevenlol.damien.sql;

import lombok.SneakyThrows;
import me.stevenlol.damien.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    @SneakyThrows
    public SQL() {
        this.connect();
        this.createDatabases();
    }

    private final String host = Main.getPlugin().getConfig().getString("database.host");
    private final int port = Main.getPlugin().getConfig().getInt("database.port");
    private final String database = Main.getPlugin().getConfig().getString("database.database");
    private final String username = Main.getPlugin().getConfig().getString("database.username");
    private final String password = Main.getPlugin().getConfig().getString("database.password");
    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    private void createDatabases() throws SQLException {
        this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS damien_player_bans (" +
                "UUID VARCHAR(100)," +
                "BANNER VARCHAR(100)," +
                "REASON VARCHAR(255)," +
                "DURATION INT," +
                "DATE VARCHAR(100))");
    }

    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
                System.out.println("Connected to database successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    public void disconnect() {
        if (isConnected()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @SneakyThrows
    public PreparedStatement createStatement(String sql) {
        return this.connection.prepareStatement(sql);
    }

}
