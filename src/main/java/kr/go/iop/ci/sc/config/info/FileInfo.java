package kr.go.iop.ci.sc.config.info;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties("file")
@Getter
@Setter
public class FileInfo {
	@Schema(description = "허용된 확장자")
	private String allowedFileExt;
	
	@Schema(description = "기본 업로드 경로")
	private String uploadBasePath;
	
	@Schema(description = "업로드 파일 경로")
	private String uploadFilePath;
	
	@Schema(description = "시스템 파일 경로")
	private String systemFilePath;
	
	@Schema(description = "시스템 파일 경로 - 온행정지원")
	private String systemFilePathOnpas;
	
	@Schema(description = "총 파일 사이즈")
	private String uploadTotalSizeLimit;
	
	@Schema(description = "파일 사이즈 제한")
	private String uploadSizeLimit;
}