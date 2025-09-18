package kr.go.iop.ci.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 기본게시판 서비스
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023/11/24
 *
 *        <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2023.11.24    양정숙        최초 생성
 *        </pre>
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
