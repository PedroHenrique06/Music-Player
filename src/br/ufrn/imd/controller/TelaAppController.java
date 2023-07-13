package br.ufrn.imd.controller;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import br.ufrn.imd.dao.SongDAO;
import br.ufrn.imd.dao.PlaylistDAO;
import br.ufrn.imd.dao.UserDAO;
import br.ufrn.imd.model.Directory;
import br.ufrn.imd.model.Song;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;


import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;


import br.ufrn.imd.model.Playlist;
import br.ufrn.imd.model.User;
import br.ufrn.imd.model.VipUser;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

/**
 * Classe controladora responsável por gerenciar a lógica da interface gráfica do player de música.
 */
public class TelaAppController {
	@FXML
	private Label currentUsername;
	
	@FXML
	private Label regularOrVip;
	
    @FXML
    private Button chooseButton;
    
    @FXML
    private Button chooseDirectoryButton;
    
    @FXML
    private Button createPlaylistButton;
    
    @FXML Button logoutButton;
    
    @FXML
    private Button playButton;
    
    @FXML
    private Button pauseButton;
    
     @FXML
    private File selectedSong;
     
    @FXML
    private ListView<Song> songListView;
    
    private MediaPlayer mediaPlayer; 
    
    @FXML
    private ListView<Playlist> playlistListView;
    
    private ObservableList<Playlist> observablelistaPlaylists;
    private Playlist selectedPlaylist;
    private PlaylistDAO playlistDAO = PlaylistDAO.getInstance();
    
    private Playlist AllSongs = new Playlist("Todas as músicas");
    private Song lastSongPlayed = new Song();
        
    private User currentUser = UserDAO.getInstance().getCurrentUser();
    
    /**
     * Método inicializado ao carregar a interface gráfica.
     */

    @FXML
    public void initialize() {
    	loadSongList();
        loadPlaylistList();
    	
    	if(currentUser != null) {
    	currentUsername.setText(currentUser.getUsername());
    	regularOrVip.setText( currentUser instanceof VipUser ? "VIP" : "Conta gratuita!");}
    	
    	
    	observablelistaPlaylists = FXCollections.observableArrayList();
    	observablelistaPlaylists.add(AllSongs);
    	for(Playlist playlist : playlistDAO.getListaPlaylists()) {
    		observablelistaPlaylists.add(playlist);
    	}
    	
        playlistListView.setItems(observablelistaPlaylists);
        
        playlistListView.setCellFactory(param -> new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });
        
