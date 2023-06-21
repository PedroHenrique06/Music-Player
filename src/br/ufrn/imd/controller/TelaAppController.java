package br.ufrn.imd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;

public class TelaAppController {

    @FXML
    private Button chooseButton;

    @FXML
    private Label songLabel;

    private File selectedSong;

    @FXML
    private void handleChooseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Música");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Arquivos de Áudio", "*.mp3", "*.wav", "*.ogg"),
                new ExtensionFilter("Todos os Arquivos", "*.*"));

        selectedSong = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedSong != null) {
            songLabel.setText(selectedSong.getName());
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

