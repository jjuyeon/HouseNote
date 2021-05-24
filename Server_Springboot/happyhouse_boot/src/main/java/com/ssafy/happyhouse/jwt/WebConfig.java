package com.ssafy.happyhouse.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String[] EXCLUDE_PATHS = {
			"/api/user/kakaologin",
			"/api/user/logintoken",
			"/api/sido",
			"/api/gugun",
			"/api/dong",
    };
	
	@Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
						.addPathPatterns("/**")
						.excludePathPatterns(EXCLUDE_PATHS);
    }
}
