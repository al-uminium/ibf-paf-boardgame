package ibf.paf.boardgame.service;

import java.util.List;

import org.bson.Document;
import java.sql.Timestamp;

import javax.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.boardgame.repository.BoardGameRepo;

@Service
public class BoardGameService {
  @Autowired 
  private BoardGameRepo bgRepo; 

  public JsonObject getGames (Integer limit, Integer offset) {
    JsonArrayBuilder gamesJsonArray = Json.createArrayBuilder();

    if (limit == null) {
      limit = 25; 
    }

    if (offset == null) {
      offset = 0; 
    }

    List<Document> queryList = bgRepo.getBoardGames(limit, offset); 
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
