package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor lombok 사용으로 생성자 자동 생성
public class  OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository; //final -> 값을 넣어주어야 한다
    private final DiscountPolicy discountPolicy;

    @Autowired private DiscountPolicy rateDiscountPolicy;


    /* 수정자 주입 테스트
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/



    @Autowired //-> 생성자 1개이므로 생략 가능
    //lombok @RequiredArgsConstructor로 생략
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
