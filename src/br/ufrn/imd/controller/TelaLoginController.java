package br.ufrn.imd.controller;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
        
        // Pegar os usuarios existentes na base de dados
        try {
        	String path = "C:/Users/PEDRO HENRIQUE/OneDrive/Área de Trabalho/Github/MusicApp/usuarios/logins.txt";
        	InputStream is = new FileInputStream(path); // bytes
			InputStreamReader isr = new InputStreamReader(is); // char
    		BufferedReader br = new BufferedReader(isr); // string
    		String loginPasswordLine = br.readLine();
    		
    		while(loginPasswordLine != null){
    			// Crie um objeto StringTokenizer para tokenizar a String
    			String[] tokens = loginPasswordLine.split(" : ");
                
    			// String tokenizer = new StringTokenizer(loginPasswordLine);
                
                // Lista que guardará o usuário e a senha atuais
                ArrayList<String> usernameAndPassword = new ArrayList<>();
                
                // Adiociona usuário e senha ao array
                for (String token : tokens) {
                    usernameAndPassword.add(token);
                }
                
                // Compara com o que o usuario que está tentando fazer login	
	    		if(username.equals(usernameAndPassword.get(0)) && password.equals(usernameAndPassword.get(1))) {
	    			try {
	    				System.out.println("Usuário encontrado... autenticação concluida.");
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
		            }
	    			catch (IOException e) {
		                e.printStackTrace();
		                // Tratar exceção de carregamento da tela de cadastro
		            }
	    		break;
	    			
	    	}
	    	// Adiciona próxima linha do arquivo
	    	loginPasswordLine = br.readLine();
    	}
    		
    	// Caso o usuário não exista no arquivo de logins
    	if(loginPasswordLine == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            
            // Defina o título e a mensagem da caixa de diálogo
            alert.setTitle("Resultado da operação");
            alert.setHeaderText(null);
            alert.setContentText("Não há usuário com essas credenciais no sistema, favor realizar o cadastro");
            
            // Exiba a caixa de diálogo e aguarde até que o usuário a feche
            alert.showAndWait();
    	}
    		
    	br.close();
    	
        } 
        catch (IOException e) {
            e.printStackTrace();
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
