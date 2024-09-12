
package musica;

import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * @author Samuka
 */
public class Musica {
    private String titulo;
    private String artista;
    private String album;
    private Image capa;
    private String caminho;
    private Duration duracao;
    
    public Musica(String titulo, String artista, String album, Image capa, String caminho){
        this.album = album;
        this.artista = artista;
        this.caminho = caminho;
        this.capa = capa;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Image getCapa() {
        return capa;
    }

    public void setCapa(Image capa) {
        this.capa = capa;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }
    
    public String toString(){
        return "Titulo: " + this.titulo +
                "\nArtista: " + this.artista +
                "\nAlbum: " + this.album;
    }
    
}
