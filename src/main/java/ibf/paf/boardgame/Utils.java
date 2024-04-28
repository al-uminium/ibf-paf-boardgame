package ibf.paf.boardgame;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.stereotype.Service;

@Service
public class Utils {
  
  public Map<String, Integer> getLimitAndOffset(String payload){
    try {
      JsonReader reader = Json.createReader(new StringReader(payload));
      JsonObject jsonPayload = reader.readObject();
      Map<String, Integer> payloadParameters = new HashMap<>();
      payloadParameters.put("limit", jsonPayload.getInt("limit"));
      payloadParameters.put("offset", jsonPayload.getInt("offset"));
      return payloadParameters;
    } catch (NullPointerException e) {
      Map<String, Integer> payloadParameters = new HashMap<>();
      payloadParameters.put("limit", null);
      payloadParameters.put("offset", null);
      return payloadParameters;
    }
  }
}
