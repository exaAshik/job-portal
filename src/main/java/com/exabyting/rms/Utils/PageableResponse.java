package com.exabyting.rms.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableResponse <T>{
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
}
