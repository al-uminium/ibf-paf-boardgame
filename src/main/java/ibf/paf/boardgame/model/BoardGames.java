package ibf.paf.boardgame.model;

import org.bson.types.ObjectId;

public class BoardGames {
  private ObjectId _id;
  private Integer gid; 
  private String name;
  private Integer year; 
  private Integer ranking; 
  private Integer users_rated; 
  private String url; 
  private String image;   

  public BoardGames(Integer gid, String name) {
    this.gid = gid;
    this.name = name;
  }

  @Override
  public String toString() {
    return "BoardGames [_id=" + _id + ", gid=" + gid + ", name=" + name + ", year=" + year + ", ranking=" + ranking
        + ", usersRated=" + users_rated + ", url=" + url + ", image=" + image + "]";
  }
}
