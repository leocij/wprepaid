package com.lemelo.novo_cartao;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class NovoCartaoNode {

    private DatePicker dataNascimentoDatePicker;
    private TextField valorTextField;
    private String numeroDigitado;

    public Node executar(Tab novoCartaoTab) {

        GridPane formularioGridPane = geraFormularioGridPane();

        GridPane principalGridPane = geraPrincipalGridPane(formularioGridPane);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(0,0,0,0));
        vBox.getChildren().addAll(principalGridPane);
        return vBox;
    }

    private GridPane geraFormularioGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,2,0,2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        Text cpfLabel = new Text("CPF: ");
        TextField cpfTextField = new TextField();
        cpfLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        cpfTextField.setPrefWidth(5000);
        gridPane.add(cpfLabel, 0, 0);
        gridPane.add(cpfTextField, 0, 1);

        Text statusCpfLabel = new Text("Status CPF: ");
        TextField statusCpfTextField = new TextField();
        cpfLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        cpfTextField.setPrefWidth(5000);
        gridPane.add(statusCpfLabel, 0, 2);
        gridPane.add(statusCpfTextField, 0, 3);

        Text matriculaLabel = new Text("Matrícula: ");
        TextField matriculaTextField = new TextField();
        cpfLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        cpfTextField.setPrefWidth(5000);
        gridPane.add(matriculaLabel, 0, 4);
        gridPane.add(matriculaTextField, 0, 5);

        Text nomeLabel = new Text("CPF: ");
        TextField nomeTextField = new TextField();
        cpfLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        cpfTextField.setPrefWidth(5000);
        gridPane.add(nomeLabel, 0, 6);
        gridPane.add(nomeTextField, 0, 7);

        Text dataNascimentoLabel = new Text("Data Nascimento: ");
        dataNascimentoLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        gridPane.add(dataNascimentoLabel,0,8);
        dataNascimentoDatePicker = new DatePicker(LocalDate.now());
        gridPane.add(dataNascimentoDatePicker,0,9);

        Text valorLabel = new Text("Valor: ");
        valorLabel.setStyle("-fx-font: normal bold 15px 'verdana' ");
        gridPane.add(valorLabel, 0, 10);
        valorTextField = new TextField();
        valorTextField.setMaxWidth(80);
        gridPane.add(valorTextField, 0, 11);
        valorTextField.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(new BigDecimal("0")));

        //Posiciona o cursor no fim da linha
        valorTextField.setOnMousePressed(event -> Platform.runLater(()->valorTextField.positionCaret(valorTextField.getText().length())));

        //Formatação em moeda corrente
        valorTextField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            String numeroAntigoStr = valorTextField.getText();
            if(e.getCode().isDigitKey()) {
                if(numeroDigitado==null) {
                    numeroDigitado = e.getText();
                } else {
                    numeroDigitado += e.getText();
                }
                Platform.runLater(()->valorTextField.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(new BigDecimal(numeroDigitado).divide(new BigDecimal("100")))));
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                if(numeroDigitado==null){
                    Platform.runLater(()->valorTextField.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(new BigDecimal("0"))));
                } else {
                    numeroDigitado = removeUltimoDigito(numeroDigitado);
                    if(numeroDigitado.equals("")) {
                        Platform.runLater(()->valorTextField.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(new BigDecimal("0"))));
                    } else {
                        Platform.runLater(()->valorTextField.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(new BigDecimal(numeroDigitado).divide(new BigDecimal("100")))));
                    }
                }
            }
            else {
                //System.out.println(e);
                Platform.runLater(()->valorTextField.setText(numeroAntigoStr));
            }

            //Coloca o cursor no fim da string
            Platform.runLater(()->valorTextField.positionCaret(valorTextField.getText().length()));
        });

        return gridPane;
    }

    private String removeUltimoDigito(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private GridPane geraPrincipalGridPane(GridPane formularioGridPane) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5,2,0,2));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_LEFT);

        gridPane.add(formularioGridPane, 0,0);

        return gridPane;
    }
}
