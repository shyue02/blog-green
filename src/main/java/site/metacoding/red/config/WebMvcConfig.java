package site.metacoding.red.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import site.metacoding.red.handler.LoginIntercepter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginIntercepter())
		.addPathPatterns("/s/**");	// =어떤 주소일 때 이 인터셉터가 동작하나요? //** = 모든 주소
		//.addPathPatterns("/admin/**")
		//.excludePathPatterns("/s/boards/**");	// 제외
	} // /s/* = /s/boards, s/users	XXXX/s/boards/1 이렇게 두 단계는 매칭 안 됨
}
