package co.mobile.b.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }

    /**
     * CORS 이슈 해결.
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO 매핑설정 세분화해야함.
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600L);
    }

    /**
     * 스프링부트 2.2 이후 버전에서 발생하는 charset 이슈 해결.(application/json;charset=utf-8. 이런식으로 content-type 넘어가던게
     * 2.2 이후에서는 application/json 만 보내도록 권장함. 따라서 따로 characterEncodingFilter를 빈으로 등록해서 UTF-8로 인코딩하도록 강제로 처리)
     * @return
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

}
