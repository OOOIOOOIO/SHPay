package com.sh.shpay.global.config;

import com.sh.shpay.global.resolver.token.TokenInfoFromHeaderArgumentResolver;
import com.sh.shpay.global.session.interceptor.sessionCheckInterceptor.UserSessionCheckInterceptor;
import com.sh.shpay.global.session.resolver.usersession.UserInfoFromSessionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * .yml에서 add-mappings을 false로 설정하면 스프링에서 기본적으로 제공하는 정적자원요청에 대한 매핑을 사용하지 않기 때문에 정적 리소스 접근을 위해 열어두어야 한다.
 *
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInfoFromSessionResolver userInfoFromSessionResolver;
    private final TokenInfoFromHeaderArgumentResolver tokenInfoFromHeaderArgumentResolver;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSessionCheckInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/openbanking/**", "/api/users/**", "/api/menu/**", "/api/main/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoFromSessionResolver);
        resolvers.add(tokenInfoFromHeaderArgumentResolver);

    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

/* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
//                registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는s다. */
//                registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
//                registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
//                }