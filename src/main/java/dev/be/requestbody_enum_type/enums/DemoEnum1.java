package dev.be.requestbody_enum_type.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum DemoEnum1 {

    GOOD_GID("goodGid"),
    HELLO_WORLD("helloWorld");

    private String text;

    DemoEnum1(String text) {
        this.text = text;
    }

    @JsonCreator
    public static DemoEnum1 from(String s) {
        /**
         * [1] : 2개 조건이 필요한 이유 (= i.name().equals(s) || i.getText().equals(s))
         * - Request -> from() 호출 -> Controller -> Builder에서 .demoEnum() 호출 -> from() 호출
         *
         * - 1번째 from() 호출 시에는 Request에 담겨온 값으로 DemoEnum1에서 값을 찾는다.
         * - 이때는 Enum.getText()로 Enum을 찾는다.
         * - 그리고 2번째 from() 호출 시에는 이미 찾은 Enum로 값을 찾는데
         * - 이 경우 Enum.name()로 비교해야한다.
         * - i.getText()로 비교하면 올바른 값을 찾을 수 없다.
         */
        return Arrays.stream(values())
                     .filter(i -> i.name().equals(s) || i.getText().equals(s)) // [1]
                     .findFirst()
                     .orElseGet(() -> {
                         log.warn("Invalid Enum Value : {}", s);
                         return null;
                     });
    }
}
