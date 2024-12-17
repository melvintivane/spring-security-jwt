package tech.melvin.springsecurity6.controller.dto;

import java.util.List;

public record FeedDTO(List<FeedItemDTO> feedItems,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements) {
}
