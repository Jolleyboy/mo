/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import mo.MusicFile;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Joshua
 */
public class musicFileTest {
    MusicFile mf;
    public musicFileTest() {
        mf = new MusicFile("music\\seattle.mp3");
    }
    
    @Test
    public void musicFileNameTest() {
        mf.setArtist("test");
        Assert.assertEquals(mf.getArtist(), "test");
        mf.setArtist("test2");
        Assert.assertEquals(mf.getArtist(),"test2");
    }
    
    @Test
    public void musicFileArtistTest() {
        mf.setArtist("test");
        Assert.assertEquals(mf.getArtist(), "test");
    }
    
    @Test
    public void musicFileAlbumTest() {
        mf.setAlbum("test");
        Assert.assertEquals(mf.getAlbum(), "test");
    }
    
    @Test
    public void musicFileDurationTest() {
        mf.setDuration("3:21");
        Assert.assertEquals(mf.getDuration(), "3:21");
    }
    
    @Test
    public void musicFileGenreTest() {
        mf.setGenre("test");
        Assert.assertEquals(mf.getGenre(), "test");
    }
}
