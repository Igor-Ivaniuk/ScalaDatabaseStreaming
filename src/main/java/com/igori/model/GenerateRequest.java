package com.igori.model;

import lombok.Data;

@Data
public class GenerateRequest {

  private Integer custCount;
  private Integer reviewCountMin;
  private Integer reviewCountMax;

}
