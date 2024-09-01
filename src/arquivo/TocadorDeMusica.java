/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arquivo;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Pedro
 */
public class TocadorDeMusica {
    private Clip clip;
    //Esta é a classe que toca música em si
    //Ele só trabalha com .wav, então por isso é necessário um conversor
    
    private Long frame;
    //É o frame em que estou
    
    private AudioSystem audioAdmin;
    //Objeto conversor de audio
    
    AudioInputStream audioStream;
    
    private String enderecoDoAudio;
    
    private String status;
    
    public TocadorDeMusica(String enderecoDoAudio) throws UnsupportedAudioFileException, IOException, LineUnavailableException
                                                          //Essas são exceções obrigatórias do método
                                                          //getAudioInputStream
        {
        this.criaStreamDoAudio();
        //Este método inicializa a stream do áudio
    }
    
    public void criaStreamDoAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        this.audioStream = this.audioAdmin.getAudioInputStream(new File(enderecoDoAudio).getAbsoluteFile());
        //Isso aqui registra o stream do áudio dentro da variável
        
        this.clip = audioAdmin.getClip();
        //Esse aqui cria uma referência do tocador (semelhante à declaração de um objeto)
        
        this.clip.open(audioStream);
        //Abre o arquivo de audio baseado no stream
    }
    
    public void tocar(){
        this.clip.start();
        //Começa a tocar
        
        this.status = "tocando";
        //Aqui, é possível gerir o status do player para, no futuro, tocar ou parar
    }
    
    public void pausar() throws JaEstaTocandoException{
        if(this.status.equals("tocando")){
            this.frame = this.clip.getMicrosecondPosition();
            //Pega a posição de onde parou
            
            clip.stop();
            status = "pausado";
        }  
        else{
            throw new JaEstaTocandoException("A musica ja esta pausada!");
        }
    }
    
    public void voltarATocar() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        if(this.status.equals("pausado")){
            clip.close();
            
            this.criaStreamDoAudio();
            //Para voltar a tocar, é preciso fechar o arquivo antes e reiniciar ele
            clip.setMicrosecondPosition(frame);
            //Põe a posição onde a musica parou
            this.tocar();
        }
    }
    
    public void reiniciar() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        this.clip.stop();
        this.clip.close();
        this.criaStreamDoAudio();
        this.frame = 0L;
        this.clip.setMicrosecondPosition(0);
        this.tocar();
    }
    
    public void parar(){
        this.frame = 0L;
        this.clip.stop();
        this.clip.close();
    }
}


