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
  private Integer limit = 25; 
  private Integer offset = 0;

  @Autowired private Utils utils; 

  public JsonObject getGames (Integer limit, Integer offset) {
    List<Document> queryList = bgRepo.getBoardGames(limit==null ? limit : this.limit, offset==null ? offset : this.offset); 
    JsonObject jsonResp = utils.generateJsonResponse(queryList, offset, limit);

    return jsonResp;
  }

  public JsonObject getGamesByRank (Integer limit, Integer offset) {
    List<Document> queryList = bgRepo.getBoardGamesByRank(limit==null ? limit : this.limit, offset==null ? offset : this.offset);
    JsonObject jsonResp = utils.generateJsonResponse(queryList, offset, limit);
    return jsonResp; 
  }

  public JsonObject getGameById(Integer gid) {
    System.out.println(gid);
    List<Document> queryList = bgRepo.getGameById(gid);
    JsonObject resp = utils.generateGameIDResponse(queryList.getFirst());
    return resp; 
  }
}
