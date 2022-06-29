package hello.core.singleton;

public class SingletonService {

    //static 영역에 객체 하나 생성
    private static final SingletonService instance = new SingletonService();

    //public 메서드로 조회 허용
    public static SingletonService getInstance() {
        return instance;
    }

    //private 생성자
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
