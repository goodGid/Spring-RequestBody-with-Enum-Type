package dev.be.requestbody_enum_type.dto.request;

import java.util.List;

import dev.be.requestbody_enum_type.enums.DemoEnum4;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Demo4Request {
    private String id;
    private List<DemoEnum4> demoEnum;
}