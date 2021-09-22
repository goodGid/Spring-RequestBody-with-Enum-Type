package dev.be.requestbody_enum_type.dto.request;

import dev.be.requestbody_enum_type.enums.DemoEnum1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo1Request {
    private String id;
    private DemoEnum1 demoEnum;
}