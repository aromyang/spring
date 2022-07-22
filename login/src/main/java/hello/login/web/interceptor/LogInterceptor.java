package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid); //afterCompletion에서 쓰기 위해 담기
        //스프링 인터셉터는 호출 시점이 분리되어 있어 HttpServletRequest에 담아두었다
        //!! 인터셉터는 싱글톤처럼 사용되므로 멤버 변수(static)으로 사용하는 경우 위험하다


        /**
         * @RequestMapping -> HandlerMethod
         * 정적 리소스 -> ResourceHttpRequestHandler
         */
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler; //호출한 컨트롤러 메서드의 모든 정보가 포함됨
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        return true; //핸들러 어댑터 호출 -> 컨트롤러 호출출
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        Object logId = request.getAttribute(LOG_ID); //HTTP request는 한 사용자에 대해 보장되므로 가능

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);

        if (ex != null) { //예외 발생한 경우

            log.error("afterCompletion error", ex);

        }

    }
}
