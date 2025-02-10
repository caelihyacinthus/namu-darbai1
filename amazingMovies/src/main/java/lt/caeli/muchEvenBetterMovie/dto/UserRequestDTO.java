package lt.caeli.muchEvenBetterMovie.dto;

import lt.caeli.muchEvenBetterMovie.model.Role;

import java.util.List;

public record UserRequestDTO(
        long id,
        String username,
        List<Role> roles
) {
}
