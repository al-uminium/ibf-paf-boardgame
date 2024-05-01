package ibf.paf.boardgame.service;

public class BoardGamesException extends Exception {
  public BoardGamesException() { }
  public BoardGamesException(String msg) { super(msg, null, true, false); }
}
