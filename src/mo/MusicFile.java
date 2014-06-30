package mo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

/**
 *  Class MusicFile
 *  Holds the tag, header, and path information for a music file
 */
public class MusicFile {
    private Tag tag;                //The ID3 tag 
    private Path path;              //The Path to the music file
    private AudioHeader header;     //The header of the music file
    private String duration;
    private int id;
    /**
     * Constructor that takes an AudioFile
     * @param af 
     */
    public MusicFile(AudioFile af) {
        tag = af.getTag();//get the tag
        header = af.getAudioHeader();
        path = af.getFile().toPath();
    }
    
    /**
     * Constructor that takes a file
     * @param file 
     */
    public MusicFile(File file) {
        AudioFile af;
        try {
            af = AudioFileIO.read(file);
            tag = af.getTag();//get the tag
            header = af.getAudioHeader();
            path = af.getFile().toPath();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    /**
     * Constructor that takes a filename
     * @param filename 
     */
    public MusicFile(String filename) {
        File file = new File(filename);
        AudioFile af;
        try {
            af = AudioFileIO.read(file);
            tag = af.getTag();//get the tag
            header = af.getAudioHeader();
            path = af.getFile().toPath();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public MusicFile(String name, String artist, String album, String genre, String time) {
        File file = new File("music\\seattle.mp3");
        AudioFile af = null;
        try {
            af = AudioFileIO.read(file);
            tag = af.getTag();//get the tag
            header = af.getAudioHeader();
            path = af.getFile().toPath();
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTitle(name);
        this.setArtist(artist);
        this.setAlbum(album);
        this.setGenre(genre);
        this.duration = time;
    }
    /**
     * Sets the artist for this music file's tag
     * @param artist 
     */
    public void setArtist(String artist) {
        try {
            tag.setField(FieldKey.ARTIST, artist);
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getPath() {
        return path.toString();
    }
    /**
     * Returns the artist from the tag as a string
     * @return 
     */
    public String getArtist() {
        return tag.getFirst(FieldKey.ARTIST);
    }
    
    /**
     * Returns the title from the tag as a string
     * @return 
     */
    public String getTitle(){
        return tag.getFirst(FieldKey.TITLE);
    }
    
    /**
     * Sets the title for this music file's tag 
     * @param title 
     */
    public void setTitle(String title){
        try {
            tag.setField(FieldKey.TITLE, title);
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the genre
     * @return 
     */
    public String getGenre(){
        return tag.getFirst(FieldKey.GENRE);
    }
    
    /**
     * Sets the Genre 
     * @param genre 
     */
    public void setGenre(String genre) {
        try {
            tag.setField(FieldKey.GENRE, genre);
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the album from the music file
     * @return 
     */
    public String getAlbum(){
        return tag.getFirst(FieldKey.ALBUM);
    }
    
    /**
     * Sets the album
     * @param album 
     */
    public void setAlbum(String album){
        try {
            tag.setField(FieldKey.ALBUM, album);
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
