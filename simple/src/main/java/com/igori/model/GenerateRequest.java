package com.igori.model;

import lombok.Data;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */
@Data
public class GenerateRequest {

    private Integer custCount;
    private Integer reviewCountMin;
    private Integer reviewCountMax;

}
