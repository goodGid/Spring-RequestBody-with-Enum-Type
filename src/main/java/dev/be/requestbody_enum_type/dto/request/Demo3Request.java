package dev.be.requestbody_enum_type.dto.request;

import dev.be.requestbody_enum_type.enums.DemoEnum3;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo3Request {
    private String id;
    private DemoEnum3 demoEnum;
}