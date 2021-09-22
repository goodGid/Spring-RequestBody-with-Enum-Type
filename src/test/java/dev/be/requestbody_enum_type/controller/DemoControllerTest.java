package dev.be.requestbody_enum_type.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.be.requestbody_enum_type.dto.request.Demo1Request;
import dev.be.requestbody_enum_type.dto.response.DemoResponse;
import dev.be.requestbody_enum_type.enums.DemoEnum1;
import dev.be.requestbody_enum_type.enums.DemoEnum4;

/**
 * [1] : WebEnvironment.RANDOM_PORT를 사용하면 실제로 내장 톰캣이 뜬다.
 * 그래서 mockMvc가 아니라
 * RestTemplate(=[2])을 사용하여 내장 톰캣과 Interaction 이 가능하다.
 *
 * ref : https://goodgid.github.io/Spring-Test-SpringBootTest-Annotation/#webenvironmentrandom_port--resttemplate
 */
@ExtendWith(SpringExtension.class) // Junit5 사용 시
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // [1]
class DemoControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate; // [2]

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * === SUCCESS 테스트코드 ===
     */
    @Test
    public void SUCCESS_Call_POST_Controller_with_Request_Body() throws Exception {
        Demo1Request requestBody = Demo1Request.builder()
                                               .id("1")
                                               .demoEnum(DemoEnum1.GOOD_GID)
                                               .build();

        HttpEntity<Demo1Request> request = new HttpEntity<>(requestBody, getHttpHeaders());

        ResponseEntity<DemoResponse> result = testRestTemplate.postForEntity("/api/enum/1",
                                                                             request,
                                                                             DemoResponse.class);
        Assertions.assertThat(result.getBody().getId()).isEqualTo("1");
        Assertions.assertThat(result.getBody().getDemo1Enum()).isEqualTo(DemoEnum1.GOOD_GID);
    }

    @Test
    public void SUCCESS_Call_POST_Controller_with_JSONObject() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("demoEnum", "goodGid");

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), getHttpHeaders());

        ResponseEntity<DemoResponse> result = testRestTemplate.postForEntity("/api/enum/1",
                                                                             request,
                                                                             DemoResponse.class);

        Assertions.assertThat(result.getBody().getId()).isEqualTo(jsonObject.get("id"));
        Assertions.assertThat(result.getBody().getDemo1Enum()).isEqualTo(DemoEnum1.GOOD_GID);
    }

    @Test
    public void SUCCESS_Call_POST_Controller_with_JSONObject_List_Enum() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "1");
        jsonObject.put("demoEnum", new ArrayList<String>() {{
            add("goodGid");
            add("helloWorld");
        }});
        try {
            objectMapper.writeValueAsString(jsonObject);
        } catch (Exception e) {
            /*
            List를 갖고있는 JSONObject를 ObjectMapper로 파싱하면 에러가 발생한다.

            com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
            No serializer found for class org.json.JSONObject and no properties discovered to create BeanSerializer
            (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
             */
            System.out.println(e);
        }
        /**
         * 우리의 목표는 Jackson에게 JSON 구조의 String을 넘겨주는 것이다.
         * 그러므로 JSON 구조의 String을 만들어주는 방법으로
         * 무난하게 Map과 ObjectMapper를 사용한다.
         */
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("demoEnum", new ArrayList<String>() {{
            add("goodGid");
            add("helloWorld");
        }});

        String asString = objectMapper.writeValueAsString(map);

        HttpEntity<String> request = new HttpEntity<>(asString, getHttpHeaders());

        ResponseEntity<DemoResponse> result = testRestTemplate.postForEntity("/api/enum/4",
                                                                             request,
                                                                             DemoResponse.class);

        Assertions.assertThat(result.getBody().getId()).isEqualTo(jsonObject.get("id"));
        Assertions.assertThat(result.getBody().getDemo4Enum().get(0).name()).isEqualTo(DemoEnum4.GOOD_GID.name());
        Assertions.assertThat(result.getBody().getDemo4Enum().get(1).name()).isEqualTo(DemoEnum4.HELLO_WORLD.name());
    }

    /**
     * === FAIL 테스트코드 ===
     */
    @Test
    public void FAIL_Call_POST_Controller_with_JSONObject_1() throws Exception {
        /**
         * ## Information
         * API : /api/enum/2
         *
         * Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error:
         * Cannot construct instance of `dev.be.requestbody_enum_type.enums.DemoEnum2`,
         * problem: No enum constant dev.be.requestbody_enum_type.enums.DemoEnum2.goodGid2; nested exception is com.fasterxml.jackson.databind.exc.ValueInstantiationException:
         * at [Source: (PushbackInputStream); line: 1, column: 22] (through reference chain: dev.be.requestbody_enum_type.dto.request.Demo2Request["demoEnum"])]
         *
         * ## Comment
         * DemoEnum2.from() 메소드에서
         * 입력받은 "s"값에 해당하는 Enum을 찾을 수 없으므로 'Cannot construct" 발생한다.
         */

        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", "1");
        personJsonObject.put("demoEnum", "goodGid2");

        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), getHttpHeaders());

        ResponseEntity<DemoResponse> result = testRestTemplate.postForEntity("/api/enum/2",
                                                                             request,
                                                                             DemoResponse.class);
        Assertions.assertThat(result.getStatusCode()).isNotEqualTo(HttpStatus.OK);
    }

    @Test
    public void FAIL_Call_POST_Controller_with_JSONObject_2() throws Exception {
        /**
         * ## Information
         * API : /api/enum/3
         *
         * Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error:
         * Cannot deserialize value of type `dev.be.requestbody_enum_type.enums.DemoEnum3` from String "goodGid2":
         * not one of the values accepted for Enum class: [GOOD_GID, HELLO_WORLD]; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException:
         * at [Source: (PushbackInputStream); line: 1, column: 22] (through reference chain: dev.be.requestbody_enum_type.dto.request.Demo3Request["demoEnum"])]
         *
         * ## Comment
         * DemoEnum2와 달리 @JsonCreator가 없으므로
         * 'Cannot construct' 에러가 아닌 'Cannot deserialize'가 발생한다.
         */

        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", "1");
        personJsonObject.put("demoEnum", "goodGid2");

        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), getHttpHeaders());

        ResponseEntity<DemoResponse> result = testRestTemplate.postForEntity("/api/enum/3",
                                                                             request,
                                                                             DemoResponse.class);
        Assertions.assertThat(result.getStatusCode()).isNotEqualTo(HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-CUSTOM-HEADER", "custom header");
        return headers;
    }

}