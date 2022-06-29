package hello.core.singleton;

public class StatefulService {
    //private int price; //공유되는 필드

//    public  void order(String name, int price) {
//        System.out.println("name = " + name + " price = " + price);
//        this.price = price;
//    }

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price; //공유 필드를 사용하지 않고 지역 변수를 사용해 바로 리턴
    }


//    public int getPrice() {
//        return price;
//    }


}
