/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mo;

/**
 * Class for holding records received from web service
 * @author Jolley
 */
public class Record {
    private String title;
    private String artist;
    private String duration;
    private String album;
    
    public String getTitle() {
        return title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public String getAlbum() {
        return album;
    }
}