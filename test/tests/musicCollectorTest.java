/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mo.Model;
import mo.MusicCollector;
import mo.MusicFile;
import org.testng.Assert;
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
        ObservableList<MusicFile> ol = FXCollections.observableArrayList();
        model.setList(ol);
        mc.searchComp(path);
        ol = model.getList();
        boolean fEquals = false;
        
        //for debugging.  Shows what files are in the list
        int numFiles = 0;
        for (MusicFile file : ol) {
            System.out.println("File number: " + ++numFiles);
            System.out.println("Music File path: " + file.getPath());
            System.out.println("Music File title: " + file.getTitle() + "\n");    
        }
        ///////////////////////////////////////////////////////////////////////
        
        //Does it have seattle.mp3?
        for (MusicFile file : ol) {
            if (mf.equals(file)) {
               fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        
        //Does it have crazy.mp3?
        fEquals = false;
        for (MusicFile file : ol) {
            mf = new MusicFile("music\\crazy.mp3");
            if (mf.equals(file)) {
               fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        
        //Does it have drivemycar.mp3?
        fEquals = false;
        for (MusicFile file : ol) {
            mf = new MusicFile("music\\otherMusic\\drivemycar.mp3");
            if (mf.equals(file)) {
               fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        Assert.assertEquals(numFiles,3);
    }
    
    //once searchComp passes this test we'll be sure it's working
    @Test
    public void fullTest() {
        //create an empty list and set it to the model
        ObservableList<MusicFile> ol = FXCollections.observableArrayList();
        model.setList(ol);
        
        //run our test method
        mc.findAndAddFiles(new File("music\\"));
        
        //get the newly filled list
        ol = model.getList();
        boolean fEquals = false;
        
        //for debugging.  Shows what files are in the list
        int numFiles = 0;
        for (MusicFile file : ol) {
            System.out.println("File number: " + ++numFiles);
            System.out.println("Music File path: " + file.getPath());
            System.out.println("Music File title: " + file.getTitle() + "\n");    
        }
        ////////////////////////////////////////////////////////////
        
        MusicFile mf = new MusicFile("music\\seattle.mp3");
        for (MusicFile file : ol) {        
            if (mf.equals(file)) {
            fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        
        fEquals = false;      
        mf = new MusicFile("music\\crazy.mp3");
        for (MusicFile file : ol) {
            if (mf.equals(file)) {
               fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        
        fEquals = false;
        mf = new MusicFile("music\\otherMusic\\drivemycar.mp3");
        for (MusicFile file : ol) {
            if (mf.equals(file)) {
               fEquals = true; 
            }   
        }
        Assert.assertEquals(fEquals, true);
        Assert.assertEquals(numFiles,3);
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
