package kr.go.iop.ci.sc.cmmn.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 공통 적용할 Bean 정의
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.12.01
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.12.01    양정숙        최초 생성
 * </pre>
 */
@Configuration
public class CmmnConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
