package musica;

import java.util.ArrayList;

/**
 *
 * @author Samuel Lopes
 * @author Luiz Fernando Lopes
 * @author Pedro Lopes
 */
public class Playlist {
    private ArrayList<Musica> playlist;
    
    public Playlist(){
        playlist = new ArrayList<>();
    }
    
    public ArrayList<Musica> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<Musica> playlist) {
        this.playlist = playlist;
    }
    
    /**
     * Metodo para adicionar musicas na playlist
     * @param musica 
     */
    
    public void addMusica(Musica musica){
        playlist.add(musica);
    }
    
    /**
     * Metodo para buscar uma musica no ArrayList playlist
     * @param caminho
     * @return 
     */
    
    public Musica buscaMusica(String caminho){
        for(Musica musica:playlist){
            if(musica.getCaminho().equals(caminho)){
                return musica;
            }
        }
        return null;
    }
}
