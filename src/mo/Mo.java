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
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
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
 
        // --- File Menu
        Menu menuFile = new Menu("File");
        
        MenuItem open = new MenuItem("Music Scan"); // -- Music Scan Submenu
        final FileChooser fileChooser = new FileChooser();
        open.setOnAction((ActionEvent t) -> {
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            final File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                System.out.println(selectedDirectory);
            }
	});

        MenuItem exit = new MenuItem("Exit"); // -- Exit Submenu
        exit.setOnAction((ActionEvent t) -> {
            System.exit(0);
	});
        // ---

 	menuFile.getItems().addAll(open, exit);
 
        // --- Edit Menu
        Menu menuEdit = new Menu("Edit");
        MenuItem edits = new MenuItem("Edits"); // -- Edits Submenu
        menuEdit.getItems().addAll(edits);
        // ---
 
        // --- View Menu
        Menu menuView = new Menu("View");
        MenuItem changeview = new MenuItem("Change view"); // -- Change View Submenu

        menuView.getItems().addAll(changeview);
        // ---
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        table.setEditable(true);
        
        TableColumn nameCol = new TableColumn("Name");
        TableColumn artistCol = new TableColumn("Artist");
        TableColumn albumCol = new TableColumn("Album");
        TableColumn genreCol = new TableColumn("Genre");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn fileType = new TableColumn("File Type");
        
        table.getColumns().addAll(nameCol, timeCol, artistCol, albumCol, genreCol, fileType);
        final VBox tbl = new VBox();
        tbl.setSpacing(5);
        tbl.setPadding(new Insets(10, 10, 20, 10));
        tbl.getChildren().addAll(table);
        
        final VBox btns = new VBox();
        final HBox btnbox = new HBox(20);
        VBox.setVgrow(btnbox, Priority.ALWAYS);
        btns.setSpacing(10);
        btns.setPadding(new Insets(10, 10, 10, 10));
        
        Button btn1 = new Button();
        btn1.setText("Music Scan");
        btnbox.getChildren().add(btn1);
        
        Button btn2 = new Button();
        btn2.setText("Apply Changes");
        btnbox.getChildren().add(btn2);
        
        Button btn3 = new Button();
        btn3.setText("TEST");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Float[] values = new Float[] {-1.0f};
                final Label [] labels = new Label[values.length];
                final ProgressBar[] pbs = new ProgressBar[values.length];
                final ProgressIndicator[] pins = new ProgressIndicator[values.length];
                final HBox hbs [] = new HBox [values.length];
                
                Stage stage2 = new Stage();
                stage2.setTitle("Music Scan");
                Scene scene2 = new Scene(new VBox(), 550, 250);
                stage2.setScene(scene2);
                stage2.show(); 
                
                for (int i = 0; i < values.length; i++) {
                    final Label label = labels[i] = new Label();
                    label.setText("Scanning  ");
 
                    final ProgressBar pb = pbs[i] = new ProgressBar();
                    pb.setProgress(values[i]);
 
                    final ProgressIndicator pin = pins[i] = new ProgressIndicator();
                    pin.setProgress(values[i]);
                    final HBox hb = hbs[i] = new HBox();
                    hb.setSpacing(5);
                    hb.setPadding(new Insets(60, 10, 10, 10));
                    hb.setAlignment(Pos.CENTER);
                    hb.getChildren().addAll(label, pb, pin);
                }
                
                final VBox vb = new VBox();
                vb.setSpacing(5);
                vb.getChildren().addAll(hbs);
                scene2.setRoot(vb);
                stage2.show();
            }
        });
        btnbox.getChildren().add(btn3);
        
        btns.getChildren().addAll(btnbox);
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        ((VBox) scene.getRoot()).getChildren().addAll(tbl);
        ((VBox) scene.getRoot()).getChildren().addAll(btns);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
