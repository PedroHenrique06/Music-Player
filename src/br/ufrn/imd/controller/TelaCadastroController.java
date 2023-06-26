package br.ufrn.imd.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.ufrn.imd.model.Diretorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Classe controladora responsável por gerenciar a lógica da tela de cadastro.
 */
public class TelaCadastroController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private RadioButton vipButton;

    /**
     * Inicializa o controlador.
     */
    @FXML
    private void initialize() {
        registerButton.setOnAction(this::handleRegisterButtonAction);
    }

    /**
     * Manipula o evento de clique no botão de cadastro.
     *
     * @param actionEvent O evento de clique.
     */
    @FXML
    private void handleRegisterButtonAction(ActionEvent actionEvent) {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String vip = "false";

        if (vipButton.isSelected()) {
            vip = "true";
        }

        String userData = username + " : " + password + " : " + vip;

        Diretorio diretorio = new Diretorio("usuarios");

        if (diretorio.ehValido()) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Resultado da operação");
            alert.setHeaderText(null);

            try {
                FileWriter fileWriter = new FileWriter("./usuarios/logins.txt", true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.printf(userData + "\n");
                printWriter.close();

                alert.setContentText("O usuário foi cadastrado com sucesso");
                alert.showAndWait();

                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                alert.setContentText("Falha na operação, não foi possível cadastrar o usuário");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }
}
