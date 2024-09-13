
package musica;

import javafx.scene.image.Image;

/**
 * @author Luiz Fernando Lopes
 * @author Samuel Lopes
 * @author Pedro Lopes
 */
public class Musica {
    private String titulo;
    private String artista;
    private String album;
    private Image capa;
    private String caminho;
    private int id;
    private double duracao;
    
    public Musica(String titulo, String artista, String album, Image capa, String caminho, int id){
        this.album = album;
        this.artista = artista;
        this.caminho = caminho;
        this.capa = capa;
        this.titulo = titulo;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public String toString(){
        return "Titulo: " + this.titulo +
                "\nArtista: " + this.artista +
                "\nAlbum: " + this.album;
    }
    
}
