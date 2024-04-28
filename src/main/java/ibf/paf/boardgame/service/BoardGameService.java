package ibf.paf.boardgame.service;

import java.util.List;

import org.bson.Document;
import java.sql.Timestamp;

import javax.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf.boardgame.Utils;
import ibf.paf.boardgame.repository.BoardGameRepo;

@Service
public class BoardGameService {
  @Autowired 
  private BoardGameRepo bgRepo; 

  @Autowired private Utils utils; 

  public JsonObject getGames (Integer limit, Integer offset) {

    if (limit == null) {
      limit = 25; 
    }

    if (offset == null) {
      offset = 0; 
    }

    List<Document> queryList = bgRepo.getBoardGames(limit, offset); 
    JsonObject jsonResp = utils.generateJsonResponse(queryList, offset, limit);

    return jsonResp;
  }

  public JsonObject getGamesByRank (Integer limit, Integer offset) {
    List<Document> queryList = bgRepo.getBoardGamesByRank(limit, offset);
    JsonObject jsonResp = utils.generateJsonResponse(queryList, offset, limit);
    return jsonResp; 
  }


}
