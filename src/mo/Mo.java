/*
User Interface
 */

package mo;

import java.awt.Desktop;
import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matt
 */
public class Mo extends Application {
    
//<<<<<<< HEAD
    private TableView<ExMusic> table = new TableView<ExMusic>();
    
    private ObservableList<ExMusic> data =
        FXCollections.observableArrayList(
                new ExMusic("name1","artist1","album1","genre1","time1"),
                new ExMusic("name2","artist2","album2","genre2","time2"),
                new ExMusic("name3","artist3","album3","genre3","time3")
        );
//>>>>>>> origin/master

    @Override
    public void start(Stage stage) {

    	stage.setTitle("Music Organizer v1.1");
        Scene scene = new Scene(new VBox(), 700, 550);
         
        table.setEditable(true);
        
        MenuBar menuBar = new MenuBar();
 
        // --- File Menu
        Menu menuFile = new Menu("File");
        
        MenuItem open = new MenuItem("Music Scan"); // -- Music Scan Submenu
        open.setOnAction((ActionEvent t) -> {
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
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
        
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<ExMusic, String>("name"));
        TableColumn artistCol = new TableColumn("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<ExMusic, String>("artist"));
        TableColumn albumCol = new TableColumn("Album");
        albumCol.setCellValueFactory(new PropertyValueFactory<ExMusic, String>("album"));
        TableColumn genreCol = new TableColumn("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<ExMusic, String>("genre"));
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<ExMusic, String>("time"));
        
        table.setItems(data);
        table.getColumns().addAll(nameCol, timeCol, artistCol, albumCol, genreCol);
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
        btn1.setText("Null");
        btnbox.getChildren().add(btn1);
        
        Button btn2 = new Button();
        btn2.setText("Load Examples");
        btn2.setOnAction((ActionEvent t) -> {
            
	});
        
        btnbox.getChildren().add(btn2);
        
        Button btn3 = new Button();
        btn3.setText("Music Scan");
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
                
                Button btn4 = new Button();
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
                final HBox btnbox2 = new HBox(20);
                vb.setSpacing(5);
                btn4.setText("Cancel");
                btn4.setOnAction((ActionEvent t) -> {
                    
                });
                
            vb.getChildren().addAll(hbs);
            btnbox2.getChildren().add(btn4);
            btnbox2.setAlignment(Pos.CENTER);
            btnbox2.setPadding(new Insets(60, 10, 10, 10));
            vb.getChildren().addAll(btnbox2);
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
    
    public static class ExMusic {
 
        private String name;
        private String artist;
        private String album;
        private String genre;
        private String time;
 
        private ExMusic(String name, String artist, String album, String genre, String time) {
            this.name = new String(name);
            this.artist = new String(artist);
            this.album = new String(album);
            this.genre = new String(genre);
            this.time = new String(time);
        }
        //---------
        public String getName() {
            return name;
        }
        public void setName(String name1) {
            this.name = name1;
        }
        //---------
        public String getArtist() {
            return artist;
        }
        public void setArtist(String artist1) {
            this.artist = artist1;
        }
        //---------
        public String getAlbum() {
            return album;
        }
        public void setAlbum(String album1) {
            this.album = album1;
        }
        //---------
        public String getGenre() {
            return genre;
        }
        public void setGenre(String genre1) {
            this.genre = genre1;
        }
        //---------
        public String getTime() {
            return time;
        }
        public void setTime(String time1) {
            this.time = time1;
        }
        //---------
    }
}
