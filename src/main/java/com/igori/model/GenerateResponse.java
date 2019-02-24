package com.igori.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenerateResponse {

  private Integer totalCount;
  private List<Integer> customerIds;
}
