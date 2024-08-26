package arquivo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v22Tag;
import org.jaudiotagger.tag.id3.ID3v23Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;



/**
 *
 * @author Samuka
 */
public class ManipulaArquivo {
    
    public static ArrayList<String> buscaMusicas(){
        ArrayList<String> musicas = new ArrayList<>();        
        String extensao = ".mp3";
        String url = System.getProperty("user.home")+"/Music";
        url.replaceAll("\\\\", "/");
        Path caminho = Paths.get(url);
        try{
            Files.walkFileTree(caminho, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path arquivo, BasicFileAttributes atributos) throws IOException {
                    // Verifica se o arquivo tem a extensão desejada
                    if (arquivo.toString().endsWith(extensao)) {
                        musicas.add(arquivo.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path diretório, IOException excecao) throws IOException {
                    // Após visitar o diretório, continue com o próximo
                    return FileVisitResult.CONTINUE;
                }
            });
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        
        return musicas;
    }
    
    public static HashMap<String, String> getMetadados(String caminho){
        
        try{
            File musica = new File(caminho);
            AudioFile arquivo = AudioFileIO.read(musica);
            Tag tag = arquivo.getTag();
            
            //o bloco if analisa qual é o tipo de metadados do arquivo mp3 e chama a função correta de acordo com o tipo
            
            if (tag instanceof ID3v23Tag) { 
                return processarTagID3v23((ID3v23Tag) tag);
            } else if (tag instanceof ID3v24Tag) {
                return processarTagID3v24((ID3v24Tag) tag);
            } else if (tag instanceof ID3v22Tag) {
                return processarTagID3v22((ID3v22Tag) tag);
            } else {
                return null;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static Image carregaCapa(String caminho){
        Image capa = null;
        try{
            File musica = new File(caminho);
            AudioFile arquivo = AudioFileIO.read(musica);
            Tag tag = arquivo.getTag();
            
            if(tag instanceof ID3v23Tag){
                return processarCapaID3v23((ID3v23Tag) tag);
            } else if (tag instanceof ID3v24Tag) {
                return processarCapaID3v24((ID3v24Tag) tag);
            } else if (tag instanceof ID3v22Tag) {
                return processarCapaID3v22((ID3v22Tag) tag);
            } else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    private static Image processarCapaID3v23(ID3v23Tag iD3v23Tag) throws IOException{
        byte[] capaByte = iD3v23Tag.getFirstArtwork().getBinaryData();
                
        if(capaByte != null){
            ByteArrayInputStream bais = new ByteArrayInputStream(capaByte);
            BufferedImage imagemBuffered = ImageIO.read(bais);
                    
            return SwingFXUtils.toFXImage(imagemBuffered, null);
        }
        return null;
    }
    
    private static Image processarCapaID3v24(ID3v24Tag iD3v24Tag) throws IOException{
        byte[] capaByte = iD3v24Tag.getFirstArtwork().getBinaryData();
                
        if(capaByte != null){
            ByteArrayInputStream bais = new ByteArrayInputStream(capaByte);
            BufferedImage imagemBuffered = ImageIO.read(bais);
                    
            return SwingFXUtils.toFXImage(imagemBuffered, null);
        }
        return null;
    }
    
    private static Image processarCapaID3v22(ID3v22Tag iD3v22Tag) throws IOException{
        byte[] capaByte = iD3v22Tag.getFirstArtwork().getBinaryData();
                
        if(capaByte != null){
            ByteArrayInputStream bais = new ByteArrayInputStream(capaByte);
            BufferedImage imagemBuffered = ImageIO.read(bais);
                    
            return SwingFXUtils.toFXImage(imagemBuffered, null);
        }
        return null;
    }
    
    private static HashMap<String, String> processarTagID3v23(ID3v23Tag iD3v23Tag) {
        HashMap<String,String> tags = new HashMap<>();
        tags.put("titulo", iD3v23Tag.getFirst(FieldKey.TITLE));
        tags.put("artista", iD3v23Tag.getFirst(FieldKey.ARTIST));
        tags.put("album", iD3v23Tag.getFirst(FieldKey.ALBUM));
        return tags;
    }

    private static HashMap<String, String> processarTagID3v24(ID3v24Tag iD3v24Tag) {
        HashMap<String,String> tags = new HashMap<>();
        tags.put("titulo", iD3v24Tag.getFirst(FieldKey.TITLE));
        tags.put("artista", iD3v24Tag.getFirst(FieldKey.ARTIST));
        tags.put("album", iD3v24Tag.getFirst(FieldKey.ALBUM));
        return tags;
    }

    private static HashMap<String, String> processarTagID3v22(ID3v22Tag iD3v22Tag) {
        HashMap<String,String> tags = new HashMap<>();
        tags.put("titulo", iD3v22Tag.getFirst(FieldKey.TITLE));
        tags.put("artista", iD3v22Tag.getFirst(FieldKey.ARTIST));
        tags.put("album", iD3v22Tag.getFirst(FieldKey.ALBUM));
        return tags;
    }
    
}
