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
    	
    	System.out.println("Inside handleRegister");
        // Lidar com o evento de clique do botão de login
    	
          
        String username = usernameField.getText();
        String password = passwordField.getText();
       	
        
     // Colocar o nome e a senha em um arquivo
     
		String nomeDiretorio = "usuarios";
		String linha = username + " " + password; 
		
		Diretorio diretorio = new Diretorio(nomeDiretorio);
		
		if(diretorio.criarDiretorio()) {
			
			String caminho = "C:/Users/PEDRO HENRIQUE/OneDrive/Área de Trabalho/Github/MusicApp/" + nomeDiretorio + "/logins.txt";
			
			try {
				
				FileWriter file = new FileWriter(caminho, true);
				
				PrintWriter writer = new PrintWriter(file);
				writer.printf(linha + "\n");
				System.out.println("Mensagem gravada com sucesso!");
				writer.close();
				
			} catch (IOException e) {
				System.out.println("Deu ruim no arquivo");
				e.printStackTrace();
			}
			
			// Voltar a tela de login
			
			
		}
		
		
	}
                     
}

