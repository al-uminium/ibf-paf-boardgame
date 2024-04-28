package ibf.paf.boardgame.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BoardGameRepo {
  
  @Autowired
  private MongoTemplate template;

  public List<Document> getBoardGames(Integer limit, Integer offset) {
    Query query = Query.query(Criteria.where("name").exists(true)).limit(limit).skip(offset);
    return template.find(query, Document.class, "games");
  }

  public List<Document> getBoardGamesByRank(Integer limit, Integer offset) {
    Criteria criteria = Criteria.where("name").exists(true);
    Query query = Query.query(criteria).limit(limit).skip(offset).with(Sort.by(Sort.Direction.ASC, "ranking"));
    return template.find(query, Document.class, "games");
  }
}
