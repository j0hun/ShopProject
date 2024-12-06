package com.jyhun.shop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDTO<T> {

    private int status;
    private String message;
    private T dto;
}
