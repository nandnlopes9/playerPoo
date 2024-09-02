package player;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Samuka
 */
public class FXMLDocumentController implements Initializable {
    
    private Media musicaAtual;
    private MediaPlayer tocadorDeMusica;
    private ArrayList<String> musicas = new ArrayList<String>();
    private int indiceMusica = 0;
    
    @FXML
    private ProgressBar barraProgresso;
    @FXML
    private ImageView btnAnterior;
    @FXML
    private ImageView btnPlay;
    @FXML
    private ImageView btnProximo;
    @FXML
    private Label cantorMusicaTocandoAgora;
    @FXML
    private ImageView capaTocandoAgora;
    @FXML
    private Label tituloMusicaTocandoAgora;
    @FXML
    private VBox todasMusicas;
    
    private boolean play=false;
    
    /**
     * Essa função muda a imagem do botão play conforme o usuário clica
     */
    @FXML
    void playClick() {
        if(!play){
            btnPlay.setImage(new Image(getClass().getResourceAsStream("../assets/BotoesPlayPause/Pause.png")));
            play=true;
            tocadorDeMusica.play();
        }else{
            btnPlay.setImage(new Image(getClass().getResourceAsStream("../assets/BotoesPlayPause/Play.png")));
            play=false;
            tocadorDeMusica.pause();
        }
    }
    
        @FXML
    void proximaClick(ActionEvent event) {
        indiceMusica++;
        
        if(indiceMusica > musicas.size()){
            indiceMusica = 0;
        }
        
        this.reinicializaMusica(indiceMusica);
        this.play = false;
        this.playClick();
    }

    @FXML
    void anteriorClick(ActionEvent event) {
        indiceMusica--;
        
        if(indiceMusica < 0){
            indiceMusica = musicas.size();
        }
        
        this.reinicializaMusica(indiceMusica);
        this.play = false;
        this.playClick();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reinicializaMusica(indiceMusica);
    }
    
    public void reinicializaMusica(int idMusica){
        musicaAtual = null;
        tocadorDeMusica = null;
        
        musicaAtual = new Media(new File(musicas.get(idMusica)).toURI().toString());
        tocadorDeMusica = new MediaPlayer(musicaAtual);
        
    }

    
}