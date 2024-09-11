package musica;

import java.util.ArrayList;

/**
 *
 * @author Samuka
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
    
    public void addMusica(Musica musica){
        playlist.add(musica);
    }
    
    public Musica buscaMusica(String caminho){
        for(Musica musica:playlist){
            if(musica.getCaminho().equals(caminho)){
                return musica;
            }
        }
        return null;
    }
}
