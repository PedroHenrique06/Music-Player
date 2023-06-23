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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class TelaCadastroController {
	@FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;
    
    @FXML
    private RadioButton vipButton;
    
    private void initialize() {
        registerButton.setOnAction(this::handleRegisterButtonAction);
    }
    
    @FXML
    private void handleRegisterButtonAction(ActionEvent actionEvent) {
    	
        // Lidar com o evento de clique do botão de login
          
        String username = usernameField.getText();
        String password = passwordField.getText();
       	
        // Colocar o nome e a senha em um arquivo
		String line = username + " : " + password; 
		
		Diretorio diretorio = new Diretorio("usuarios");
		
		if(diretorio.ehValido()) {
			
			String path = "C:/Users/PEDRO HENRIQUE/OneDrive/Área de Trabalho/Github/MusicApp/usuarios/logins.txt";
			
			// Mensagem informando o resultado da operação (sucesso ou fracasso)
			Alert alert = new Alert(AlertType.INFORMATION);
	        
	        // Defina o título e a mensagem da caixa de diálogo
	        alert.setTitle("Resultado da operação");
	        alert.setHeaderText(null);
	        
			try {
				FileWriter file = new FileWriter(path, true);
				
				PrintWriter writer = new PrintWriter(file);
				writer.printf(line + "\n");
				writer.close();
				
		        alert.setContentText("O usuário foi cadastrado com sucesso");
		        
		        // Exiba a caixa de diálogo e aguarde até que o usuário a feche
		        alert.showAndWait();
		        
		     // Fechar a janela de cadsastro, se necessário
                Stage stage = (Stage) registerButton.getScene().getWindow();
                // Fechar a janela
                stage.close();
				
				
			} catch (IOException e) {
				alert.setContentText("Falha na operação, não foi possível cadastrar o usuário");
				// Exiba a caixa de diálogo e aguarde até que o usuário a feche
		        alert.showAndWait();
				e.printStackTrace();
			}
			
		}
		
	}
                     
}

