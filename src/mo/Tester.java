package mo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Tester{
  public static void main(String[] args) {
      try {
          JSONParser parser = new JSONParser();
          String s = "[\n" +
"  {\n" +
"    \"score\": 0.968049,\n" +
"    \"recordings\": [\n" +
"      {\n" +
"        \"duration\": 167,\n" +
"        \"artists\": [\n" +
"          {\n" +
"            \"name\": \"Owl City\",\n" +
"            \"id\": \"ad279295-653f-42e1-9aaa-b731df2e78ba\"\n" +
"          }\n" +
"        ],\n" +
"        \"id\": \"5252d258-3d57-4d95-9a55-174f00467da4\",\n" +
"        \"title\": \"Hello Seattle\"\n" +
"      },\n" +
"      {\n" +
"        \"duration\": 167,\n" +
"        \"artists\": [\n" +
"          {\n" +
"            \"name\": \"Owl City\",\n" +
"            \"id\": \"ad279295-653f-42e1-9aaa-b731df2e78ba\"\n" +
"          }\n" +
"        ],\n" +
"        \"id\": \"6d05f8a1-d9df-415c-9c64-c1d6b6943c34\",\n" +
"        \"title\": \"Hello Seattle\"\n" +
"      }\n" +
"    ],\n" +
"    \"id\": \"2a02fbcd-f1a4-4c98-a5e1-e87b40365f5a\"\n" +
"  }\n" +
"]";
          Object obj = parser.parse(s);
          JSONArray array = (JSONArray)obj;
         
          //print out everything
          System.out.println(array.get(0));
          JSONObject jsob = (JSONObject)(array.get(0));
         
          //get the recordings array
          JSONArray recordingsArray = (JSONArray) jsob.get("recordings");
          JSONObject recording = (JSONObject) recordingsArray.get(0);
          int duration = Integer.parseInt(recording.get("duration").toString());
          
          //get the duration
          System.out.println(duration/60 + ":" + duration%60);
          
          //Get the title of the song
          System.out.println(recording.get("title"));
          
          //get the artists
          JSONArray artistsArray = (JSONArray)recording.get("artists");
          JSONObject artist = (JSONObject) artistsArray.get(0);
          
          System.out.println(artist.get("name"));
      } catch (ParseException ex) {
          Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}