/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mo;

/**
 *
 * @author Jolley
 */
public class MusicSaver {
    public void changeFilename(MusicFile mf, String firstAttribute, String secondAttribute, String thirdAttribute) {
        mf.setNewPath(firstAttribute + " - " + secondAttribute + " - " + thirdAttribute);
    }
    
    public void changeFilename(MusicFile mf, String firstAttribute, String secondAttribute) {
        mf.setNewPath(firstAttribute + " - " + secondAttribute);
    }
}
