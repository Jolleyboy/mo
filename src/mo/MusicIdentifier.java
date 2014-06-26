/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.tag.Tag;

/**
 *
 * @author Joshua
 */
public class MusicIdentifier {

    Model model = Model.getInstance();

    public void identifyNewSongs() {

    }
<<<<<<< HEAD

    public String fingerprint(MusicFile mf) {
        String line = "";
        String fingerprint = "";
        String[] info;
        String[] temp;
        ArrayList<String> theInfo = new ArrayList<String>();
        String fpcalc = "C:\\Users\\Joshua\\Documents\\NetBeansProjects\\mo\\src\\musicIdentifier\\fpcalc.exe";
            
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ProcessBuilder(fpcalc,mf.getPath()).start().getInputStream()))){
            while ((line = br.readLine()) != null) {
                fingerprint += line + "\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(MusicIdentifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        info = fingerprint.split("\n");
        temp = info[1].split("=");
        theInfo.add(temp[1]);
        temp = info[2].split("=");
        theInfo.add(temp[1]);
        fingerprint = theInfo.get(0) + " " + theInfo.get(1);
        return fingerprint;
    }

    private void identify() {
    }
}
