package com.example.location;

import com.zaxxer.hikari.pool.HikariProxyConnection;
import org.geolatte.geom.codec.db.oracle.ConnectionFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomConnectionFinder implements ConnectionFinder {
    private static final Logger logger = LoggerFactory.getLogger(CustomConnectionFinder.class);

    @Override
    public Connection find(Connection connection) {
        try {
            return (Connection) ((HikariProxyConnection) connection).unwrap( Class.forName("oracle.jdbc.driver.OracleConnection"));
        } catch (SQLException e) {
            logger.error("CustomConnectionFinder: error={}", e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
