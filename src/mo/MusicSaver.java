/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * Saves the changes we've made to the MusicFile to the hard drive following
 * A user specified file name and folder structure
 * @author Jolley
 */
public class MusicSaver {
    /**
     * Change the name of the file based on attributes
     * @param mf
     * @param attributes 
     */
    public void changeFilename(MusicFile mf, String[] attributes) {
        String name = "";
        //add each attribute to the name of the file
        for (int i = 0; i < attributes.length; i++) {
            name += attributes[i];
            if (i != attributes.length - 1) {
                name += " - ";
            }
        }
        mf.setNewName(name);
    }
    
    /**
     * Save each file following the attribute list
     * @param attributes
     * @param topDirectory 
     */
    public void saveFiles(String[] attributes, File topDirectory) {
        Model model = Model.getInstance();
        ObservableList<MusicFile> ol = model.getList();
        String name = "";
        
        //loop through each music file in the model
        for (MusicFile mf : ol) {
            for (int i = 0; i < attributes.length; i++) {
                // call each method as appropriate
                //name += attributes[i] + "/";
            }
            //save the tag info
            File file = new File(mf.getPath());
            AudioFile af;
            try {
                af = AudioFileIO.read(file);
                af.setTag(mf.getTag()); //write the tag we've been working with
                af.commit(); //commit writes the tag to the actual file
            } catch (CannotReadException | IOException | TagException |
                     ReadOnlyFileException | InvalidAudioFrameException |
                     CannotWriteException ex) {
                ex.getMessage();
                ex.printStackTrace();
            }
            //creates a new file in the approrpriate place
            file.renameTo(new File(topDirectory.getAbsolutePath() + name + mf.getNewName()));
        }
    }
}
 