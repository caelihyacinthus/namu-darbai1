package lt.caeli.muchEvenBetterMovie.service;

import lt.caeli.muchEvenBetterMovie.model.Actor;
import lt.caeli.muchEvenBetterMovie.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
  ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public List<Actor> findAllActors() {
    return actorRepository.findAll();
  }
}
