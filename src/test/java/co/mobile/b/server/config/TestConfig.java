package co.mobile.b.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

/**
 * 테스트 설정 잡을만한 것 설정잡기.
 */
@TestConfiguration
public class TestConfig {

    /**
     * 테스트는 H2 메모리 디비라서 데이터를 로딩시켜놔야함. -> 서버에서는 대부분 배포시에만 실행됨.
     * @return
     */
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {

        };
    }

}
