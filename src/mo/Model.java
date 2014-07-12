package mo;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mo.MusicFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************************
* Class Model
*    Handles everything file/database/data related.
****************************************************/

public class Model {

  private static Model singleton = new Model();
  private ObservableList<MusicFile> listMF;
  private File selectedDirectory;
  private Logger logger = LoggerFactory.getLogger(Model.class);
    
  private Model() {
    
     
    listMF = FXCollections.observableArrayList();
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
  
  public void setPath(File directory) {
      this.selectedDirectory = directory;
  }
  
  public File getPath() {
      return selectedDirectory;
  }
}