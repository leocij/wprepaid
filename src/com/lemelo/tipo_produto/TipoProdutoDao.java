package com.lemelo.tipo_produto;

import com.lemelo.util.BibliotecaString;
import com.lemelo.util.FabricaConexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TipoProdutoDao {
    public void insert(TipoProduto tipoProduto) {
        String tipoProdutoSqlInsert = "INSERT INTO TIPO_PRODUTO (TIPO_PRODUTO) VALUES (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(new BibliotecaString().urlBanco(),"SA","");
            preparedStatement = connection.prepareStatement(tipoProdutoSqlInsert);
            preparedStatement.setString(1, tipoProduto.getTipoProduto());
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

    public ObservableList<TipoProduto> buscaTodasTipoProdutos() throws SQLException {
        ObservableList<TipoProduto> tipoProdutos = FXCollections.observableArrayList();
        String tipoProdutoSqlSelect = "SELECT * FROM TIPO_PRODUTO ORDER BY ID DESC";
        ResultSet resultSet = new FabricaConexao().getResultSet(tipoProdutoSqlSelect);
        while (resultSet.next()) {
            TipoProduto tipoProduto = new TipoProduto();
            tipoProduto.setId(resultSet.getLong("ID"));
            tipoProduto.setTipoProduto(resultSet.getString("TIPO_PRODUTO"));

            tipoProdutos.add(tipoProduto);
        }
        resultSet.close();
        return tipoProdutos;
    }
}
