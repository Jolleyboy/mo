package mo;

/***************************************************
* Class Controller
*    Handles User input
*    Is the glue between the Model and the View
*    Program Logic goes here!
****************************************************/

/**
* Class Controller
****************************************************/
public class Controller implements Runnable {

  Model model = Model.getInstance();
  
  /**
  * getFilename -- returns a filename from the user
  **************************************************/
  

  @Override
  /**
  * run -- runs the program
  **************************************************/
  public void run() {

  }

  /**
  * main -- le driver programe.  Avec Vite!
  **************************************************/
  public static void main(String[] args) {
    Controller controller = new Controller();
    controller.run();
  }
}

