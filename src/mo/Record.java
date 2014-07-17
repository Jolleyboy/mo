/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mo;

import java.util.ArrayList;

/**
 * Class for holding records received from web service
 * @author Jolley
 */
public class Record {
    public ArrayList<String> titles;
    public ArrayList<String> artists;
    public ArrayList<String> albums;
    
    //this is more of a struct than a class, so we left these public
    public Record() {
        this.titles = new ArrayList<String>();
        this.artists = new ArrayList<String>();
        this.albums = new ArrayList<String>();
    }
}