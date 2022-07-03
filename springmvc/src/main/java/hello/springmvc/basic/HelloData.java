package hello.springmvc.basic;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // -> @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 자동 적용
public class HelloData {
    private String username;
    private int age;
}
