/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Joshua
 */
public class MusicIdentifier {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(MusicIdentifier.class);
    
    Model model = Model.getInstance();
    Object obj;
    JSONParser parser = new JSONParser();
          
  
    public void identifyNewSongs() {
        ObservableList<MusicFile> ol = model.getList();
        ObservableList<MusicFile> newList = FXCollections.observableArrayList();
    
        for (MusicFile mf : ol) {
            if (mf.isIdentified()) {
                continue;
            }
            parseJSON(identify(fingerprint(mf)),mf);
            newList.add(mf);
            try {
                Thread.sleep(400); //Only 3 requests per second
            } catch (InterruptedException ex) {
                Logger.getLogger(MusicIdentifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        model.setList(newList);
    }
   
    /***   
     * Fingerprints the music file using fpcalc
     * @param mf
     * @return 
     */
    public String fingerprint(MusicFile mf) {
        String line = "";
        String fingerprint = "";
        String[] info;
        String[] temp;
        ArrayList<String> theInfo = new ArrayList<String>();
        String fpcalc = "lib\\fp\\fpcalc.exe";
            
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ProcessBuilder(fpcalc,mf.getPath()).start().getInputStream()))){
            while ((line = br.readLine()) != null) {
                fingerprint += line + "\n";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            fingerprint = "File not found";
            return fingerprint;
        }
        
        info = fingerprint.split("\n");
        temp = info[1].split("=");
        theInfo.add(temp[1]);
        temp = info[2].split("=");
        theInfo.add(temp[1]);
        fingerprint = theInfo.get(0) + " " + theInfo.get(1);
        return fingerprint;
    }

    /***
     * Identifies the music file based on the fingerprint returns a JSON obj
     * @param fingerprint
     * @return 
     */
    public Object identify(String fingerprint) {
        try {
            String info[] = fingerprint.split(" ");
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("api.acoustid.org")
                    .setPath("/v2/lookup")
                    .setParameter("client", "wDj2TMtS")
                    .setParameter("meta","recordings")
                    .setParameter("duration",info[0])
                    .setParameter("fingerprint", info[1])
                    .build();
//            
//            String url = uri.toString();
//            url = url.replace("%2B", "+");
            
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
                obj = parser.parse(EntityUtils.toString(entity1));
            EntityUtils.consume(entity1);
            response1.close();
        } catch (IOException | URISyntaxException | ParseException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return obj;
    }
    
    /***
     * Parses the JSON into music file info.  
     * @param obj
     * @param mf 
     */
    public void parseJSON(Object obj, MusicFile mf) {
        JSONObject jsob = (JSONObject)obj;
        System.out.println(jsob.toString());
        JSONArray results = (JSONArray)jsob.get("results");
        if (results != null) {
            //print out everything
            System.out.println(results.get(0));
    //        JSONObject result = (JSONObject)(results.get(0));
    //
    //        //get the recordings array
    //        JSONArray recordingsArray = (JSONArray) result.get("recordings");
    //        JSONObject recording = (JSONObject) recordingsArray.get(0);
    //        int duration = Integer.parseInt(recording.get("duration").toString());
    //
    //        //get the duration
    //        System.out.println(duration/60 + ":" + duration%60);
    //        mf.setDuration(duration/60 + ":" + duration%60);
    //
    //        //Get the title of the song
    //        System.out.println(recording.get("title"));
    //        mf.setTitle(recording.get("title").toString());
    //
    //        //get the artists
    //        JSONArray artistsArray = (JSONArray)recording.get("artists");
    //        JSONObject artist = (JSONObject) artistsArray.get(0);
    //
    //        System.out.println(artist.get("name"));
    //        mf.setArtist(artist.get("name").toString());

            //create a new Record to hold all the information we're about to get
            Record record = new Record();
            try {
                //Loop through every result catagory we got from the JSON
                for (Object anObj : results) {
                    JSONObject result = (JSONObject)anObj;
                    JSONArray recordingsArray = (JSONArray) result.get("recordings");
                    if (recordingsArray != null) {
                        //Loop through every recording catagory we got from the JSON
                        for (Object anObj2 : recordingsArray) {
                            JSONObject recording = (JSONObject) anObj2;
                            boolean fContains = false;
                            JSONArray artistArray = (JSONArray)recording.get("artists");

                            //loop through every artist catagory we got from the JSON
                            for (Object anObj3 : artistArray) {
                                JSONObject artist = (JSONObject) anObj3;

                                //check for duplicates, add if there are none.
                                for (String str : record.artists) {
                                    if (record.artists.size() > 0) {
                                        if (str.trim().contains(artist.get("name").toString())){
                                            fContains = true;
                                        }
                                    }

                                }
                                if (!fContains) {
                                    record.artists.add(artist.get("name").toString());
                                }
                            }

                            //Loop through every releaseGroup we got from the JSON
                            JSONArray releaseGroupsArray = (JSONArray)recording.get("releasegroups");
                            if (releaseGroupsArray != null) {
                                for (Object anObj4 : releaseGroupsArray) {
                                    JSONObject releaseGroup = (JSONObject) anObj4;
                                    artistArray = (JSONArray)releaseGroup.get("artists");

                                    //Loop through every artist in this section
                                    for (Object anObj3 : artistArray) {
                                        JSONObject artist = (JSONObject) anObj3;
                                        fContains = false;

                                        //check for duplicates, add if there are none.
                                        for (String str : record.artists) {
                                            if (record.artists.size() > 0) {
                                                if (str.trim().contains(artist.get("name").toString())){
                                                    fContains = true;
                                                }
                                            }

                                        }
                                        if (!fContains) {
                                            record.artists.add(artist.get("name").toString());
                                        }
                                    }

                                    //check for duplicate Albums, add if there are none
                                    fContains = false;
                                    for (String str : record.albums) {
                                        if (record.albums.size() > 0) {
                                            if (str.trim().contains(releaseGroup.get("title").toString())) {
                                                fContains = true;
                                            }
                                        }

                                    }
                                    if (!fContains) {
                                        record.albums.add(releaseGroup.get("title").toString());
                                    }
                                }
                            }
                            fContains = false;
                            //check for duplicate song titles, add if there are none
                            for (String str : record.titles) {
                                if (record.titles.size() > 0) {
                                    if (str.trim().contains(recording.get("title").toString())) {
                                        fContains = true;
                                    }
                                }

                            }
                            if (!fContains) {
                                record.titles.add(recording.get("title").toString());
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                ex.getMessage();
                ex.printStackTrace();
            }

            if (record.artists.size() == 1) {
                mf.setArtist(record.artists.get(0));
            } else if (record.artists.size() > 1) {
                mf.setDuplicates(true);
            }

            if (record.albums.size() == 1) {
                mf.setAlbum(record.albums.get(0));
            } else if (record.albums.size() > 1) {
                mf.setDuplicates(true);
            }

            if (record.titles.size() == 1) {
                mf.setTitle(record.titles.get(0));
            } else if (record.titles.size() > 1) {
                mf.setDuplicates(true);
            }


            //Give this information to the MusicFile for use in the GUI
            mf.setRecord(record);
            mf.setIdentified();

        }
    }
}

