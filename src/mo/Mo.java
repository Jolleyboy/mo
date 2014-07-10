package mo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Mo extends Application {
    
    private final TableView<MusicFile> table;
    private final Model model = Model.getInstance();
    private MusicCollector mc;
    private MusicIdentifier mi;
    private Logger logger = LoggerFactory.getLogger(Mo.class);

    private ObservableList<MusicFile> data = model.getList();
    
    public Mo() { 
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        this.table = new TableView<>();
        mc = new MusicCollector();
        mi = new MusicIdentifier();
    }
    
    public MenuBar initMenus(Stage stage){
        MenuBar menuBar = new MenuBar();
        
        // --- File Menu
        Menu menuFile = new Menu("File");
        
        MenuItem open = new MenuItem("Add Folder to Library"); // -- Search Submenu
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);
                if (selectedDirectory != null) {
                    System.out.println("Scanning " + selectedDirectory.getPath());
                    try {
                        mc.searchComp(selectedDirectory.getPath() + "\\");
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                        System.out.println("ERROR: Null Pointer Exception");
                    }
                }
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
        MenuItem identify = new MenuItem("Identify Music"); // -- Edits Submenu
        identify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    id();
                }
            
        });
        
        menuEdit.getItems().addAll(identify);
        // ---
 
        // --- View Menu
        Menu menuView = new Menu("View");
        MenuItem changeview = new MenuItem("Change view"); // -- Change View Submenu

        menuView.getItems().addAll(changeview);
        // ---
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        return menuBar;
    };
    
    public void initTable(){
        // NAME COLUMN
        TableColumn nameCol = new TableColumn("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.275));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<MusicFile, String>>() {
                @Override
                public void handle(CellEditEvent<MusicFile, String> t) {
                    ((MusicFile) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setTitle(t.getNewValue());
                }
            }
        );
        
       //ARTIST COLUMN
        TableColumn artistCol = new TableColumn("Artist");
        artistCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));

        artistCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistCol.setOnEditCommit(
            new EventHandler<CellEditEvent<MusicFile, String>>() {
                @Override
                public void handle(CellEditEvent<MusicFile, String> t) {
                    ((MusicFile) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setArtist(t.getNewValue());
                }
            }
        );
        
        //ALBUM COLUMN
        TableColumn albumCol = new TableColumn("Album");
        albumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.275));
        albumCol.setCellValueFactory(new PropertyValueFactory<>("album"));

        albumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        albumCol.setOnEditCommit(
            new EventHandler<CellEditEvent<MusicFile, String>>() {
                @Override
                public void handle(CellEditEvent<MusicFile, String> t) {
                    ((MusicFile) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setAlbum(t.getNewValue());
                }
            }
        );
        
        //GENRE COLUMN
        TableColumn genreCol = new TableColumn("Genre");
        genreCol.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        genreCol.setCellFactory(TextFieldTableCell.forTableColumn());
        genreCol.setOnEditCommit(
            new EventHandler<CellEditEvent<MusicFile, String>>() {
                @Override
                public void handle(CellEditEvent<MusicFile, String> t) {
                    ((MusicFile) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setGenre(t.getNewValue());
                }
            }
        );
        
        //TIME COLUMN
        TableColumn timeCol = new TableColumn("Time");
        timeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.075));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        
        table.getColumns().addAll(nameCol, timeCol, artistCol, albumCol, genreCol);
    };
    
    public void id(){
        Task task = new Task<Void>() {
            @Override public Void call() {
                mi.identifyNewSongs();
                data = model.getList(); 
                updateProgress(1,1);
                return null;
            }
        };
        
        ProgressBar bar = new ProgressBar();
        ProgressIndicator pi = new ProgressIndicator();
        bar.progressProperty().bind(task.progressProperty());
        pi.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
                
        Stage stage2 = new Stage();
        stage2.setTitle("Identify");
        Scene scene2 = new Scene(new VBox(), 550, 250);
                
        stage2.setScene(scene2);
        stage2.show();  
         
        final VBox vb = new VBox();
        final HBox btnbox2 = new HBox(20);
        Label label = new Label();
        label.setText("Identifying...");
        vb.setSpacing(10);
        vb.setPadding(new Insets(40, 10, 10, 10));
        
        Button btn4 = new Button();
        btn4.setText("Cancel");
        btn4.setOnAction((ActionEvent t) -> {    
            stage2.close();
        });
                
        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(label, bar, pi);
        btnbox2.getChildren().add(btn4);
        btnbox2.setAlignment(Pos.CENTER);
        btnbox2.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().addAll(hb, btnbox2);
        scene2.setRoot(vb);
        stage2.show();
                
        
    };
    
    @Override
    public void start(Stage stage) {
        
    	stage.setTitle("Music Organizer v1.3");
        stage.setWidth(700);
        stage.setHeight(550);
        
        Scene scene = new Scene(new VBox());
        
        table.setEditable(true);
        table.minHeight(800);
        
        MenuBar menuBar = initMenus(stage); //INITIALIZE MENUS
        
        initTable(); //INITIALIZE TABLE
        
        //data.add(new MusicFile("music\\seattle.mp3"));
        //data.add(new MusicFile("music\\crazy.mp3"));
        table.setItems(data);
        
        final VBox tbl = new VBox();
        VBox.setVgrow(tbl, Priority.ALWAYS);
        tbl.setSpacing(0);
        tbl.setPadding(new Insets(10, 10, 0, 10));
        tbl.getChildren().addAll(table);
        
        final VBox btns = new VBox();
        final HBox btnbox = new HBox(20);
        //VBox.setVgrow(btnbox, Priority.ALWAYS);
        btns.setSpacing(0);
        btns.setPadding(new Insets(10, 10, 10, 10));
        
        Button btn1 = new Button();
        btn1.setText("*");
        btnbox.getChildren().add(btn1);
        
        Button btn2 = new Button();
        btn2.setText("Example");
        btn2.setOnAction((ActionEvent t) -> {
            
	});
        
        btnbox.getChildren().add(btn2);
        
        Button btn3 = new Button();
        btn3.setText("Identify Music Files");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id();
            }
        });
        
        btnbox.getChildren().add(btn3);
        
        btns.getChildren().addAll(btnbox);
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, tbl, btns);
       
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
