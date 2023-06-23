package br.ufrn.imd.controller;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.io.File;
import java.util.ArrayList;

import br.ufrn.imd.model.Musica;

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
            observableList.add(musica);
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