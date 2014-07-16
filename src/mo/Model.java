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
    
  /**
   * Constructor for the Singleton
   */
  private Model() {
    listMF = FXCollections.observableArrayList();
    //to prevent making seperate instances
  }

 /**
  * Returns the singleton
  * @return 
  */
  public static Model getInstance() {
    return singleton;
  }
  
  /**
   * Returns the list in model
   * @return 
   */
  public ObservableList<MusicFile> getList() {
      return listMF;
  }
  
  /**
   * Sets the list in the Model
   * @param listMF 
   */
  public void setList(ObservableList<MusicFile> listMF) {
      this.listMF = listMF;
  }
  
  /**
   * This is used by MusicCollector
   * @param directory 
   */
  public void setPath(File directory) {
      this.selectedDirectory = directory;
  }
  
  /**
   * This is used by MusicCollector
   * @return 
   */
  public File getPath() {
      return selectedDirectory;
  }
}