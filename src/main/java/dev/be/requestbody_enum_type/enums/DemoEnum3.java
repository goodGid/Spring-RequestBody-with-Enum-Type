package dev.be.requestbody_enum_type.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum DemoEnum3 {

    GOOD_GID("good_gid"),
    HELLO_WORLD("helloWorld");

    private String text;

    DemoEnum3(String text) {
        this.text = text;
    }

}
