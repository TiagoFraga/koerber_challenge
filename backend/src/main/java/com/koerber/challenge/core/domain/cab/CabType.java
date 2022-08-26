package com.koerber.challenge.core.domain.cab;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CabType {

    YELLOW(1,"Yellow"),
    GREEN(2,"Green");

    private final int id;
    private final String name;
}
