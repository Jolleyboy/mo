/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tests;

import mo.MusicFile;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Joshua
 */
public class musicFileTest {
    
    MusicFile mf = new MusicFile();
    public musicFileTest() {
    
    }
    
    @Test
    public void musicFileNameTest() {
        mf.setName("test");
        Assert.assertEquals(mf.getName(), "test");
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
