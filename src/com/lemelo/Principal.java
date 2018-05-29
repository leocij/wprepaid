package com.lemelo;

import com.lemelo.novo_cartao.NovoCartaoNode;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Principal extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);

        Tab novoCartaoTab = new Tab();
        novoCartaoTab.setText("Novo Cartão");
        novoCartaoTab.setStyle("-fx-font: normal bold 15px 'verdana' ");
        novoCartaoTab.setClosable(false);
        novoCartaoTab.setContent(new NovoCartaoNode().executar(novoCartaoTab));
        tabPane.getTabs().add(novoCartaoTab);

        Integer WIDTH_TAM = 940;
        Integer HEIGHT_TAM = 680;
        Scene scene = new Scene(tabPane, WIDTH_TAM, HEIGHT_TAM, Color.GRAY);
        primaryStage.setMinWidth(WIDTH_TAM);
        primaryStage.setMinHeight(HEIGHT_TAM);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cartão Pré-Pago");

        primaryStage.show();
    }
}
