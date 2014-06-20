package mo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import mo.MusicFile;

/***************************************************
* Class Model
*    Handles everything file/database/data related. al
****************************************************/

public class Model {

  private static Model singleton = new Model();

  private Model() {
    //to prevent making seperate instances
  }

  public static Model getInstance() {
    return singleton;
  }
  public List<MusicFile> findFiles(Path directory){
    List<MusicFile> listMF; 
    listMF = new ArrayList<MusicFile>();
    //check every file in every directory under this path
      
      //if the file is an mp3 or .ogg or .flac or any other music file
        //make a new File
        //make a new AudioFile(newFile)
        //make a new MusicFile(audioFile)
        //add the musicFile to the list
   return listMF;
  }
}
