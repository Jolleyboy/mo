/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mo.Model;
import mo.MusicCollector;
import mo.MusicFile;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class musicCollectorTest {
    
    public musicCollectorTest() {
    }

    MusicCollector mc = new MusicCollector();
    String path = "music\\";
    Model model = Model.getInstance();
    
    @Test
    public void searchCompTest() {
        MusicFile mf = new MusicFile("music\\seattle.mp3");
        mc.searchComp(path);
        ObservableList<MusicFile> ol = model.getList();
        
        //for debugging.  Shows what files are in the list
        int numFiles = 0;
        for (MusicFile file : ol) {
            System.out.println("File number: " + ++numFiles);
            System.out.println("Music File path: " + mf.getPath());
            System.out.println("Music File title: " + mf.getTitle() + "\n");    
        }
        
        Assert.assertEquals(ol.contains(mf), true);
    }
    
    //once searchComp passes this test we'll be sure it's working
    @Test
    public void fullTest() {
        mc.searchComp("music\\");
        Assert.assertEquals(model.getList().contains(new MusicFile("music\\seattle.mp3")),true);
        Assert.assertEquals(model.getList().contains(new MusicFile("music\\crazy.mp3")),true);
        Assert.assertEquals(model.getList().contains(new MusicFile("music\\otherMusic\\drivemycar.mp3")),true);
    }
    
    //When you add it manually into a list and check, it works.
    @Test
    public void containsTest() {
        ObservableList<MusicFile> ol = FXCollections.observableArrayList();
        MusicFile mf = new MusicFile("music\\seattle.mp3");
        ol.add(mf);
        Assert.assertEquals(ol.contains(mf), true);
    }
    
    //When you add it to the model and check it, it works.  There must be
    //something wrong with the MusicCollector algorithms
    @Test
    public void modelTest() {
        ObservableList<MusicFile> ol = model.getList();
        MusicFile mf = new MusicFile("music\\seattle.mp3");
        ol.add(mf);
        model.setList(ol);
        Assert.assertEquals(model.getList().contains(mf), true);
    }
}
