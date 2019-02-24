package com.igori.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by IgorIvaniuk on 15.04.2016.
 */
@Data
@Builder
public class GenerateResponse {

    private Integer totalCount;
    private List<Integer> customerIds;
}
