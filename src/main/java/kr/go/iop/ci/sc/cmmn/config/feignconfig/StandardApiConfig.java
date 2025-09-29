package kr.go.iop.ci.sc.cmmn.config.feignconfig;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import kr.go.iop.ci.sc.cmmn.exception.ApiBizException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign Client 환경 구성
 * 정책연구 표준 응답 형식 API를 처리한다.
 * - 정책연구 표준 반환포맷
 *   status
 *   resultCd
 *   resultMsg
 *   resultData
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
//@Configuration => Bean 등록되면 의도하지 않은 API까지 처리되므로 Configuration 쓰면 안됨
@Slf4j
public class StandardApiConfig {

	/**
	 * API 호출 성공 시 반환값 처리
	 * API 표준 응답 형식 중 resultData만 EgovMap 형식으로 리턴
	 * @param converters
	 * @param customizer
	 * @return
	 */
	@Bean
	public Decoder successDecoder(ObjectFactory<HttpMessageConverters> converters, ObjectProvider<HttpMessageConverterCustomizer> customizer) {
		return new ApiSuccessDecoder(converters, customizer);
	}

	public static class ApiSuccessDecoder implements Decoder {
		private final Decoder decoder;

		public ApiSuccessDecoder(ObjectFactory<HttpMessageConverters> converters, ObjectProvider<HttpMessageConverterCustomizer> customizer) {
			decoder = new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(converters, customizer)));
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object decode(Response response, Type type) {
			try {
				Class<?> returnType = Object.class;
				ResolvableType resolvType = ResolvableType.forClassWithGenerics(ResponseVO.class, returnType);
				Object resultData = ((ResponseVO<?>) decoder.decode(response, resolvType.getType())).getResultData();

				log.info("resultObject[{}]", resultData);

				if (resultData instanceof Map) {
					HashMap<String, Object> resultMap = new HashMap<>();
					resultMap.put("resultData", resultData);
					return resultMap;
					
				} else {
					HashMap<String, Object> resultMap = new HashMap<>();
					resultMap.put("result", resultData);
					return resultMap;
				}

			} catch (IOException e) {
				try {
					log.info("IOException", e);
					return decoder.decode(response, type);
					
				} catch (FeignException | IOException e1) {
					log.info("FeignException | IOException", e1);
					return new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다.");
				}
			}
		}
	}

	@Getter
	@Setter
	public static class ResponseVO<T> {
		private String status;
		private String resultCd;
		private String resultMsg;
		private T resultData;
	}

	/**
	 * 오류 처리
	 */
	@SuppressWarnings("unchecked")
	@Bean
	public ErrorDecoder errorDecoder() {
		return(methodKey, response) -> {
			try {
				HashMap<String, Object> resultMap = new ObjectMapper().readValue(response.body().asInputStream(), HashMap.class);
				return new ApiBizException(HttpStatus.BAD_REQUEST, (String) resultMap.get("resultMsg"));
			
			} catch (IOException e) {
				log.info("IOException", e);
				return new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다.");
			}
		};
	}

}
