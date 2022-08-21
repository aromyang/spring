package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

//체크 예외 SQLException을 인터페이스에서도 던져주어야 함 -> 종속적 -> 체크 예외를 런타임 예외로 변경하자
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
