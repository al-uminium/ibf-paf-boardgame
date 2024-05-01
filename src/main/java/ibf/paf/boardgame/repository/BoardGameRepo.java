package ibf.paf.boardgame.repository;

import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.ScriptOperators.Accumulator;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.expression.spel.ast.Projection;
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

//   db.games.aggregate([
//     {
//         $match: { gid: 5 }
//     },
//     {
//         $lookup: {
//             from: 'comments',
//             foreignField: 'gid',
//             localField: 'gid',
//             as: 'comments'
//         }
//     },
//     {
//         $project: {
//             game_id: "$gid", 
//             name: 1,
//             year: 1,
//             ranking: 1,
//             average: { $avg: "$comments.rating" }, 
//             users_rated: 1, 
//             url: 1, 
//             thumbnail: 1
//         }
//     }
// ])
  public List<Document> getGameById(Integer gid) {
    MatchOperation matchGID = Aggregation.match(Criteria.where("gid").is(gid));
    LookupOperation lookup = Aggregation.lookup("comments", "gid", "gid", "comments");
    AggregationOperation project = Aggregation.project()
                                              .and("gid").as("game_id")
                                              .and("name").as("name")
                                              .and("year").as("year")
                                              .and("ranking").as("ranking")
                                              .and(AccumulatorOperators.Avg.avgOf("comments.rating")).as("average")
                                              .and("users_rated").as("users_rated")
                                              .and("url").as("url")
                                              .and("image").as("thumbnail");

    Aggregation pipeline = Aggregation.newAggregation(matchGID, lookup, project);

    AggregationResults<Document> results = template.aggregate(pipeline, "games", Document.class);

    List<Document> res = results.getMappedResults();

    for (Document document : res) {
      System.out.println(document.toString());
    }

    return results.getMappedResults();
  }
}
