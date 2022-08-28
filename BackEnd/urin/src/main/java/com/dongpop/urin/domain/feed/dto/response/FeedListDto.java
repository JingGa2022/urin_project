package com.dongpop.urin.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Data
public class FeedListDto {
    private int totalPages;
    private List<FeedDto> feedList;
}
