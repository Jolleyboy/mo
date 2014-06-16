/***************************************************
* Class View
*    Handles everything display related
*    You give this to your console ascii designer
*        and he can go to town and do whatever he likes
*        as long as he doesn't change the function names
*        and parameters
****************************************************/

public class View {

  private static View singleton = new View();

  private View() {
    //to prevent making seperate instances
  }

  public static View getInstance() {
    return singleton;
  }

}