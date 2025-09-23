package kr.go.iop.ci.sc.cmmn.utils;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import kr.go.iop.ci.sc.cmmn.bean.WebClientConfig;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * API 호출 util
 *
 * @author MSA팀
 * @version 1.0
 * @param <T>
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
@Component
@RequiredArgsConstructor
public class ApiUtils {
	private final WebClientConfig webClientConfig;
	
	/**
	 * blocking 방식으로 API 호출 (Get method)
	 * @param url
	 * @param paramVO
	 * @return
	 */
	public Object get(String url, Object paramVO) {
		Object result = new Object();
		
		try {
			result = this.mono(url, paramVO, HttpMethod.GET).toFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			result = "error";
			
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
		
		return result;
	}
	
	/**
	 * blocking 방식으로 API 호출 (Post method)
	 * @param url
	 * @param paramVO
	 * @return
	 */
	public Object post(String url, Object paramVO) {
		Object result = new Object();
		
		try {
			result = this.mono(url, paramVO, HttpMethod.POST).toFuture().get();
		} catch (InterruptedException | ExecutionException e) {
			result = "error";
			
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
		
		return result;
	}

	/**
	 * non-blocking 방식으로 API 호출 (Get method)
	 * @param url
	 * @param paramVO
	 * @return
	 */
	public Object subscribeGet(String url, Object paramVO) {
		// callback 처리 필요함
		return this.mono(url, paramVO, HttpMethod.GET).subscribe();
	}
	
	/**
	 * non-blocking 방식으로 API 호출 (Post method)
	 * @param url
	 * @param paramVO
	 * @return
	 */
	public Object subscribePost(String url, Object paramVO) {
		// callback 처리 필요함
		return this.mono(url, paramVO, HttpMethod.POST).subscribe();
	}

	/**
	 * WebClient 호출
	 * @param url
	 * @param paramVO
	 * @param method
	 * @return
	 */
	private Mono<Object> mono(String url, Object paramVO, HttpMethod method) {
		if(paramVO == null) {
			HashMap<String, Object> param = new HashMap<>();
			param.put("", "");
			paramVO = param;
		}
		
		return webClientConfig.webClient()
				.method(method)
				.uri(url)
				.bodyValue(paramVO)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, __ -> Mono.error(new IllegalArgumentException("4xx")))
				.onStatus(HttpStatusCode::is5xxServerError, __ -> Mono.error(new IllegalArgumentException("5xx")))
				.bodyToMono(Object.class);
	}
}
