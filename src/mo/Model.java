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

}