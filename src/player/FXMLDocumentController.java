/*
    CRIAR ARQUIVO JAVADOC
*/

package player;

import musica.*;
import arquivo.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

/**
 *
 * @author Samuka
 */
public class FXMLDocumentController implements Initializable {
    
    private Media musicaAtual;
    private MediaPlayer tocadorDeMusica;
    private Playlist playlist = new Playlist();
    private int indiceMusica = 0;
    private boolean play=false;
    private Duration duracaoTotalMusicaAtual;
    private int idMusica=0;
    
    @FXML
    private Label artistaTocandoAgora;

    @FXML
    private Slider barraProgresso;

    @FXML
    private ImageView btnAnterior;

    @FXML
    private ImageView btnPlay;

    @FXML
    private ImageView btnProximo;

    @FXML
    private HBox btn_novaPasta;

    @FXML
    private ImageView capaTocandoAgora;

    @FXML
    private Label duracaoTotal;

    @FXML
    private VBox musicasTela;

    @FXML
    private AnchorPane telaPrincipal;

    @FXML
    private Label timer;

    @FXML
    private Label tituloTocandoAgora;
    @FXML
    private Slider barraVolume;
    @FXML
    private ImageView imgVolume;
    
    public void barraVolume(){
        barraVolume.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                if(newValue.doubleValue() >= 0.5){
                    imgVolume.setImage(new Image(new File(Paths.get("").toAbsolutePath().resolve("src/assets/volumeAlto.png").toString()).toURI().toString()));
                }else if(newValue.doubleValue() < 0.5 && newValue.doubleValue() > 0){
                    imgVolume.setImage(new Image(new File(Paths.get("").toAbsolutePath().resolve("src/assets/volumeBaixo.png").toString()).toURI().toString()));
                }else{
                    imgVolume.setImage(new Image(new File(Paths.get("").toAbsolutePath().resolve("src/assets/mute.png").toString()).toURI().toString()));
                }
                tocadorDeMusica.setVolume(newValue.doubleValue());
            }
        });
    }
    
    public void barraProgresso(){
        tocadorDeMusica.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                atualizaTempo(newValue, timer);
                barraProgresso.setValue(newValue.toSeconds());
                if(barraProgresso.getValue() == duracaoTotalMusicaAtual.toSeconds()){
                    proximaClick(null);
                }
            }            
        });
        barraProgresso.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (barraProgresso.isValueChanging()) {
                    tocadorDeMusica.seek(Duration.seconds(newValue.doubleValue()));
                }
            }
        });
    }
    
    private void atualizaBarra(Duration tempoTotal){
        barraProgresso.setMax(tempoTotal.toSeconds());
    }
    
    private void atualizaTempo(Duration tempoAtual, Label tempoLabel) {
         
        if (tempoAtual == null || tempoLabel == null) {
            return; // Evita problemas com valores nulos
        }

        // Converte a duração atual para minutos e segundos
        int minutos = (int) (tempoAtual.toMinutes());
        int segundos = (int) (tempoAtual.toSeconds() % 60);

        // Formata o tempo como "MM:SS"
        String timeString = String.format("%02d:%02d", minutos, segundos);

        // Atualiza o texto do Label
        tempoLabel.setText(timeString);
}
    
    /**
     * Essa função abre a janela para selecionar um diretorio 
     * @param event 
     */
    @FXML
    public void eventoBtnPasta(MouseEvent event) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Escolha um diretório");
            
            File selectedDirectory = directoryChooser.showDialog(null);
            
            if (selectedDirectory != null) {
                // Definindo o caminho da nova pasta
                Path newFolderPath = Paths.get(selectedDirectory.getAbsolutePath());
                
                // Guardando o caminho selecionado pelo usuario, em uma variavel
                String caminhoPasta = newFolderPath.toString();
                musicasTela.getChildren().clear();
                musicasTela.getChildren().addAll(addMusica(caminhoPasta));
                indiceMusica=0;
            }
    }
    
    /**
     * Essa função muda a imagem do botão play conforme o usuário clica e inicia a música ou pausa dependendo do estado da variável play
     */
    @FXML
    public void playClick(MouseEvent event) {
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
    
    /**
     * Essa função avança para a próxima música
     * @param event 
     */
    @FXML
    public void proximaClick(MouseEvent event) {
        indiceMusica++;
        
        if(indiceMusica >= playlist.getPlaylist().size()){
            indiceMusica = 0;
        }
        System.out.println(indiceMusica);
        System.out.println(playlist.getPlaylist().size());
        this.reinicializaMusica(indiceMusica);
        Musica musicaAtual = playlist.buscaMusica(this.musicaAtual.getSource());
        atualizaMusicaAtual(musicaAtual);
        tocadorDeMusica.setOnReady(() -> {
            Duration tempoTotal = tocadorDeMusica.getTotalDuration();
            atualizaTempo(tempoTotal, this.duracaoTotal);
            atualizaBarra(tempoTotal);
            duracaoTotalMusicaAtual = tempoTotal;
        });
        tocadorDeMusica.setVolume(barraVolume.getValue());
        this.play = false;
        timer.setText("00:00");
        barraProgresso();
        this.playClick(null);
    }
    
    /**
     * Essa função volta para a música anterior
     * @param event 
     */
    @FXML
    public void anteriorClick(MouseEvent event) {
        indiceMusica--;
        
        if(indiceMusica < 0){
            indiceMusica = playlist.getPlaylist().size()-1;
        }
        this.reinicializaMusica(indiceMusica);
        
        Musica musicaAtual = playlist.buscaMusica(this.musicaAtual.getSource());
        tocadorDeMusica.setOnReady(() -> {
            Duration tempoTotal = tocadorDeMusica.getTotalDuration();
            atualizaTempo(tempoTotal, duracaoTotal);
            atualizaBarra(tempoTotal);
            duracaoTotalMusicaAtual = tempoTotal;
        });
        tocadorDeMusica.setVolume(barraVolume.getValue());
        atualizaMusicaAtual(musicaAtual);
        this.play = false;
        timer.setText("00:00");
        barraProgresso();
        this.playClick(null);
    }
    
    public void atualizaMusicaAtual(Musica musica){
        capaTocandoAgora.setImage(musica.getCapa());
        artistaTocandoAgora.setText(musica.getArtista());
        tituloTocandoAgora.setText(musica.getTitulo());
    }
    
    /**
     * Essa função é ativada sempre que o botão de anterior ou próximo é clicado, assim ela envia para a função playMusica a música que será tocada
     * @param idMusica 
     */
    
    public void reinicializaMusica(int idMusica){
        playMusica(playlist.getPlaylist().get(idMusica).getCaminho());
    }
    
    /**
     * Essa função atualiza o objeto tocadorMusica com a musica que deverá ser tocada
     * @param caminho 
     */
    public void playMusica(String caminho){
        musicaAtual = null;
        if(tocadorDeMusica != null){
            tocadorDeMusica.dispose();
        }
        musicaAtual = new Media(caminho);
        tocadorDeMusica = new MediaPlayer(musicaAtual);
    }
    
    /**
     * Essa função cria os container das músicas e retorna uma lista com todas as músicas formatadas para serem colocadas na tela
     * @return 
     */
    
    public ArrayList<HBox> addMusica(String caminho){
        addTodasMusicas(caminho);
        ArrayList<HBox> musicas = new ArrayList<>();
        for(Musica musica : playlist.getPlaylist()){
            HBox containerMusica = new HBox();
            VBox dadosMusica = new VBox();
            ImageView capa = new ImageView(musica.getCapa());
            Label titulo = new Label(musica.getTitulo()+"\n");
            Label album = new Label(musica.getAlbum()+"\n");
            Label artista = new Label(musica.getArtista()+"\n");
            //Procurar uma maneira melhor de pegar a duração das musicas sem precisar inicia-las
            
            titulo.setFont(new Font(25));
            titulo.setTextFill(Color.WHITE);
            album.setTextFill(Color.WHITE);
            artista.setTextFill(Color.WHITE);
            
            album.setPadding(new Insets(5,0,0,0));
            artista.setPadding(new Insets(5,0,0,0));
            
            dadosMusica.getChildren().add(titulo);
            dadosMusica.getChildren().add(album);
            dadosMusica.getChildren().add(artista);
            dadosMusica.setPadding(new Insets(0,0,0,10));
            
            capa.setFitWidth(130);
            capa.setFitHeight(130);
            capa.setPreserveRatio(true);
            
            containerMusica.getChildren().add(capa);
            containerMusica.getChildren().add(dadosMusica);
            containerMusica.setPadding(new Insets(0,0,10,15));
            containerMusica.setCursor(Cursor.HAND);
            containerMusica.setOnMouseClicked((MouseEvent event)->{
                capaTocandoAgora.setImage(musica.getCapa());
                artistaTocandoAgora.setText(musica.getArtista());
                tituloTocandoAgora.setText(musica.getTitulo());
                playMusica(musica.getCaminho());
                play = false;
                tocadorDeMusica.setVolume(barraVolume.getValue());
                tocadorDeMusica.setOnReady(() -> {
                    Duration tempoTotal = tocadorDeMusica.getTotalDuration();
                    atualizaTempo(tempoTotal, duracaoTotal);
                    atualizaBarra(tempoTotal);
                    duracaoTotalMusicaAtual = tempoTotal;
                });
                playClick(null);
                timer.setText("00:00");
                indiceMusica = musica.getId();
                barraProgresso();
                
            });
            musicas.add(containerMusica);
        }
        return musicas;
    }
    
    /**
     * Essa função cria objetos Musica com as informações necessárias e armazena na playlist
     */
    public void addTodasMusicas(String caminho){
        HashMap<String, String> metadadosMusica;
        for(String url : ManipulaArquivo.buscaMusicas(caminho)){
            metadadosMusica = ManipulaArquivo.getMetadados(url);
            Image capa = ManipulaArquivo.carregaCapa(url);
            if(metadadosMusica != null){
                Musica musica = new Musica(metadadosMusica.get("titulo"), metadadosMusica.get("artista"), metadadosMusica.get("album"), capa, new File(url).toURI().toString(), idMusica++);
                playlist.addMusica(musica);
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        musicasTela.getChildren().addAll(addMusica(null));
        musicasTela.setMaxWidth(910);
        musicasTela.setMaxHeight(865);
        playMusica(playlist.getPlaylist().get(indiceMusica).getCaminho());
        atualizaMusicaAtual(playlist.buscaMusica(playlist.getPlaylist().get(indiceMusica).getCaminho()));
        tocadorDeMusica.setOnReady(() -> {
            Duration tempoTotal = tocadorDeMusica.getTotalDuration();
            atualizaTempo(tempoTotal, duracaoTotal);
            atualizaBarra(tempoTotal);
            duracaoTotalMusicaAtual = tempoTotal;
        });
        barraProgresso();
        barraVolume();
        tocadorDeMusica.setVolume(barraVolume.getValue());
    }
}
