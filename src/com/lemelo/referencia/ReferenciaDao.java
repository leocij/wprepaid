package com.lemelo.referencia;

import com.lemelo.util.BibliotecaString;
import com.lemelo.util.FabricaConexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ReferenciaDao {
    public void insert(Referencia referencia) {
        String referenciaSqlInsert = "insert into referencia (referencia) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(new BibliotecaString().urlBanco(),"SA","");
            preparedStatement = connection.prepareStatement(referenciaSqlInsert);
            preparedStatement.setString(1, referencia.getReferencia());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Referencia> buscaTodasReferencias() throws SQLException {
        ObservableList<Referencia> referencias = FXCollections.observableArrayList();
        String referenciaSqlSelect = "SELECT * FROM REFERENCIA ORDER BY ID DESC";
        ResultSet resultSet = new FabricaConexao().getResultSet(referenciaSqlSelect);
        while (resultSet.next()) {
            Referencia referencia = new Referencia();
            referencia.setId(resultSet.getLong("id"));
            referencia.setReferencia(resultSet.getString("referencia"));

            referencias.add(referencia);
        }
        resultSet.close();
        return referencias;
    }
}
