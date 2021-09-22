package dev.be.requestbody_enum_type.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum DemoEnum2 {

    GOOD_GID("goodGid"),
    HELLO_WORLD("helloWorld");

    private String text;

    DemoEnum2(String text) {
        this.text = text;
    }

    @JsonCreator
    public static DemoEnum2 from(String s) {
        return valueOf(s);
    }
}
