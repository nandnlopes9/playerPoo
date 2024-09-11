/*
    ARRUMAR UM JEITO DE CONSEGUIR PEGAR A DURAÇÃO DA MUSICA
    FAZER A BARRA DE PROGRESSO FUNCIONAR
    COLOCAR O BOTÃO DE ADICIONAR PASTA
    CRIAR ARQUIVO JAVADOC
*/

package player;

import musica.*;
import arquivo.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author Samuka
 */
public class FXMLDocumentController implements Initializable {
    
    private Media musicaAtual;
    private MediaPlayer tocadorDeMusica;
    private Playlist playlist = new Playlist();
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
    private Label artistaTocandoAgora;
    @FXML
    private ImageView capaTocandoAgora;
    @FXML
    private Label tituloTocandoAgora;
    @FXML
    private VBox musicasTela;
    @FXML
    private AnchorPane telaPrincipal;
    @FXML
    private Rectangle playerTela;
    
    private boolean play=false;
    
    /**
     * Essa função muda a imagem do botão play conforme o usuário clica
     */
    @FXML
    void playClick(MouseEvent event) {
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
    void proximaClick(MouseEvent event) {
        indiceMusica++;
        
        if(indiceMusica > playlist.getPlaylist().size()){
            indiceMusica = 0;
        }
        this.reinicializaMusica(indiceMusica);
        Musica musicaAtual = playlist.buscaMusica(this.musicaAtual.getSource());
        
        capaTocandoAgora.setImage(musicaAtual.getCapa());
        artistaTocandoAgora.setText(musicaAtual.getArtista());
        tituloTocandoAgora.setText(musicaAtual.getTitulo());
        this.play = false;
        this.playClick(null);
    }

    @FXML
    void anteriorClick(MouseEvent event) {
        
        indiceMusica--;
        
        if(indiceMusica < 0){
            indiceMusica = playlist.getPlaylist().size()-1;
        }
        this.reinicializaMusica(indiceMusica);
        
        Musica musicaAtual = playlist.buscaMusica(this.musicaAtual.getSource());
        capaTocandoAgora.setImage(musicaAtual.getCapa());
        artistaTocandoAgora.setText(musicaAtual.getArtista());
        tituloTocandoAgora.setText(musicaAtual.getTitulo());
        this.play = false;
        this.playClick(null);
    }
    
    public void reinicializaMusica(int idMusica){
        playMusica(playlist.getPlaylist().get(idMusica).getCaminho());
    }
    
    public void playMusica(String caminho){
        musicaAtual = null;
        if(tocadorDeMusica != null){
            tocadorDeMusica.dispose();
        }
        musicaAtual = new Media(caminho);
        tocadorDeMusica = new MediaPlayer(musicaAtual);
    }
    
    public ArrayList<HBox> addMusica(){
        addTodasMusicas();
        ArrayList<HBox> musicas = new ArrayList<>();
        for(Musica musica : playlist.getPlaylist()){
            HBox containerMusica = new HBox();
            VBox dadosMusica = new VBox();
            ImageView capa = new ImageView(musica.getCapa());
            Label titulo = new Label(musica.getTitulo()+"\n");
            Label album = new Label(musica.getAlbum()+"\n");
            Label artista = new Label(musica.getArtista()+"\n");
            //Procurar uma maneira melhor de pegar a duração das musicas sem precisar inicia-las
            
            titulo.setTextFill(Color.WHITE);
            album.setTextFill(Color.WHITE);
            artista.setTextFill(Color.WHITE);
            
            titulo.setFont(new Font(25));
            titulo.setPadding(new Insets(0,0,10,0));
            album.setPadding(new Insets(0,0,5,0));
            artista.setPadding(new Insets(0,0,5,0));
            
            dadosMusica.getChildren().add(titulo);
            dadosMusica.getChildren().add(album);
            dadosMusica.getChildren().add(artista);
            dadosMusica.setPadding(new Insets(0,0,0,10));
            
            capa.setFitWidth(130);
            capa.setFitHeight(130);
            capa.setPreserveRatio(true);
            
            containerMusica.getChildren().add(capa);
            containerMusica.getChildren().add(dadosMusica);
            containerMusica.setPadding(new Insets(0,0,10,10));
            containerMusica.setCursor(Cursor.HAND);
            containerMusica.setMaxWidth(900);
            containerMusica.setOnMouseClicked((MouseEvent event)->{
                playMusica(musica.getCaminho());
                capaTocandoAgora.setImage(musica.getCapa());
                artistaTocandoAgora.setText(musica.getArtista());
                tituloTocandoAgora.setText(musica.getTitulo());
                play = false;
                playClick(null);
            });
            musicas.add(containerMusica);
        }
        return musicas;
    }
    
    public void addTodasMusicas(){
        HashMap<String, String> metadadosMusica;
        for(String caminho : ManipulaArquivo.buscaMusicas()){
            metadadosMusica = ManipulaArquivo.getMetadados(caminho);
            Image capa = ManipulaArquivo.carregaCapa(caminho);
            if(metadadosMusica != null){
                Musica musica = new Musica(metadadosMusica.get("titulo"), metadadosMusica.get("artista"), metadadosMusica.get("album"), capa, new File(caminho).toURI().toString());
                playlist.addMusica(musica);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        musicasTela.getChildren().addAll(addMusica());
        musicasTela.setMaxWidth(910);
    }
}
