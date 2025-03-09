package com.exabyting.rms.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceNotFound  extends Exception{
    private String message;
}
