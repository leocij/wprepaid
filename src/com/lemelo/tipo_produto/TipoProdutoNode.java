package com.lemelo.tipo_produto;

import com.lemelo.util.Flag;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class TipoProdutoNode {
    private TextField tipoProdutoTextField;
    private Flag flag;

    public Node executar(Tab tipoProdutoTab) throws SQLException {

        tipoProdutoTab.setOnSelectionChanged(e->{
            if(tipoProdutoTab.isSelected()==true) {
                Platform.runLater(()->tipoProdutoTextField.requestFocus());
            }
        });

        GridPane formularioGridPane = geraFormularioGridPane();

        GridPane botoesGridPane = geraBotoesGridPane();

        TableView<TipoProduto> tipoProdutoTableView = geraTipoProdutoTableView();
        GridPane tipoProdutoTableViewGridPane = geraTipoProdutoTableViewGridPane(tipoProdutoTableView);

        GridPane principalGridPane = geraPrincipalGridPane(formularioGridPane, botoesGridPane, tipoProdutoTableViewGridPane);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0,0,0,0));
        vBox.getChildren().addAll(principalGridPane);
        return vBox;
    }

    private GridPane geraPrincipalGridPane(GridPane formularioGridPane, GridPane botoesGridPane, GridPane tipoProdutoTableViewGridPane) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,2,0,2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        gridPane.add(formularioGridPane, 0,0);
        gridPane.add(botoesGridPane, 0,1);
        gridPane.add(tipoProdutoTableViewGridPane, 0,2);

        return gridPane;
    }

    private GridPane geraTipoProdutoTableViewGridPane(TableView<TipoProduto> tipoProdutoTableView) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 2, 0, 2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        tipoProdutoTableView.setPrefWidth(5000);
        tipoProdutoTableView.setPrefHeight(5000);
        gridPane.add(tipoProdutoTableView,0,0);

        return gridPane;
    }

    private GridPane geraBotoesGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 2, 0, 2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        Button salvarButton = new Button("Salvar");
        salvarButton.setStyle("-fx-font: normal bold 15px 'verdana' ");
        gridPane.add(salvarButton,0,1);

        TipoProdutoDao tipoProdutoDao = new TipoProdutoDao();
        salvarButton.defaultButtonProperty().bind(salvarButton.focusedProperty());

        salvarButton.setOnAction(event -> {
            String tipoProdutoStr = tipoProdutoTextField.getText();

            TipoProduto tipoProduto = new TipoProduto();
            tipoProduto.setTipoProduto(tipoProdutoStr);

            if (flag == Flag.EDITAR) {
                //TODO
            } else {
                tipoProdutoDao.insert(tipoProduto);
                limpaFormulario();
                Platform.runLater(() -> {
                    try {
                        geraTipoProdutoTableView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        Button novoButton = new Button("Novo");
        novoButton.setStyle("-fx-font: normal bold 15px 'verdana' ");
        gridPane.add(novoButton,1,1);

        return gridPane;
    }

    private TableView<TipoProduto> geraTipoProdutoTableView() throws SQLException {
        TableColumn<TipoProduto, String> tipoProdutoColuna = new TableColumn<>("Tipo Produto");
        tipoProdutoColuna.setCellValueFactory(new PropertyValueFactory<>("tipoProduto"));

        TipoProdutoDao tipoProdutoDao = new TipoProdutoDao();
        ObservableList<TipoProduto> list = tipoProdutoDao.buscaTodasTipoProdutos();

        TableView<TipoProduto> tableView = new TableView<>();
        tableView.getColumns().clear();
        tableView.setItems(list);
        tableView.getColumns().addAll(tipoProdutoColuna);

        return tableView;
    }

    private void limpaFormulario() {
        tipoProdutoTextField.setText("");
        tipoProdutoTextField.requestFocus();
    }

    private GridPane geraFormularioGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 2, 0, 2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        Text tipoProdutoLabel = new Text("Tipo produto: ");
        tipoProdutoTextField = new TextField();
        tipoProdutoLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        tipoProdutoTextField.setPrefWidth(5000);
        gridPane.add(tipoProdutoLabel,0,0);
        gridPane.add(tipoProdutoTextField,0,1);

        return gridPane;
    }
}
