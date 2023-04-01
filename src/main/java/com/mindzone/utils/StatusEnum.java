package com.mindzone.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    ACTIVE("active"), INACTIVE("in-active");
    private String status;
}
