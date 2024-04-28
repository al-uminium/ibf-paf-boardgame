package ibf.paf.boardgame;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.bson.Document;
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

  public JsonObject generateJsonResponse(List<Document> queryList, Integer offset, Integer limit) {
    JsonArrayBuilder gamesJsonArray = Json.createArrayBuilder();
    for (Document document : queryList) {
      Integer gid = document.getInteger("gid");
      String name = document.getString("name");
      gamesJsonArray.add(Json.createObjectBuilder()
                    .add("game_id", gid)
                    .add("name", name));
    }

    JsonObject jsonResp = Json.createObjectBuilder()
                                .add("games", gamesJsonArray)
                                .add("offset", offset)
                                .add("limit", limit)
                                .add("total", limit)
                                .add("timestamp", new Timestamp(System.currentTimeMillis()).toString())
                                .build();

    return jsonResp;
  }
}
