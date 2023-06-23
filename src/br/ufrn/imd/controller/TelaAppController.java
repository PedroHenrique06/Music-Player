package br.ufrn.imd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import br.ufrn.imd.model.Diretorio;
import br.ufrn.imd.model.Musica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TelaAppController {

    @FXML
    private Button chooseButton;

    @FXML
    private File selectedSong;

    @FXML
    private ListView<Musica> musicListView;
    private ObservableList<Musica> observableList;

    @FXML
    public void initialize() {
        observableList = FXCollections.observableArrayList();
        musicListView.setItems(observableList);
        
        musicListView.setCellFactory(param -> new ListCell<Musica>() {
            @Override
            protected void updateItem(Musica item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitulo());
                }
            }
        });
        
        loadSongList();
    }

    @FXML
    private void handleChooseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Música");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivos de Áudio", "*.mp3", "*.wav", "*.ogg"),
                new ExtensionFilter("Todos os Arquivos", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Musica musica = criarMusica(selectedFile);
            savePath(musica.getLocal());
            observableList.add(musica);
        }
    }
    
    private void loadSongList() {
	    	try {
		    	InputStream is = new FileInputStream("./musicas/musicas.txt"); // bytes
				InputStreamReader isr = new InputStreamReader(is); // char
				BufferedReader br = new BufferedReader(isr); // string
				
				String line = br.readLine();
				
				while(line != null){
					File fileSong = new File(line);
					Musica song = criarMusica(fileSong);
		            observableList.add(song);
					line = br.readLine();
				}
				
				br.close();
	    	} 
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
  }
    
    private Musica criarMusica(File arquivo) {
        Musica musica = new Musica();
        musica.setArquivo(arquivo);
        musica.setTitulo(arquivo.getName());
        musica.setLocal(arquivo.getAbsolutePath());
        // Configurar as propriedades da música com base no arquivo selecionado
        return musica;
    }
    
    private void savePath(String path) {
    	Diretorio diretorio = new Diretorio("musicas");
    	if(diretorio.ehValido()) {
				try {
					FileWriter fileWriter = new FileWriter("./musicas/musicas.txt", true);
					PrintWriter writter = new PrintWriter(fileWriter);
					writter.printf(path + "\n");
					writter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	}    		
    		
	}


    @FXML
    private void handlePlayButtonAction(ActionEvent event) {
        if (selectedSong != null) {
            // Adicionar lógica para reproduzir a música
            System.out.println("Reproduzindo música: " + selectedSong.getAbsolutePath());
        }
    }

    @FXML
    private void handlePauseButtonAction(ActionEvent event) {
        if (selectedSong != null) {
            // Adicionar lógica para pausar a reprodução da música
            System.out.println("Pausando música: " + selectedSong.getAbsolutePath());
        }
    }

    @FXML
    private void handleStopButtonAction(ActionEvent event) {
        if (selectedSong != null) {
            // Adicionar lógica para parar a reprodução da música
            System.out.println("Parando música: " + selectedSong.getAbsolutePath());
        }
    }
}