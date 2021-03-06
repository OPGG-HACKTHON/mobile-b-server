package co.mobile.b.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

/* BaseEntity 사용하기 위해 사용 */
@EnableJpaAuditing
@SpringBootApplication
public class MobileBServerApplication {
    
    public static void main(String[] args) {

//        System.setProperty("spring.profiles.default", "dev");

        SpringApplication.run(MobileBServerApplication.class, args);
    }
    
    // TODO : 임시
    // 초기화 작업 할 메소드, 해당 어노테이션이 적용된 초기화 메서드는 WAS가 띄워질 때 실행
    @PostConstruct
    public void init(){

    }
    
}
