package lt.caeli.muchEvenBetterMovie.dto;

import lt.caeli.muchEvenBetterMovie.model.Role;

import java.util.List;

public record UserPostDTO(
        Long id,
        String username,
        String password,
        List<Role> roles
) {
}
