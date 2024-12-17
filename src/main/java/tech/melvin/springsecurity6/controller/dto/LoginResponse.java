package tech.melvin.springsecurity6.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
