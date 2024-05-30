package com.example.wanted.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum State {
    ONSALE("ONSALE"), RESERVED("RESERVED"), SOLDOUT("SOLDOUT");

    String state;

    State(String state) {
        this.state = state;
    }

    @JsonValue
    public String value() {
        return state;
    }

    @JsonCreator
    public static State parsing(String inputValue) {
        return Arrays.stream(State.values()).filter(type
                -> type.value().equals(inputValue)).findFirst().orElse(null);
    }
}
