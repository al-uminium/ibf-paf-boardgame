package ibf.paf.boardgame.controller;

import org.springframework.web.bind.annotation.RestController;

import ibf.paf.boardgame.Utils;
import ibf.paf.boardgame.service.BoardGameService;

import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class BoardGameRestController {
  @Autowired private BoardGameService svc;
  @Autowired private Utils utils;
  
  @GetMapping("/games")
  public ResponseEntity<Object> getGames(@RequestBody(required = false) String payload) {

    Map<String, Integer> payloadParameters = utils.getLimitAndOffset(payload);

    JsonObject jsonResponse = svc.getGames(payloadParameters.get("limit"), payloadParameters.get("offset"));
    return ResponseEntity.ok(jsonResponse.toString());
  }
  
  @GetMapping("/games/rank")
  public ResponseEntity<Object> getGamesByRank(@RequestBody(required = false) String payload) {

    Map<String, Integer> payloadParameters = utils.getLimitAndOffset(payload);

    JsonObject jsonResponse = svc.getGamesByRank(payloadParameters.get("limit"), payloadParameters.get("offset"));

    return ResponseEntity.ok(jsonResponse.toString());
  }
  
}
