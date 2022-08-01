package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 템플릿 사용
 */

@Slf4j
public class MemberServiceV3_2 {


    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    //트랜잭션 매니저를 주입받아 트랜잭션 템플릿으로 생성, 사용
    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) {

        //execute 안에서 트랜잭션 시작, 로직 수행에 따라 커밋/롤백
        //수행/체크 예외 -> 커밋 / 언체크 예외 -> 롤백
        txTemplate.executeWithoutResult((status) ->
        {
            //비즈니스 로직 수행
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                //언체크 예외로 전환
                throw new IllegalStateException(e);
            }
        });

    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember); //예외 상황 테스트
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
