package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }
    //HttpServletResponse 객체로 HTTP 바디에 직접 메시지 전달

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    //v1 + 상태 코드 설정

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }
    //@ResponseBody 사용 -> view 사용 X, HTTP 메시지 컨버터를 통해 HTTP 메시지 직접 입력

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("arom");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }
    //HTTP 메시지 컨버터를 통해 JSON 형식 반환

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("arom");
        helloData.setAge(20);

        return helloData;
    }
    //@ResonseBody 사용 + @ResponseStatus로 상태 코드 설정
    //-> 상태 코드를 동적으로 변경 원할 시 ResponseEntity를 사용한다

}

//@RestController = @Controller + @ResponseBody 로 해결할 수 있다
