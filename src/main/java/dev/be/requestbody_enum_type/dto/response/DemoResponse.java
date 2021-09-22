package dev.be.requestbody_enum_type.dto.response;

import java.util.List;

import dev.be.requestbody_enum_type.enums.DemoEnum1;
import dev.be.requestbody_enum_type.enums.DemoEnum2;
import dev.be.requestbody_enum_type.enums.DemoEnum3;
import dev.be.requestbody_enum_type.enums.DemoEnum4;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoResponse {
    private String id;
    private DemoEnum1 demo1Enum; // DemoEnum1에 @JsonCreator 선언 O + 자세한 조건까지 존재
    private DemoEnum2 demo2Enum; // DemoEnum2에 @JsonCreator 선언 O
    private DemoEnum3 demo3Enum; // DemoEnum3에 @JsonCreator 선언 X
    private List<DemoEnum4> demo4Enum; // DemoEnum1에 @JsonCreator 선언 O + 자세한 조건까지 존재
}
