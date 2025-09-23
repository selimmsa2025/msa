package kr.go.iop.ci.sc.cmmn.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * WebClient API 호출 설정
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
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

	/**
	 * 웹 클라이언트 build
	 * @return
	 */
	@Bean
	public WebClient webClient() {
		//타임아웃 설정 (5초)
		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				.responseTimeout(Duration.ofMillis(5000));

		//build
		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) //인메모리버퍼 증가
				.defaultHeaders(httpHeaders -> httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				//Request Header 로깅 필터
				.filter(
					ExchangeFilterFunction.ofRequestProcessor(
						clientRequest -> {
							log.debug(">>>>>>>>> REQUEST <<<<<<<<<<");
							log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());

							clientRequest.headers().forEach(
								(name, values) -> values.forEach(value -> log.info("{} : {}", name, value))
							);

							return Mono.just(clientRequest);
						}
					)
				)
				//Response Header 로깅 필터
				.filter(
					ExchangeFilterFunction.ofResponseProcessor(
						clientResponse -> {
							log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");

							clientResponse.headers().asHttpHeaders().forEach(
								(name, values) -> values.forEach(value -> log.info("{} {}", name, value))
							);

							return Mono.just(clientResponse);
						}
					)
				)
				.build();
	}
}
