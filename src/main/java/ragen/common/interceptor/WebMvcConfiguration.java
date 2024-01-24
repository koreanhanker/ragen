package ragen.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final HttpInterceptor httpInterceptor;

    @Autowired
    public WebMvcConfiguration(HttpInterceptor httpInterceptor) {
        this.httpInterceptor = httpInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/usr/login","/usr/getUsrId",
                        "/usr/search/usrIdSendMail","/usr/search/usrPwdSendMail",
                        "/usr/setUsrPwdByEmail", "/usr/forward/usrPwd","/usr/forward/usrPwd",
                        "/api/kakao/message","/api/kakao/reference","/api/kakao/expired_session",
                        "/api/kakao/result","/api/kakao/atch/**","/board/atch/**","/email/atch/**","/web/atch/**");
    }
}