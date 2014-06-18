package mo;

import java.io.FileReader;
import org.json.JSONObject;
public class Tester{
  public static void main(String[] args) {
  String s = "{\"status\": \"ok\", \"results\": [{\"recordings\": [{\"artists\": [{\"id\": \"ad279295-653f-42e1-9aaa-b731df2e78ba\", \"name\": \"Owl City\"}], \"duration\": 167, \"releasegroups\": [{\"artists\": [{\"id\": \"89ad4ac3-39f7-470e-963a-56509c546377\", \"name\": \"Various Artists\"}], \"secondarytypes\": [\"Compilation\"], \"type\": \"Album\", \"id\": \"673ca53a-4ad8-4d69-9f0c-d557a7fa08d5\", \"title\": \"Promo Only: Mainstream Radio, May 2010\"}, {\"type\": \"Album\", \"id\": \"6fa35df1-a08e-42f5-aeb2-ae590ba9fd6b\", \"title\": \"Ocean Eyes\"}], \"title\": \"Hello Seattle\", \"id\": \"5252d258-3d57-4d95-9a55-174f00467da4\"}, {\"artists\": [{\"id\": \"ad279295-653f-42e1-9aaa-b731df2e78ba\", \"name\": \"Owl City\"}], \"duration\": 167, \"releasegroups\": [{\"type\": \"Album\", \"id\": \"6fa35df1-a08e-42f5-aeb2-ae590ba9fd6b\", \"title\": \"Ocean Eyes\"}], \"title\": \"Hello Seattle\", \"id\": \"6d05f8a1-d9df-415c-9c64-c1d6b6943c34\"}], \"score\": 0.968049, \"id\": \"2a02fbcd-f1a4-4c98-a5e1-e87b40365f5a\"}]}";
  
  JSONObject jsob = new JSONObject(s);
  System.out.println(jsob.toString());
  System.out.println(jsob.get("results"));
  }
}