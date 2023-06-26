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
import br.ufrn.imd.dao.MusicaDAO;
import br.ufrn.imd.dao.PlaylistDAO;
import br.ufrn.imd.dao.PlaylistDAO;
import br.ufrn.imd.dao.UsuarioDAO;
import br.ufrn.imd.model.Diretorio;
import br.ufrn.imd.model.Musica;

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
import br.ufrn.imd.model.UsuarioVIP;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

/**
 * Classe controladora responsável por gerenciar a lógica da interface gráfica do player de música.
 */
public class TelaAppController {
	@FXML
	private Label nomeUsuarioAtual;
	
	@FXML
	private Label vipOuComum;
	
	
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
    private ListView<Musica> musicListView;
    

    private MediaPlayer mediaPlayer; 
    
    @FXML
    private ListView<Playlist> playlistListView;
    
    private ObservableList<Playlist> observablelistaPlaylists;
    private Playlist playlistSelecionada;
    private PlaylistDAO playlistdao = PlaylistDAO.getInstance();
    
    private Playlist TodasAsMusicas = new Playlist("Todas as músicas");
    private Musica ultimaMusicaTocada = new Musica();
    //private UsuarioDAO usuarioAtual = UsuarioDAO.getInstance();
    
    
    
    private Usuario usuarioAtual;
    
    /**
     * Método inicializado ao carregar a interface gráfica.
     */

