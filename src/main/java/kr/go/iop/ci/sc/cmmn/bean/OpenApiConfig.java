package kr.go.iop.ci.sc.cmmn.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger 호출 설정
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
 * 2023.12.20    양정숙        최초 생성
 * </pre>
 */
@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

	@Value("${system-info:onnara}")
	private String systemInfo;
	
	@Value("${web-url.onnara.frontend.api:localhost:8000}")
	private String onnaraApiUrl;

	@Value("${web-url.onnara-ext.frontend.api:localhost:8200}")
	private String onnaraExtApiUrl;

	@Value("${web-url.prism.frontend.api:localhost:8000}")
	private String prismApiUrl;
	
	@Value("${info.app.name}")
	private String infoAppName;

	@Value("${info.app.description}")
	private String infoAppDescription;

	@Value("${info.app.version}")
	private String infoAppVersion;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${web-url.protocol:http://}")
	private String webProtocol;

	private static String tokenBearer = "Bearer";

	@Profile("local")
	@Bean
	public OpenAPI svcOpenAPILocal() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes(tokenBearer,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(tokenBearer).bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList(tokenBearer))
				.info(new Info().title(infoAppName).description(infoAppDescription).version(infoAppVersion));
	}

	@Profile({ "dev", "ops" })
	@Bean
	public OpenAPI svcOpenAPIKubernetes() {
		List<Server> serverList = new ArrayList<>();

		if(systemInfo.equals("onnara") || systemInfo.equals("onpas")) {
			serverList.add(new Server().url(webProtocol + onnaraApiUrl + "/" + appName));

		} else {
			serverList.add(new Server().url(webProtocol + prismApiUrl + "/" + appName));
		}

		return new OpenAPI()
				// .servers(List.of(new Server().url(webProtocol + apiUrl + "/" + appName)))
				.servers(serverList) // 쿠버네티스 환경에서는 서버 정보를 APIGateway로 표시
				.components(new Components().addSecuritySchemes(tokenBearer,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(tokenBearer).bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList(tokenBearer))
				.info(new Info().title(infoAppName).description(infoAppDescription).version(infoAppVersion));
	}
}
