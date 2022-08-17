package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();

        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);

    }


    //RuntimeException 상속 -> Unchecked
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }


    //Unchecked -> 예외를 잡거나 던지지 않아도 자동으로 처리해줌
    static class Service {
        Repository repository = new Repository();

        //필요한 경우 예외를 잡아서 처리
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        //throws 하지 않아도 ok
        public void callThrow() {
            repository.call();
        }

    }


    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }


}
