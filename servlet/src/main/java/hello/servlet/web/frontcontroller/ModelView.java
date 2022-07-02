package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {
    private String viewName; //논리 주소
    private Map<String, Object> model = new HashMap<>(); //뷰를 랜더링하기 위한 객체

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