    @FXML
    public void initialize() {
    	loadSongList();
        loadPlaylistList();
    	usuarioAtual = UsuarioDAO.getInstance().getUsuarioAtual();
    	
    	if(usuarioAtual != null) {
    	nomeUsuarioAtual.setText(usuarioAtual.getUsername());
    	vipOuComum.setText( usuarioAtual instanceof UsuarioVIP ? "VIP" : "Conta gratuita!");}
    	
    	
    	System.out.println("Usuario atual: " + usuarioAtual.getUsuarioAtual().getUsername());
  
    	observablelistaPlaylists = FXCollections.observableArrayList();
    	observablelistaPlaylists.add(TodasAsMusicas);
    	for(Playlist playlist : playlistdao.getListaPlaylists()) {
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
        
    }
    
    
    /**
     * Manipula o evento de seleção de uma playlist.
     *
     * @param event O evento de seleção.
     */
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

    /**
     * Cria uma nova playlist quando o botão é clicado.
     *
     * @param event O evento do clique no botão.
     */
    @FXML
    private void criarPlaylist(ActionEvent event) {
        if(usuarioAtual instanceof UsuarioVIP) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criar Playlist");
        dialog.setHeaderText("Digite o nome da nova playlist:");
        dialog.setContentText("Nome:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nomePlaylist -> {
            Playlist novaPlaylist = new Playlist(nomePlaylist);
            observablelistaPlaylists.add(novaPlaylist);
         
             Diretorio diretorio = new Diretorio(usuarioAtual.getUsuarioAtual().getUsername());
            
            if(diretorio.ehValido()) {
            	File file = new File("./" + diretorio.getNome() + "/playlist_" + nomePlaylist + ".txt");
            	
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
            Musica musica = criarMusica(selectedFile);
            savePath(musica.getLocal());
            playlistSelecionada = playlistListView.getSelectionModel().getSelectedItem();
            playlistSelecionada.addMusica(musica);
            savePath(musica.getLocal(), usuarioAtual.getUsuarioAtual().getUsername(), playlistSelecionada.getTitulo());
            
            if(playlistSelecionada.getTitulo() != "Todas as músicas") {
            	TodasAsMusicas.addMusica(musica);
            }
            
        }
    }
    
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
                UsuarioDAO.getInstance().resetDAO(); // Chamando o método resetDAO para limpar os dados
                MusicaDAO.getInstance().resetDAO();
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
					// TODO Auto-generated catch block
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
                UsuarioDAO.getInstance().resetDAO(); // Chamando o método resetDAO para limpar os dados
                MusicaDAO.getInstance().resetDAO();
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
					// TODO Auto-generated catch block
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
                    savePath(musica.getLocal(), usuarioAtual.getUsuarioAtual().getUsername(), playlistSelecionada.getTitulo());
                    if(playlistSelecionada.getTitulo() != "Todas as músicas") {
                    	TodasAsMusicas.addMusica(musica);
                    	
                    }
                }
            }
        }
    }
    
    
    /**
     * Carrega a lista de músicas salvas a partir de um arquivo de texto.
     */
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
    
    private void loadPlaylistList() {
    	    	
        File folder = new File(usuarioAtual.getUsuarioAtual().getUsername());
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String nomeArquivo) {
                return nomeArquivo.toLowerCase().endsWith(".txt");
            }
        });

        if (files != null) {
            for (File file : files) {
            	
               
            	try {
			    	InputStream is = new FileInputStream(file); // bytes
					InputStreamReader isr = new InputStreamReader(is); // char
					BufferedReader br = new BufferedReader(isr); // string
					
					String line = br.readLine();
					
					
					
					Playlist playlistAtual = new Playlist(extrairNome(file.getName()));
					while(line != null){
						File fileSong = new File(line);
						Musica song = criarMusica(fileSong);
			            playlistAtual.addMusica(song);
						line = br.readLine();
					}
					playlistdao.adicionarPlaylist(playlistAtual);
					
					br.close();
				} 
			catch(Exception e){
				e.printStackTrace();
			}
          }
        }
    	
}
    
    private String extrairNome(String nome) {

    	// Extrair o nome sem a extensão
        String nomeSemExtensao = extrairNomeSemExtensao(nome);

        // Extrair apenas a parte desejada
        String parteDesejada = extrairParteDesejada(nomeSemExtensao);

        return parteDesejada;
    }

    private String extrairNomeSemExtensao(String nomeArquivo) {
        int indicePonto = nomeArquivo.lastIndexOf(".");
        if (indicePonto != -1) {
            return nomeArquivo.substring(0, indicePonto);
        }
        return nomeArquivo;
    }

    private String extrairParteDesejada(String nomeSemExtensao) {
        int indiceSublinhado = nomeSemExtensao.indexOf("_");
        if (indiceSublinhado != -1) {
            return nomeSemExtensao.substring(indiceSublinhado + 1);
        }
        return nomeSemExtensao;
    }
    
    
    private void loadPlaylistList() {
    	    	
        File folder = new File(usuarioAtual.getUsuarioAtual().getUsername());
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String nomeArquivo) {
                return nomeArquivo.toLowerCase().endsWith(".txt");
            }
        });

        if (files != null) {
            for (File file : files) {
            	
               
            	try {
			    	InputStream is = new FileInputStream(file); // bytes
					InputStreamReader isr = new InputStreamReader(is); // char
					BufferedReader br = new BufferedReader(isr); // string
					
					String line = br.readLine();
					
					
					
					Playlist playlistAtual = new Playlist(extrairNome(file.getName()));
					while(line != null){
						File fileSong = new File(line);
						Musica song = criarMusica(fileSong);
			            playlistAtual.addMusica(song);
						line = br.readLine();
					}
					playlistdao.adicionarPlaylist(playlistAtual);
					
					br.close();
				} 
			catch(Exception e){
				e.printStackTrace();
			}
          }
        }
    	
}
    
    private String extrairNome(String nome) {

    	// Extrair o nome sem a extensão
        String nomeSemExtensao = extrairNomeSemExtensao(nome);

        // Extrair apenas a parte desejada
        String parteDesejada = extrairParteDesejada(nomeSemExtensao);

        return parteDesejada;
    }

    private String extrairNomeSemExtensao(String nomeArquivo) {
        int indicePonto = nomeArquivo.lastIndexOf(".");
        if (indicePonto != -1) {
            return nomeArquivo.substring(0, indicePonto);
        }
        return nomeArquivo;
    }

    private String extrairParteDesejada(String nomeSemExtensao) {
        int indiceSublinhado = nomeSemExtensao.indexOf("_");
        if (indiceSublinhado != -1) {
            return nomeSemExtensao.substring(indiceSublinhado + 1);
        }
        return nomeSemExtensao;
    }
    
    
    /**
     * Cria um objeto Musica com base em um arquivo selecionado.
     *
     * @param arquivo O arquivo de áudio selecionado.
     * @return O objeto Musica criado.
     */
    private Musica criarMusica(File arquivo) {
        Musica musica = new Musica();
        musica.setArquivo(arquivo);
        musica.setTitulo(arquivo.getName());
        musica.setLocal(arquivo.getAbsolutePath());
        // Configurar as propriedades da música com base no arquivo selecionado
        return musica;
    }
    
    /**
     * Salva o caminho de um arquivo de música em um arquivo de texto.
     *
     * @param path O caminho do arquivo de música a ser salvo.
     */
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

    /**
     * Manipula o evento de clique no botão Play.
     *
     * @param event O evento de clique.
     */
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