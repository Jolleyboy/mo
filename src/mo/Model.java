package mo;

import java.io.File;
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
  private List<MusicFile> listMF;
  private File selectedDirectory;
  private Model() {
    //to prevent making seperate instances
  }

 
  public static Model getInstance() {
    return singleton;
  }
  
  public List<MusicFile> getList() {
      return listMF;
  }
  
  public void setList(List<MusicFile> listMF) {
      this.listMF = listMF;
  }
}