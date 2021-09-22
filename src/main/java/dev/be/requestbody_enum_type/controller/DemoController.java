package dev.be.requestbody_enum_type.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.be.requestbody_enum_type.dto.request.Demo1Request;
import dev.be.requestbody_enum_type.dto.request.Demo2Request;
import dev.be.requestbody_enum_type.dto.request.Demo3Request;
import dev.be.requestbody_enum_type.dto.request.Demo4Request;
import dev.be.requestbody_enum_type.dto.response.DemoResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("/api/enum/1")
    public DemoResponse demo1(@RequestBody Demo1Request demoRequest) {
        System.out.println();
        return DemoResponse.builder()
                           .id(demoRequest.getId())
                           .demo1Enum(demoRequest.getDemoEnum()) // 생성자를 한번 더 호출한다. (= DemoEnum1.from()가 호출됨)
                           .build();
    }

    @PostMapping("/api/enum/2")
    public DemoResponse demo2(@RequestBody Demo2Request demoRequest) {
        System.out.println();
        return DemoResponse.builder()
                           .id(demoRequest.getId())
                           .demo2Enum(demoRequest.getDemoEnum())
                           .build();
    }

    @PostMapping("/api/enum/3")
    public DemoResponse demo3(@RequestBody Demo3Request demoRequest) {
        System.out.println();
        return DemoResponse.builder()
                           .id(demoRequest.getId())
                           .demo3Enum(demoRequest.getDemoEnum())
                           .build();
    }

    @PostMapping("/api/enum/4")
    public DemoResponse demo4(@RequestBody Demo4Request demoRequest) { // List<DemoEnum4> 형식으로 요청을 받는다.
        System.out.println();
        return DemoResponse.builder()
                           .id(demoRequest.getId())
                           .demo4Enum(demoRequest.getDemoEnum()) // 생성자를 한번 더 호출한다. (= DemoEnum1.from()가 호출됨)
                           .build();
    }
}
