package lt.caeli.muchEvenBetterMovie.repository;

import lt.caeli.muchEvenBetterMovie.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
