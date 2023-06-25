package br.ufrn.imd.controller;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import br.ufrn.imd.dao.UsuarioDAO;
import br.ufrn.imd.model.Usuario;
import br.ufrn.imd.model.UsuarioVIP;

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
        UsuarioDAO usuariodao = UsuarioDAO.getInstance();
        usuariodao.carregaUsuarios();
        ArrayList<Usuario> listaUsuarios = usuariodao.getListaUsuarios();
        
         
        for (Usuario usuario : listaUsuarios) {
            if (username.equals(usuario.getUsername()) && password.equals(usuario.getSenha())) {
                try {
                	
                	if(usuario instanceof UsuarioVIP) {
                		usuariodao.setUsuarioAtual((UsuarioVIP)usuario);
                	}
                	else {
                		usuariodao.setUsuarioAtual(usuario);
                	}
                	
                	
                    System.out.println("Usuário encontrado... autenticação concluída.");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/view/TelaApp.fxml"));
                    Parent root = loader.load();
                   

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Fechar a janela de login, se necessário
                    Stage stage2 = (Stage) loginButton.getScene().getWindow();
                    // Fechar a janela
                    stage2.close();
                  return;
                    
                } catch (IOException err) {
                    err.printStackTrace();
                    // Tratar exceção de carregamento da tela de login
                }         
            }
        }
            Alert alert = new Alert(AlertType.INFORMATION);
            
            // Defina o título e a mensagem da caixa de diálogo
            alert.setTitle("Resultado da operação");
            alert.setHeaderText(null);
            alert.setContentText("Não há usuário com essas credenciais no sistema, favor realizar o cadastro");
            
            // Exiba a caixa de diálogo e aguarde até que o usuário a feche
            alert.showAndWait();
    	
        
	}
    
    
    @FXML
    private void handleSignUpButtonAction(ActionEvent actionevent1) {
        // Lidar com o evento de clique do botão de cadastro
        // Redirecionar para a tela de cadastro ou realizar outras ações necessárias
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/view/TelaCadastro.fxml"));
             Parent root = loader.load();
        

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
