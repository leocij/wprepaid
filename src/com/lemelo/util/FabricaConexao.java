package com.lemelo.util;

import java.sql.*;

public class FabricaConexao {
    public void createTables() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(new BibliotecaString().urlBanco(),"SA","");
            statement = connection.createStatement();
            statement.executeUpdate(new BibliotecaString().createReferenciaTable());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getResultSet(String sqlSelect) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(new BibliotecaString().urlBanco(),"SA","");
            statement = connection.createStatement();
            return statement.executeQuery(sqlSelect);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
