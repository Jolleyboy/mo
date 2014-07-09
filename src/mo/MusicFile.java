package mo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class MusicFile
 *  Holds the tag, header, and path information for a music file
 */
public class MusicFile {
    private Logger logger = LoggerFactory.getLogger(Model.class);
    private Tag tag;                //The ID3 tag 
    private Path path;              //The Path to the music file
    private AudioHeader header;     //The header of the music file
    private String duration = "?:??";
    private int id;
    private boolean fIdentified = false;
    MusicIdentifier mi = new MusicIdentifier();
    /**
     * Constructor that takes an AudioFile
     * @param af 
     */
    public MusicFile(AudioFile af) {
        tag = af.getTag();//get the tag
        header = af.getAudioHeader();
        path = af.getFile().toPath();
        if (tag.getFirst(FieldKey.CUSTOM1) == "identified") {
            fIdentified = true;
        }
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
            duration = durationString(af);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            logger.error(ex.getMessage());
        }
        if (tag.getFirst(FieldKey.CUSTOM1) == "identified") {
            fIdentified = true;
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
            duration = durationString(af);
        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex) {
            logger.error(ex.getMessage());
        }
        if (tag.getFirst(FieldKey.CUSTOM1) == "identified") {
            fIdentified = true;
        }
    
    }
    
    
    /**
     * Sets the artist for this music file's tag
     * @param artist 
     */
    public void setArtist(String artist) {
        try {
            tag.setField(FieldKey.ARTIST, artist);
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            logger.error(ex.getMessage());
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
            logger.error(ex.getMessage());
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
            logger.error(ex.getMessage());
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
            logger.error(ex.getMessage());
        }
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){ 
            return true;
        }
        if (!(other instanceof MusicFile))
        {
            return false;
        }
        
        MusicFile mf = (MusicFile) other;
        return (this.getArtist().equals(mf.getArtist()) &&
                this.getTitle().equals(mf.getTitle()) &&
                this.getAlbum().equals(mf.getAlbum()) &&
                this.getGenre().equals(mf.getGenre()) &&
                this.getDuration().equals(mf.getDuration()) &&
                this.getPath().equals(mf.getPath()));
    }

    @Override
    public int hashCode() {
        return id;
    }
    
    public boolean isIdentified() {
        return fIdentified;
    } 
    
    public void setIdentified() {
        try {
            tag.setField(FieldKey.CUSTOM1, "identified");
        } catch (KeyNotFoundException | FieldDataInvalidException ex) {
            java.util.logging.Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String durationString(AudioFile af) {
        int length = af.getAudioHeader().getTrackLength();       
        int hr = length / 3600;
        length %= 3600;
        int min = length / 60;
        int sec = length % 60;
        String strLngth = "";
        if (hr > 0) {
            strLngth = "" + hr + ":";
        }
        strLngth += min + ":" + sec;
        return strLngth;
    }    
}
