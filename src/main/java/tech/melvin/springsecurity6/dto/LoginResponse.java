package tech.melvin.springsecurity6.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
