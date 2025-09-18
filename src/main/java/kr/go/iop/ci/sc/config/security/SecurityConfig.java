package kr.go.iop.ci.sc.config.security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * org.egovframe.cloud.userservice.SecurityConfig
 * <p>
 * Spring Security Config 클래스.
 * AuthenticationFilter 를 추가하고 로그인 인증처리를 한다
 *
 * @author 표준프레임워크센터 jaeyeolkim
 * @version 1.0
 * @since 2021/06/30
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자            수정내용
 *  ----------    --------    ---------------------------
 *  2021/06/30    jaeyeolkim  최초 생성
 *  2023.11.13    양정숙        MSA팀 적용
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	
	@Autowired
	private JwtAuthenticationApiFilter jwtAuthenticationApiFilter;

	
	/**
	 * 스프링 시큐리티 설정
	 *
	 * @param http
	 * @throws Exception
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		// 로그인 인증정보를 받아 토큰을 발급할 수 있도록 필터를 등록해준다.
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProvider);

		http.csrf().disable()
			.cors().and()
			.headers().frameOptions().disable()
			.and()
			
			//세션 정책 설정
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
			.and()
			
			//인가 정책
			.authorizeRequests()
			.antMatchers("/login", "/userList").permitAll()
			// .antMatchers(SECURITY_PERMITALL_ANTPATTERNS).permitAll()
			// .anyRequest().access("@authorizationService.isAuthorization(request, authentication)") // 호출 시 권한 인가 데이터 확인
			//.anyRequest().authenticated() //order(2)로 넘어가도록 주석처리
			.anyRequest().permitAll()
			.and()
			
			//인증 설정
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	@Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .cors().and()
        .headers().frameOptions().disable()
        .and()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
            .antMatchers("/auth/**", "/", "/**").permitAll()  // 인증 API는 모두 접근 가능
            .anyRequest().permitAll()                  // JWT 필터에서 처리하도록 허용
        .and()
        .addFilterBefore(jwtAuthenticationApiFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic().disable();

        return http.build();
    }
}
