package br.ufrn.imd.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import br.ufrn.imd.dao.UsuarioDAO;
import br.ufrn.imd.model.Diretorio;
import br.ufrn.imd.model.Musica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;


import br.ufrn.imd.model.Playlist;
import br.ufrn.imd.model.Usuario;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;


public class TelaAppController {

    @FXML
    private Button chooseButton;
    
    @FXML
    private Button chooseDirectoryButton;
    
    @FXML
    private Button createPlaylistButton;
    
    @FXML
    private File selectedSong;
    @FXML
    private Button playButton;
    
    @FXML
    private Button pauseButton;
    
    @FXML
    private ListView<Musica> musicListView;
    

    private MediaPlayer mediaPlayer; 
    
    @FXML
    private ListView<Playlist> playlistListView;
    
    private ObservableList<Playlist> observablelistaPlaylists;
    private Playlist playlistSelecionada;
    
    private Playlist TodasAsMusicas = new Playlist("Todas as músicas");
    private Musica ultimaMusicaTocada = new Musica();
    @FXML
    public void initialize() {
  
    	observablelistaPlaylists = FXCollections.observableArrayList();
    	observablelistaPlaylists.add(TodasAsMusicas);
        playlistListView.setItems(observablelistaPlaylists);
        
        playlistListView.setCellFactory(param -> new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitulo());
                }
            }
        });
        
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
    private void handlePlaylistSelection(MouseEvent event) {
        if (event.getClickCount() == 1) {
            playlistSelecionada = playlistListView.getSelectionModel().getSelectedItem();
            if (playlistSelecionada != null) {
                // Atualize a lista de músicas exibida na outra ListView com as músicas da playlist selecionada
            	musicListView.setItems(playlistSelecionada.getObservableListaMusicas());

            }
        }
    }

    @FXML
    private void criarPlaylist(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Playlist");
        dialog.setHeaderText("Digite o nome da nova playlist:");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nomePlaylist -> {
            Playlist novaPlaylist = new Playlist(nomePlaylist);
            observablelistaPlaylists.add(novaPlaylist);
        });
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
            playlistSelecionada = playlistListView.getSelectionModel().getSelectedItem();
            playlistSelecionada.addMusica(musica);
            if(playlistSelecionada.getTitulo() != "Todas as músicas") {
            	TodasAsMusicas.addMusica(musica);
            }
           // observableList.add(musica);
            
        }
    }
    
    @FXML
    private void handleChooseDirectoryButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar Diretório de Músicas");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home"))); // Define o diretório inicial como o diretório do usuário

        File selectedDirectory = directoryChooser.showDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            // Percorre todos os arquivos de música dentro do diretório selecionado
            File[] musicFiles = selectedDirectory.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg"));
            if (musicFiles != null) {
                for (File musicFile : musicFiles) {
                    Musica musica = criarMusica(musicFile);
                    savePath(musica.getLocal());
                    playlistSelecionada = playlistListView.getSelectionModel().getSelectedItem();
                    if( playlistSelecionada == null) {
                    	playlistSelecionada = TodasAsMusicas;
                    }
                    playlistSelecionada.addMusica(musica);
                    if(playlistSelecionada.getTitulo() != "Todas as músicas") {
                    	TodasAsMusicas.addMusica(musica);
                    	
                    }
                }
            }
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
		            TodasAsMusicas.addMusica(song);
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
    	
    	
    	Musica selectedMusica = musicListView.getSelectionModel().getSelectedItem();
    	if(ultimaMusicaTocada == selectedMusica) {
    		mediaPlayer.play();
    	}else {
        if (selectedMusica != null) {
            File arquivoMusica = selectedMusica.getArquivo();
            Media media = new Media(arquivoMusica.toURI().toString());
           
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop(); // 
            }
            
            
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                System.out.println("Reproduzindo música: " + arquivoMusica.getAbsolutePath());
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                System.out.println("Fim da reprodução da música: " + arquivoMusica.getAbsolutePath());
            });            
        	}
        ultimaMusicaTocada = selectedMusica;
    }
}

    @FXML
    private void handlePauseButtonAction(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause(); // Pausar a reprodução da música
            System.out.println("Música pausada.");
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