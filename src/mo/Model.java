package mo;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mo.MusicFile;

/***************************************************
* Class Model
*    Handles everything file/database/data related.
****************************************************/

public class Model {

  private static Model singleton = new Model();
  private ObservableList<MusicFile> listMF;
  private File selectedDirectory;
  private Model() {
    //for testing purposes only!!!
    listMF = FXCollections.observableArrayList(
            new MusicFile("TestTitle","TestArtist","TestAlbum","TestGenre","4:00Test"),
            new MusicFile("Paradise","Coldplay","Mylo Xyloto","Alternative","4:38"),
            new MusicFile("Somebody Told Me","The Killers","Hot Fuss","Alternative","3:17"),
            new MusicFile("It's Time","Imagine Dragons","Continued Silence","Indie Rock","3:59")
        );
    //to prevent making seperate instances
  }

 
  public static Model getInstance() {
    return singleton;
  }
  
  public ObservableList<MusicFile> getList() {
      return listMF;
  }
  
  public void setList(ObservableList<MusicFile> listMF) {
      this.listMF = listMF;
  }
  
  public void setDirectory(File directory) {
      this.selectedDirectory = directory;
  }
  
  public File getDirectory() {
      return selectedDirectory;
  }
}