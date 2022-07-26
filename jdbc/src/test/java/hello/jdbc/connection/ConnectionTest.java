package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {

        //커넥션 획득 시마다 URL, USERNAME, PASSWORD 전달
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        //서로 다른 새로운 커넥션을 획득
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());

    }

    @Test
    void dataSourceDriverManager() throws SQLException {

        //생성 시점에만 URL, USERNAME, PASSWORD 넣어줌
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션 획득 시 파라미터 필요 X -> 설정과 사용이 분리되어 있다
        useDataSource(dataSource);
        //항상 새로운 커넥션을 획득

    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10); //(default)
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource); //total=10, active=2, idle=8, waiting=0
        Thread.sleep(1000); //커넥션 생성 로그 확인을 위해 추가 (커넥션 풀에 커넥션을 채울 때 별도의 스레드가 사용됨)

    }


    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());

    }

}
