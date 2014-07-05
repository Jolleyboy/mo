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
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;


/**
 *
 * @author Joshua
 */
public class MusicIdentifier {

    Model model = Model.getInstance();
    JSONArray array;
  
    public void identifyNewSongs() {

    }
   
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
            Logger.getLogger(MusicIdentifier.class.getName()).log(Level.SEVERE, null, ex);
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

    public void identify(String fingerprint) {
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
                Object obj = JSONValue.parse(EntityUtils.toString(entity1));
                array = (JSONArray)obj;
                
            EntityUtils.consume(entity1);
            response1.close();
        } catch (IOException ex) {
            Logger.getLogger(MusicIdentifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MusicIdentifier.class.getName()).log(Level.SEVERE, null, ex);
        }

       System.out.println("The JSON array is: " + array.get(0));
        
    }
}
