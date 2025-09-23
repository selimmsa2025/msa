package kr.go.iop.ci.sc.cmmn.config.feignconfig;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Feign Client 환경 구성 : feign client 작성할 패키지 위치 정의
 *
 * @author MSA팀
 * @version 1.0
 * @since 2024.07.19
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2024.07.19    양정숙        최초 생성
 * </pre>
 */
@Configuration
@EnableFeignClients(basePackages = {
		"kr.go.iop.ci.sc.feignapi",
		"kr.go.iop.ci.sc.feignapi.fallback" 
	})
public class OpenFeignConfig {


}
