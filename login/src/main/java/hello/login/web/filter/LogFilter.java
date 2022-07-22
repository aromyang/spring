package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("log filter init");

    }

    //HTTP 요청 시 호출
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("log filter doFilter");

        //HTTP 사용할 것이므로 다운 캐스팅 함
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        //HTTP 요청 구분을 위한 임의의 UUID 생성
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);
            //다음 필터가 있으면 필터 호출, 없으면 서블릿 호출
            //호출하지 않으면 다음 단계가 진행되지 않는다 -> Servlet, Controller 호출 X

        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void destroy() {

        log.info("log filter destroy");

    }
}
