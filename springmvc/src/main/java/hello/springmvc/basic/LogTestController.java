package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller -> String 반환 시 viewname으로 인식 -> 뷰 searching, rendering
@RestController //-> String 그대로 반환됨 (REST API 핵심) -> HTTP 메시지 바디에 바로 입력됨
@Slf4j
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass());
        //-> 클래스 레벨 @Slf4j 로 대체 가능

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        //-> 운영 서버에도 다 남아버림


        /*
        아래로 갈수록 레벨이 낮아짐
        운영 시스템에서 로그의 레벨 설정 가능
        application.properties에서 레벨 설정
        */

        //개발 서버 -> debug, 운영 서버 -> info 레벨로 설정하는 것이 평균적
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        //log 출력 문법("~log={}", value)을 꼭 지키자 + 사용 시 불필요한 연산 발생

        return "ok";
    }
}
