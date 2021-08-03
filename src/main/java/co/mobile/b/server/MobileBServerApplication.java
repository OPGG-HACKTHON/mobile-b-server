package co.mobile.b.server;

import co.mobile.b.server.entity.User;
import co.mobile.b.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MobileBServerApplication {

    @Autowired
    UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(MobileBServerApplication.class, args);
    }
    
    // TODO : 임시
    // 초기화 작업 할 메소드, 해당 어노테이션이 적용된 초기화 메서드는 WAS가 띄워질 때 실행
    @PostConstruct
    public void init(){
        userRepository.save(new User(1L, "test", "test", "안자이청"));
    }
    
}
