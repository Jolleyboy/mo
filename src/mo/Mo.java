/*
User Interface
 */

package mo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matt
 */
public class Mo extends Application {
    
    private final TableView table;
    private final Desktop desktop = Desktop.getDesktop();

    public Mo() {
        this.table = new TableView();
    }

	@Override
    public void start(Stage stage) {

    	stage.setTitle("Music Organizer v1.0");
        Scene scene = new Scene(new VBox(), 700, 550);
         
        MenuBar menuBar = new MenuBar();
 
        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem open = new MenuItem("Music Scan");
        final FileChooser fileChooser = new FileChooser();
        open.setOnAction((ActionEvent t) -> {
            final DirectoryChooser directoryChooser =
                new DirectoryChooser();
            final File selectedDirectory =
                    directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                System.out.println(selectedDirectory);
            }
		});

 		menuFile.getItems().addAll(open);
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent t) -> {
    		System.exit(0);
		});

 		menuFile.getItems().addAll(exit);
 
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
 
        // --- Menu View
        Menu menuView = new Menu("View");
        MenuItem changeview = new MenuItem("Change view");

        menuView.getItems().addAll(changeview);
 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        table.setEditable(true);
        
        TableColumn nameCol = new TableColumn("Name");
        TableColumn artistCol = new TableColumn("Artist");
        TableColumn albumCol = new TableColumn("Album");
        TableColumn genreCol = new TableColumn("Genre");
        TableColumn timeCol = new TableColumn("Time");
        
        table.getColumns().addAll(nameCol, artistCol, albumCol, genreCol, timeCol);
        final VBox tbl = new VBox();
        tbl.setSpacing(5);
        tbl.setPadding(new Insets(10, 10, 20, 10));
        tbl.getChildren().addAll(table);
        
        final VBox btns = new VBox();
        HBox btnbox = new HBox(20);
        VBox.setVgrow(btnbox, Priority.ALWAYS);
        btns.setSpacing(10);
        btns.setPadding(new Insets(10, 10, 10, 10));
        Button btn1 = new Button();
        
        btn1.setText("Button 1");
        btnbox.getChildren().add(btn1);
        
        Button btn2 = new Button();
        btn2.setText("Button 2");
        btnbox.getChildren().add(btn2);
        
        btns.getChildren().addAll(btnbox);
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().addAll(tbl);
        ((VBox) scene.getRoot()).getChildren().addAll(btns);
        
        stage.setScene(scene);
        stage.show();
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                Mo.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
