
## Goal

- 클라이언트의 요청 값을 바로 Enum 타입으로 받고 싶다.


## Requirements

- Enum으로 매핑시킬 Key 값을

  서버에 정의해놓은 Enum 값이 아닌 (= GOOD_GID)
  
  Enum의 Text 값으로 받아 (= goodGid)
  
  Enum 값으로 매핑시켜주고 싶다.

> Enum.class

``` java
public enum DemoEnum1 {

    GOOD_GID("goodGid"),
    HELLO_WORLD("helloWorld");
    
    private String text;

    ...
}
```

- 이 경우 "goodGid"라는 값을 받으면 코드내에서는 DemoEnum1.GOOD_GID로 매핑시켜준다.

## KeyPoint

``` java
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
        return Arrays.stream(values())
                     .filter(i -> i.getText().equals(s))
                     .findFirst()
                     .orElseGet(() -> {
                         log.warn("Invalid Enum Value : {}", s);
                         return null;
                     });
    }
}
```

- 위 요구사항을 충족시키기 위해선 `@JsonCreator`를 활용하면 된다.

- 클라이언트의 요청 값을 서버는 `Deserialize` 한다.

  이 때 @JsonCreator를 선언해주면
  
  Deserialize 시 내가 정의한 로직으로 Enum 변환이 가능하다.
  
- 클라이언트가 "goodGid"라는 값을 보냈으면

  from()에 정의해놓은 로직을 통해
  
  GOOD_GID로 매핑해서 return 시켜준다.
