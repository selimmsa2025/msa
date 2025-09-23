package kr.go.iop.ci.sc.config.info;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * CORS URL List 가져오기
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.11.13
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2024.12.04    양정숙        최초 생성
 * </pre>
 */
@Configuration
@ConfigurationProperties("cors")
@Getter
@Setter
public class CorsInfo {
	@Schema(description = "공통 허용 URL")
	private List<String> cmmnUrls;
	@Schema(description = "서비스 개별 허옹 URL")
	private List<String> appUrls;
}
