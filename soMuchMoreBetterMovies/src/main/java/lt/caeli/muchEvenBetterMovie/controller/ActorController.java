package lt.caeli.muchEvenBetterMovie.controller;

import lt.caeli.muchEvenBetterMovie.dto.ActorDTO;
import lt.caeli.muchEvenBetterMovie.dto.ActorMapper;
import lt.caeli.muchEvenBetterMovie.model.Actor;
import lt.caeli.muchEvenBetterMovie.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActorController {
  ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @GetMapping("/actors")
  public ResponseEntity<List<ActorDTO>> getAllActors() {
    return ResponseEntity.ok(ActorMapper.toActorDTO(actorService.findAllActors()));
  }

}
