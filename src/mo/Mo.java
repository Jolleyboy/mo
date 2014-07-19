package mo;

import java.io.File;
import javafx.application.Application;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Mo extends Application {
    
    private final TableView<MusicFile> table;
    private final Model model = Model.getInstance();
    private ObservableList<MusicFile> data = model.getList();
    private MusicCollector mc;
    private MusicIdentifier mi;
    private Logger logger = LoggerFactory.getLogger(Mo.class);

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
                        System.out.println("ERROR: Null Pointer Exception");
                    }
                    updateTable();
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
        MenuItem identify = new MenuItem("Identify Music"); // -- 
        identify.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    id();
                    updateTable();
                }
            
        });
        
        MenuItem rd = new MenuItem("Resolve Duplicates"); // -- 
        rd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resolveDuplicates();
                    updateTable();
                }
            
        });
        
        menuEdit.getItems().addAll(identify, rd);
        // ---
 
        // --- View Menu
        Menu menuView = new Menu("View");
        MenuItem update = new MenuItem("Update Library");
        update.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateTable();
                }
            
        });
        
        MenuItem reset = new MenuItem("Erase Library");
        reset.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resetTable();
                }
            
        });
        
        menuView.getItems().addAll(update, reset);
        // ---
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        return menuBar;
    };
    
    public void initTable(){
        // NAME COLUMN
        TableColumn nameCol = new TableColumn("Name");
        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.267));
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
        albumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
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
        timeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        
        table.getColumns().addAll(nameCol, timeCol, artistCol, albumCol, genreCol);
    };
    
    public void resolveDuplicates(){
        for (MusicFile mf : data) {
            System.out.println("parsing through list");
            if (mf.fDuplicates){
                System.out.println("duplicates found for: " 
                        + mf.getTitle() + " - "
                        + mf.getArtist() + " - " 
                        + mf.getAlbum());
                
                Stage stage3 = new Stage();
                stage3.setTitle("Resolve Duplicate Matches");
                Scene scene3 = new Scene(new VBox(), 650, 275);
                stage3.setScene(scene3);
                stage3.show();
                
                final VBox vb1 = new VBox(10);
                final HBox hb1 = new HBox(10);
                final HBox hb2 = new HBox(10);
                final HBox hb3 = new HBox(10);
                
                Label info = new Label();
                info.setText("Original Info:          "
                        + "" + mf.getTitle() + " - "
                        + mf.getArtist() + " - " 
                        + mf.getAlbum());
                ComboBox tBox = new ComboBox(FXCollections.observableList(mf.record.titles));
                ComboBox aBox = new ComboBox(FXCollections.observableList(mf.record.artists));
                ComboBox alBox = new ComboBox(FXCollections.observableList(mf.record.albums));
                tBox.prefWidth(90);
                aBox.prefWidth(90);
                alBox.prefWidth(90);
                Button apply = new Button();
                apply.setText("Apply Changes");
                apply.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(tBox.getValue().toString());
                        mf.setTitle(tBox.getValue().toString());
                        mf.setArtist(aBox.getValue().toString());
                        mf.setAlbum(alBox.getValue().toString());
                        stage3.close();
                        updateTable();
                    }
                });
                apply.setAlignment(Pos.CENTER);
                
                VBox VtBox = new VBox(10);
                VBox VaBox = new VBox(10);
                VBox ValBox = new VBox(10);
                
                Label LtBox = new Label("Title");
                Label LaBox = new Label("Artist");
                Label LalBox = new Label("Album");
                
                VtBox.getChildren().addAll(LtBox,tBox);
                VaBox.getChildren().addAll(LaBox,aBox);
                ValBox.getChildren().addAll(LalBox,alBox);
                
                hb1.setAlignment(Pos.CENTER);
                hb1.getChildren().addAll(VtBox, VaBox, ValBox);
                hb2.setAlignment(Pos.CENTER);
                hb2.getChildren().addAll(info);
                hb3.setAlignment(Pos.CENTER);
                hb3.getChildren().addAll(apply);
                vb1.setPadding(new Insets(10, 10, 10, 10));
                hb2.setPadding(new Insets(0,  10, 50, 10));
                hb3.setPadding(new Insets(50, 10, 10, 10));
                vb1.getChildren().addAll(hb2, hb1, hb3);
                
                scene3.setRoot(vb1);
                scene3.getStylesheets().add(Mo.class.getResource("Style/Mo.css").toExternalForm());
                stage3.show();
            } 
        }
    }
    
    public void id(){
        Label label = new Label();
        Task task;
        task = new Task<Void>() {
            @Override public Void call() {
                data = model.getList();
                mi.identifyNewSongs();
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
        Scene scene2 = new Scene(new VBox(), 550, 200);
                
        stage2.setScene(scene2);
        stage2.show();  
         
        final VBox vb = new VBox();
        final HBox btnbox2 = new HBox(20);
        
        label.setText("Identifying " + data.size() + " files... ");
        vb.setSpacing(10);
        vb.setPadding(new Insets(40, 10, 10, 10));
        
        Button btn4 = new Button();
        btn4.setText("Close");
        btn4.setOnAction((ActionEvent t) -> {    
            stage2.close();
        });
                
        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(bar);
        btnbox2.getChildren().add(btn4);
        btnbox2.setAlignment(Pos.CENTER);
        btnbox2.setPadding(new Insets(10, 10, 10, 10));
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(label, hb, pi, btnbox2);
        scene2.setRoot(vb);
        scene2.getStylesheets().add(Mo.class.getResource("Style/Mo.css").toExternalForm());
        stage2.show();  
    };
    
    public void saveFiles() {
        Stage stage4 = new Stage();
        stage4.setTitle("Save Files");
        Scene scene4 = new Scene(new VBox(), 650, 625);
        stage4.setScene(scene4);
        stage4.show();
        VBox vb = new VBox(10);
        VBox vb1 = new VBox(10);
        HBox hb1 = new HBox();
        HBox hb = new HBox();
        HBox hbB = new HBox();
        Label lbl = new Label();
        Label lbl1 = new Label();
        TextField lbl2 = new TextField();
        Separator sep1 = new Separator();
        lbl2.setPrefWidth(350);
        lbl1.setText("Save files to: ");
        lbl.setText("Save Location");

        Button btn1 = new Button();
        btn1.setText("Select Directory");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage4);
                if (selectedDirectory != null) {
                    try {
                        lbl2.setText(selectedDirectory.toString());
                    } catch (NullPointerException npe) {
                        System.out.println("ERROR: Null Pointer Exception");
                    }
                }
            }
        });

        hb.getChildren().addAll(lbl);
        hb.setAlignment(Pos.CENTER);
        hbB.getChildren().addAll(btn1);
        hbB.setAlignment(Pos.CENTER_RIGHT);
        hbB.setPadding(new Insets(10, 10, 10, 40));
        hb1.getChildren().addAll(lbl1, lbl2, hbB);
        hb1.setAlignment(Pos.CENTER_LEFT);
        vb1.setPadding(new Insets(10, 10, 10, 10));
        vb1.getChildren().addAll(hb, hb1, sep1);

        VBox vb2 = new VBox(10);
        HBox hb2 = new HBox();
        hb2.setAlignment(Pos.CENTER);
        Label lbl3 = new Label();
        hb2.getChildren().addAll(lbl3);
        lbl3.setText("File Name Structure");
        HBox selectName = new HBox();

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("One");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("Two");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("Three");
        rb3.setToggleGroup(group);
        
        rb1.setUserData(1);
        rb2.setUserData(2);
        rb3.setUserData(3);

        ComboBox first = new ComboBox(FXCollections.observableArrayList("", "Title", "Artist", "Album"));
        ComboBox second = new ComboBox(FXCollections.observableArrayList("", "Title", "Artist", "Album"));
        ComboBox third = new ComboBox(FXCollections.observableArrayList("", "Title", "Artist", "Album"));

        first.getSelectionModel().select(1);
        second.setValue("");
        third.setValue("");
        Label dash = new Label();
        dash.setText(" - ");
        Label dash2 = new Label();
        dash2.setText(" - ");
        Label ext = new Label();
        ext.setText(" .mp3 ");
        Separator sep2 = new Separator();

        VBox choice = new VBox(10);
        VBox warn = new VBox(10);
        HBox choiceh = new HBox(20);
        HBox rSel = new HBox();
        HBox lSel = new HBox();
        Label wait = new Label();
        wait.setText("");
        wait.setStyle("-fx-text-fill: #66ccff;");
        Label att = new Label();
        att.setText(" Preferred Attributes ");
        rb3.setPadding(new Insets(0, 0, 10, 0));
        att.setPadding(new Insets(10, 0, 0, 0));
        choice.getChildren().addAll(att, rb1, rb2, rb3);
        selectName.getChildren().addAll(first, ext, sep2);
        choice.setPadding(new Insets(0, 80, 0, 10));

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    String value = group.getSelectedToggle().getUserData().toString();
                    selectName.getChildren().clear();
                    if (null != value) {
                        switch (value) {
                            case "1":
                                first.getSelectionModel().select(1);
                                second.setValue("");
                                second.getSelectionModel().selectFirst();
                                third.setValue("");
                                third.getSelectionModel().selectFirst();
                                selectName.getChildren().addAll(first, ext);
                                break;
                            case "2":
                                second.getSelectionModel().select(2);
                                third.setValue("");
                                third.getSelectionModel().selectFirst();
                                selectName.getChildren().addAll(first, dash, second, ext);
                                break;
                            case "3":
                                second.getSelectionModel().select(2);
                                third.getSelectionModel().select(3);
                                selectName.getChildren().addAll(first, dash, second, dash2, third, ext);
                                break;
                        }
                    }
                }
            }
        });
        rb3.setSelected(true);
        //warn.getChildren().addAll(wait);
        warn.setAlignment(Pos.CENTER);
        lSel.getChildren().addAll(choice);
        rSel.getChildren().addAll(selectName);
        selectName.setAlignment(Pos.CENTER);
        lSel.setAlignment(Pos.CENTER_LEFT);
        lSel.setId("lSel");
        rSel.setAlignment(Pos.CENTER);
        rSel.setPrefWidth(350);
        vb2.setPadding(new Insets(0, 10, 0, 10));
        choiceh.setPadding(new Insets(0, 10, 20, 10));
        choiceh.getChildren().addAll(lSel, rSel);
        vb2.getChildren().addAll(hb2, choiceh, warn, sep2);
        //-------------end of vb2---------------------//

        Image rootIcon = new Image(
            getClass().getResourceAsStream("Style/icon4.png"));
        Image subIcon = new Image(
            getClass().getResourceAsStream("Style/icon4.png"));
        Image fileIcon = new Image(
            getClass().getResourceAsStream("Style/icon6.png"));
        
        VBox vb3 = new VBox(10);
        VBox headvb3 = new VBox();
        Label head = new Label(" Music Folder Layout");
        
        TreeItem<String> rootItem1;
        TreeItem<String> rootItem2;
        TreeItem<String> rootItem3;
        
        rootItem1 = new TreeItem<>("Artist", new ImageView(rootIcon));
        rootItem2 = new TreeItem<>("Artist", new ImageView(rootIcon));
        rootItem3 = new TreeItem<>("Songs", new ImageView(rootIcon));
        
        rootItem1.setExpanded(true);
        rootItem2.setExpanded(true);
        
        TreeItem<String> subRoot1;
        TreeItem<String> subRoot2;
        subRoot1 = new TreeItem<>("Album1", new ImageView(subIcon));
        subRoot2 = new TreeItem<>("Album2", new ImageView(subIcon));
        subRoot2.setExpanded(true);
        rootItem1.getChildren().addAll(subRoot1, subRoot2);
        rootItem2.getChildren().addAll();
        TreeItem<String> item1;
        TreeItem<String> item2;
        TreeItem<String> item3;
        TreeItem<String> item4;
        TreeItem<String> item11;
        TreeItem<String> item22;
        TreeItem<String> item33;
        TreeItem<String> item44;
        TreeItem<String> item111;
        TreeItem<String> item222;
        TreeItem<String> item333;
        TreeItem<String> item444;
        item1 = new TreeItem<>("MusicFile1.mp3", new ImageView(fileIcon));
        item2 = new TreeItem<>("MusicFile2.mp3", new ImageView(fileIcon));
        item3 = new TreeItem<>("MusicFile1.mp3", new ImageView(fileIcon));
        item4 = new TreeItem<>("MusicFile2.mp3", new ImageView(fileIcon));
        item11 = new TreeItem<>("MusicFile1.mp3", new ImageView(fileIcon));
        item22 = new TreeItem<>("MusicFile2.mp3", new ImageView(fileIcon));
        item33 = new TreeItem<>("MusicFile3.mp3", new ImageView(fileIcon));
        item44 = new TreeItem<>("MusicFile4.mp3", new ImageView(fileIcon));
        item111 = new TreeItem<>("MusicFile1.mp3", new ImageView(fileIcon));
        item222 = new TreeItem<>("MusicFile2.mp3", new ImageView(fileIcon));
        item333 = new TreeItem<>("MusicFile3.mp3", new ImageView(fileIcon));
        item444 = new TreeItem<>("etc...", new ImageView(fileIcon));
        subRoot1.getChildren().addAll(item1, item2);
        subRoot2.getChildren().addAll(item3, item4);
        rootItem2.getChildren().addAll(item11, item22, item33, item44);
        rootItem3.getChildren().addAll(item111, item222, item333, item444);
        TreeView<String> tree1 = new TreeView<>(rootItem1);
        TreeView<String> tree2 = new TreeView<>(rootItem2);
        TreeView<String> tree3 = new TreeView<>(rootItem3);
        
        Button saveAll = new Button();
        saveAll.setText(" Save And Create Folders ");
        saveAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("--------------------------");
                System.out.println(first.getValue() + " "
                        + second.getValue() + " "
                        + third.getValue() + ".mp3");
            }
        });
        
        Button cancel = new Button();
        cancel.setText("Cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage4.close();
            }
        });
        
        
        HBox op1 = new HBox(10);
        HBox op2 = new HBox(10);
        HBox op3 = new HBox(10);
        HBox ops = new HBox(30);
        
        VBox lb1 = new VBox(5);
        Label one = new Label("Artist -> Album -> Files");
        VBox lb2 = new VBox(5);
        Label two = new Label("Artist -> Files");
        VBox lb3 = new VBox(5);
        Label three = new Label("Single Folder of Files");
        
        lb1.getChildren().addAll(one,tree1);
        op1.getChildren().addAll(lb1);
        op1.setAlignment(Pos.CENTER);
        
        lb2.getChildren().addAll(two, tree2);
        op2.getChildren().addAll(lb2);
        op2.setAlignment(Pos.CENTER);
        
        lb3.getChildren().addAll(three, tree3);
        op3.getChildren().addAll(lb3);
        op3.setAlignment(Pos.CENTER);
        ComboBox fold = new ComboBox(FXCollections.observableArrayList(
                "", "Artist -> Album -> Files", 
                "Artist -> Files", 
                "Single Folder of Files"));
        ops.getChildren().addAll(op1, op2, op3);
        ops.setPadding(new Insets(10, 10, 10, 10));
        headvb3.getChildren().addAll(head);
        headvb3.setAlignment(Pos.CENTER);
        vb3.setAlignment(Pos.BOTTOM_RIGHT);
        Separator sep4 = new Separator();
        HBox bns = new HBox(345);
        bns.getChildren().addAll(cancel, saveAll);
        vb3.getChildren().addAll(headvb3, ops, fold, sep4, bns);
        vb3.setPadding(new Insets(10, 10, 10, 10));
        vb.getChildren().addAll(vb1, vb2, vb3);
        scene4.setRoot(vb);
        scene4.getStylesheets().add(Mo.class.getResource("Style/Mo.css").toExternalForm());
        stage4.show();
    }
    
    public void updateTable() {
        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(data));
    }
    
    public void resetTable() {
        //table.getItems().clear();
    }
    
    @Override
    public void start(Stage stage) {
        
    	stage.setTitle("Music Organizer v2.0");
        stage.setWidth(800);
        stage.setHeight(650);
        
        Scene scene = new Scene(new VBox());
        
        table.setEditable(true);
        table.prefHeight(300);
        
        MenuBar menuBar = initMenus(stage); //INITIALIZE MENUS
        
        initTable(); //INITIALIZE TABLE
        updateTable();
        
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setPadding(new Insets(10, 10, 0, 10));
        table.setPlaceholder(new Text(""));
        final VBox btns = new VBox();
        final HBox btnbox = new HBox(20);
        btns.setSpacing(0);
        btns.setPadding(new Insets(10, 10, 10, 10));
        
        Button btn1 = new Button();
        btn1.setText("Add Folder to Library");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);
                if (selectedDirectory != null) {
                    System.out.println("Scanning " + selectedDirectory.getPath());
                    try {
                        mc.searchComp(selectedDirectory.getPath() + "\\");
                    } catch (NullPointerException npe) {
                        System.out.println("ERROR: Null Pointer Exception");
                    }
                    updateTable();
                }
            }
        });
        
        Button btn3 = new Button();
        btn3.setText("Identify Music Files");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id();
                updateTable();
            }
        });
        
        Button btn2 = new Button();
        btn2.setText("Resolve Duplicates");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                resolveDuplicates();
                updateTable();
            }
        });
        
        Button btn4 = new Button();
        btn4.setText("Save to Folders");
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                saveFiles();
            }
        });
        
        final HBox btnboxR = new HBox(20);
        btnboxR.getChildren().add(btn4);
        btnboxR.setAlignment(Pos.CENTER_RIGHT);
        btn4.setAlignment(Pos.CENTER_RIGHT);
        btnbox.setAlignment(Pos.CENTER_LEFT);
        
        btnbox.getChildren().add(btn1);
        btnbox.getChildren().add(btn3);
        btnbox.getChildren().add(btn2);
        btns.getChildren().add(btnbox);
        btns.getChildren().add(btnboxR);
        
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, table, btns);
        
        Image applicationIcon;
        applicationIcon = new Image(getClass().getResourceAsStream("Style/icon3.png"));
        stage.getIcons().add(applicationIcon);
        
        stage.setScene(scene);
        scene.getStylesheets().add(Mo.class.getResource("Style/Mo.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
