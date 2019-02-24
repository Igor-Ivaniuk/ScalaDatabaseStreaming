package com.igori.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GenerateResponse {

    private Integer totalCount;
    private List<Integer> customerIds;
}
