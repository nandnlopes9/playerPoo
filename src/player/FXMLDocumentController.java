package player;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Samuka
 */
public class FXMLDocumentController implements Initializable {
    
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
    void playClick(MouseEvent event) {
        if(!play){
            btnPlay.setImage(new Image(getClass().getResourceAsStream("../assets/BotoesPlayPause/Pause.png")));
            play=true;
        }else{
            btnPlay.setImage(new Image(getClass().getResourceAsStream("../assets/BotoesPlayPause/Play.png")));
            play=false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
