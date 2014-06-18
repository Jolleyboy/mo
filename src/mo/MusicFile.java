/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;

/**
 *
 * @author Joshua
 */
public class MusicFile {
    private Tag tag;
    
    public MusicFile(AudioFile af) {
        //get the tag
    }
    
    public void setArtist(String artist) {
        try {
            tag.setField(FieldKey.ARTIST, artist);
        } catch (KeyNotFoundException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FieldDataInvalidException ex) {
            Logger.getLogger(MusicFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getArtist() {
        return tag.getFirst(FieldKey.ARTIST);
    }
    
}
