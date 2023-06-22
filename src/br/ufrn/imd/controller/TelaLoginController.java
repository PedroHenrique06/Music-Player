package br.ufrn.imd.controller;
import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class TelaLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    private void initialize() {
        signUpButton.setOnAction(this::handleSignUpButtonAction);
        loginButton.setOnAction(this::handleLoginButtonAction);
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent actionevent) {
        // Lidar com o evento de clique do botão de login
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        System.out.println("Printar dentro de função funciona!!!");
        
       if(username.equals("admin")) {
    	   try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/view/TelaApp.fxml"));
               Parent root = loader.load();
               TelaAppController TelaAppController = loader.getController();

               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.show();

               // Fechar a janela de login, se necessário
               Stage stage2 = (Stage) loginButton.getScene().getWindow();
            // Fechar a janela
            stage2.close();
           } catch (IOException e) {
               e.printStackTrace();
               // Tratar exceção de carregamento da tela de cadastro
           }
       }
    }

   
    
    @FXML
    private void handleSignUpButtonAction(ActionEvent actionevent1) {
        // Lidar com o evento de clique do botão de cadastro
        // Redirecionar para a tela de cadastro ou realizar outras ações necessárias
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/view/TelaCadastro.fxml"));
             Parent root = loader.load();
             TelaCadastroController telaCadastroController = loader.getController();

             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.show();

             // Fechar a janela de login, se necessário
           
         } catch (IOException e) {
             e.printStackTrace();
             // Tratar exceção de carregamento da tela de cadastro
         }
    	 
    }

	
}
