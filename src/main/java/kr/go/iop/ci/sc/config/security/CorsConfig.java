package kr.go.iop.ci.sc.config.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import kr.go.iop.ci.sc.config.info.CorsInfo;


/**
 * <p>
 * 로컬 환경에서 CORS 허용을 위한 설정
 *
 * @author hdw
 * @version 1.0
 * @since 2023.12.07
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2023.12.07    hdw  최초 생성

 * </pre>
 */
@Configuration
public class CorsConfig {

	@Autowired
	private CorsInfo corsInfo;

//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//		List<String> allowList = Optional.ofNullable(corsInfo.getCmmnUrls()).orElse(Collections.emptyList());
//		allowList.addAll(Optional.ofNullable(corsInfo.getAppUrls()).orElse(Collections.emptyList()));
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(allowList);
//        config.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setExposedHeaders(Arrays.asList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }

}