        songListView.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle());
                }
            }
        });
        
    }
    
    
    /**
     * Manipula o evento de seleção de uma playlist.
     *
     * @param event O evento de seleção.
     */
    @FXML
    private void handlePlaylistSelection(MouseEvent event) {
        if (event.getClickCount() == 1) {
            selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
            if (selectedPlaylist != null) {
                // Atualize a lista de músicas exibida na outra ListView com as músicas da playlist selecionada
            	songListView.setItems(selectedPlaylist.getObservableSongList());
            	

            }
        }
    }

    /**
     * Cria uma nova playlist quando o botão é clicado.
     *
     * @param event O evento do clique no botão.
     */
    @FXML
    private void createPlaylist(ActionEvent event) {
        if(currentUser instanceof VipUser) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Playlist");
        dialog.setHeaderText("Digite o nome da nova playlist:");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(playlistName -> {
            Playlist newPlaylist = new Playlist(playlistName);
            observablelistaPlaylists.add(newPlaylist);
         
             Directory directory = new Directory(currentUser.getUsername());
            
            if(directory.isValid()) {
            	File file = new File("./" + directory.getName() + "/playlist_" + playlistName + ".txt");
            	
            	try {
	            	if(file.createNewFile()) { 
	            		// Não há nada para ocorrer aqui
	            	} 
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            	
            }            		
            
        });
        }else {
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Aviso");
        	alert.setHeaderText(null);
        	alert.setContentText("Essa é uma função para usuários VIPs.");

        	alert.showAndWait();
        	 alert.close();
        }
    }

    /**
     * Manipula o evento de escolha de uma música.
     *
     * @param event O evento de escolha da música.
     */
    @FXML
    private void handleChooseButtonAction(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Música");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivos de Áudio", "*.mp3", "*.wav", "*.ogg"),
                new ExtensionFilter("Todos os Arquivos", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            Song song = createSong(selectedFile);
            savePath(song.getLocal());
            selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
            selectedPlaylist.addSong(song);
            savePath(song.getLocal(), currentUser.getUsername(), selectedPlaylist.getTitle());
            
            if(selectedPlaylist.getTitle() != "Todas as músicas") {
            	AllSongs.addSong(song);
            }
            
        }
    }
    
    
    /**
     * Salva o caminho de um arquivo de música em uma playlist que é um arquivo de texto.
     *
     * @param path O caminho do arquivo de música a ser salvo.
     * @param folder A pasta onde o arquivo será salvo.
     * @param playlistTitle O nome da playlist.
     */
    private void savePath(String path, String folder, String playlistTitle) {
    	File file = new File("./" + folder + "/playlist_" + playlistTitle + ".txt");
    	try {
				FileWriter fileWriter = new FileWriter(file, true);
				PrintWriter writter = new PrintWriter(fileWriter);
				writter.printf(path + "\n");
				writter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
	
    }
    
       
    /*
     * Manipula o evento de clique pra seleção de um novo diretório com músicas.
     *
     * @param event O evento de clique.
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja fazer logout?");

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(okButton, cancelButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true); // Opcional: para manter o diálogo sempre no topo

        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                // Realizar o logout
                UserDAO.getInstance().resetDAO(); // Chamando o método resetDAO para limpar os dados
                SongDAO.getInstance().resetDAO();
                PlaylistDAO.getInstance().resetDAO();
                
                
                // Fechar a janela atual e voltar para a tela de login
                Stage currentStage = (Stage) logoutButton.getScene().getWindow();
                currentStage.close();

                // Abrir a tela de login novamente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/view/TelaLogin.fxml"));
                Parent root = null;
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.show();
            }
        });
    }
    
    /**
     * Manipula o evento de clique pra seleção de um novo diretório com músicas.
     *
     * @param event O evento de clique.
     */
    @FXML
    private void handleChooseDirectoryButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar Diretório de Músicas");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home"))); // Define o diretório inicial como o diretório do usuário

        File selectedDirectory = directoryChooser.showDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            // Percorre todos os arquivos de música dentro do diretório selecionado
            File[] songFiles = selectedDirectory.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg"));
            if (songFiles != null) {
                for (File songFile : songFiles) {
                    Song song = createSong(songFile);
                    savePath(song.getLocal());
                    selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
                    if( selectedPlaylist == null) {
                    	selectedPlaylist = AllSongs;
                    }
                    selectedPlaylist.addSong(song);
                    savePath(song.getLocal(), currentUser.getUsername(), selectedPlaylist.getTitle());
                    if(selectedPlaylist.getTitle() != "Todas as músicas") {
                    	AllSongs.addSong(song);
                    	
                    }
                }
            }
        }
    }
    
    
    /**
     * Carrega a lista de músicas salvas a partir de um arquivo de texto.
     */
    private void loadSongList() {
    	Directory directory = new Directory("musicas");   	
    	if(directory.isValid()) {
    		
    		File file = new File("./musicas/musicas.txt");
    		if(!file.exists()) {
    			try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		
	    	try {
		    	InputStream is = new FileInputStream("./musicas/musicas.txt"); // bytes
				InputStreamReader isr = new InputStreamReader(is); // char
				BufferedReader br = new BufferedReader(isr); // string
				
				String line = br.readLine();
				
				while(line != null){
					File fileSong = new File(line);
					Song song = createSong(fileSong);
		            AllSongs.addSong(song);
					line = br.readLine();
				}
				
				br.close();
	    	} 
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
    	}
  }
    
    /**
     * Carrega as playlists do usuário atual a partir do diretório dele.
     */
    private void loadPlaylistList() {
    	    	
        File folder = new File(currentUser.getUsername());
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.toLowerCase().endsWith(".txt");
            }
        });

        if (files != null) {
            for (File file : files) {
               
            	try {
			    	InputStream is = new FileInputStream(file); // bytes
					InputStreamReader isr = new InputStreamReader(is); // char
					BufferedReader br = new BufferedReader(isr); // string
					
					String line = br.readLine();
					
					Playlist currentPlaylist = new Playlist(extractName(file.getName()));
					while(line != null){
						File fileSong = new File(line);
						Song song = createSong(fileSong);
			            currentPlaylist.addSong(song);
						line = br.readLine();
					}
					playlistDAO.addPlaylist(currentPlaylist);
					
					br.close();
				} 
			catch(Exception e){
				e.printStackTrace();
			}
          }
        }
    	
}
    /**
     * Retira a extensão do nome do arquivo.
     */
    private String extractName(String name) {

    	// Extrair o nome sem a extensão
        String nameWithoutExtension = extractNameWithoutExtension(name);

        // Extrair apenas a parte desejada
        String desiredPart = extractDesiredPart(nameWithoutExtension);

        return desiredPart;
    }

    /**
     * Retira o ".txt" do nome do arquivo.
     */
    private String extractNameWithoutExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex != -1) {
            return filename.substring(0, dotIndex);
        }
        return filename;
    }
    
    /**
     * Retira o "playlist_" do nome do arquivo.
     */
    private String extractDesiredPart(String nameWithoutExtension) {
        int indexUnderlined = nameWithoutExtension.indexOf("_");
        if (indexUnderlined != -1) {
            return nameWithoutExtension.substring(indexUnderlined + 1);
        }
        return nameWithoutExtension;
    }
    
    
    
    /**
     * Cria um objeto Musica com base em um arquivo selecionado.
     *
     * @param arquivo O arquivo de áudio selecionado.
     * @return O objeto Musica criado.
     */
    private Song createSong(File file) {
    	// Configurar as propriedades da música com base no arquivo selecionado
        Song song = new Song();
        song.setArquivo(file);
        song.setTitle(file.getName());
        song.setLocal(file.getAbsolutePath());
        
        return song;
    }
    
    /**
     * Salva o caminho de um arquivo de música em um arquivo de texto.
     *
     * @param path O caminho do arquivo de música a ser salvo.
     */
    private void savePath(String path) {
		try {
			FileWriter fileWriter = new FileWriter("./musicas/musicas.txt", true);
			PrintWriter writter = new PrintWriter(fileWriter);
			writter.printf(path + "\n");
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    		
	}

    /**
     * Manipula o evento de clique no botão Play.
     *
     * @param event O evento de clique.
     */
    @FXML
    private void handlePlayButtonAction(ActionEvent event) {
    	
    	
    	Song selectedSong = songListView.getSelectionModel().getSelectedItem();
    	if(lastSongPlayed == selectedSong) {
    		mediaPlayer.play();
    	}else {
        if (selectedSong != null) {
            File songFile = selectedSong.getFile();
            Media media = new Media(songFile.toURI().toString());
           
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.stop(); // 
            }
            
            
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                System.out.println("Reproduzindo música: " + songFile.getAbsolutePath());
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                System.out.println("Fim da reprodução da música: " + songFile.getAbsolutePath());
            });            
        	}
        lastSongPlayed = selectedSong;
    }
}
    /**
     * Manipula o evento do botão "Pause".
     *
     * @param event O evento do clique no botão "Pause".
     */
    @FXML
    private void handlePauseButtonAction(ActionEvent event) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause(); // Pausar a reprodução da música
            System.out.println("Música pausada.");
        }
    }


    
}