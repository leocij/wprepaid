package com.lemelo.referencia;

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

public class ReferenciaNode {
    private TextField referenciaTextField;
    private Flag flag;

    public Node executar(Tab referenciaTab) throws SQLException {

        referenciaTab.setOnSelectionChanged(e->{
            if(referenciaTab.isSelected()==true) {
                Platform.runLater(()->referenciaTextField.requestFocus());
            }
        });

        GridPane formularioGridPane = geraFormularioGridPane();

        GridPane botoesGridPane = geraBotoesGridPane();

        TableView<Referencia> referenciaTableView = geraReferenciaTableView();
        GridPane referenciaTableViewGridPane = geraReferenciaTableViewGridPane(referenciaTableView);

        GridPane principalGridPane = geraPrincipalGridPane(formularioGridPane, botoesGridPane, referenciaTableViewGridPane);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0,0,0,0));
        vBox.getChildren().addAll(principalGridPane);
        return vBox;
    }

    private GridPane geraReferenciaTableViewGridPane(TableView<Referencia> referenciaTableView) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 2, 0, 2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        referenciaTableView.setPrefWidth(5000);
        referenciaTableView.setPrefHeight(5000);
        gridPane.add(referenciaTableView,0,0);

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

        ReferenciaDao referenciaDao = new ReferenciaDao();
        salvarButton.defaultButtonProperty().bind(salvarButton.focusedProperty());

        salvarButton.setOnAction(event -> {
            String referenciaStr = referenciaTextField.getText();

            Referencia referencia = new Referencia();
            referencia.setReferencia(referenciaStr);

            if (flag == Flag.EDITAR) {
                //TODO
            } else {
                referenciaDao.insert(referencia);
                limpaFormulario();
                Platform.runLater(() -> {
                    try {
                        geraReferenciaTableView();
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

    private TableView<Referencia> geraReferenciaTableView() throws SQLException {
        TableColumn<Referencia, String> referenciaColuna = new TableColumn<>("Referência");
        referenciaColuna.setCellValueFactory(new PropertyValueFactory<>("referencia"));

        ReferenciaDao referenciaDao = new ReferenciaDao();
        ObservableList<Referencia> list = referenciaDao.buscaTodasReferencias();

        TableView<Referencia> tableView = new TableView<>();
        tableView.getColumns().clear();
        tableView.setItems(list);

        return tableView;
    }

    private void limpaFormulario() {
        referenciaTextField.setText("");
        referenciaTextField.requestFocus();
    }

    private GridPane geraFormularioGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 2, 0, 2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        Text referenciaLabel = new Text("Referência: ");
        referenciaTextField = new TextField();
        referenciaLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        referenciaTextField.setPrefWidth(5000);
        gridPane.add(referenciaLabel,0,0);
        gridPane.add(referenciaTextField,0,1);

        return gridPane;
    }

    private GridPane geraPrincipalGridPane(GridPane formularioGridPane, GridPane botoesGridPane, GridPane referenciaTableViewGridPane) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,2,0,2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        gridPane.add(formularioGridPane, 0,0);
        gridPane.add(botoesGridPane, 0,1);
        gridPane.add(referenciaTableViewGridPane, 0,2);

        return gridPane;
    }
}
