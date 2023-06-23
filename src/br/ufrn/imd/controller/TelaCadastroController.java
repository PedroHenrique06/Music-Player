package br.ufrn.imd.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import br.ufrn.imd.model.Diretorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TelaCadastroController {
	@FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;
    
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
			try {
				FileWriter file = new FileWriter(path, true);
				
				PrintWriter writer = new PrintWriter(file);
				writer.printf(line + "\n");
				writer.close();
				
			} catch (IOException e) {
				System.out.println("Deu ruim no arquivo");
				e.printStackTrace();
			}
			
			// Voltar a tela de login
			
			
		}
		
	}
                     
}

