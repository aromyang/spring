package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 사용
 * 파라미터 연동, 풀을 고려한 종료하기
 */

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Connection con = dataSource.getConnection();

        try {
            //트랜잭션 시작
            con.setAutoCommit(false);

            //비즈니스 로직 수행
            bizLogic(con, fromId, toId, money);

            //성공 시 커밋
            con.commit();

        } catch (Exception e) {
            //실패 시 롤백
            con.rollback();
            throw new IllegalStateException(e);

        } finally {
            //커넥션 반환
            release(con);
        }

    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember); //예외 상황 테스트
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    //커넥션 close
    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //커넥션 풀에 반환 시 기본 값으로 돌려주기 위해서
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

}
