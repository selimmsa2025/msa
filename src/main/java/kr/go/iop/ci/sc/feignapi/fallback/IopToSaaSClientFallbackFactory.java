/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.feignapi.fallback;

import java.util.Map;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrSVO;
import kr.go.iop.ci.sc.feignapi.IopToSaaSClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 구독 관련 기능(외부연계) 오류처리를 위한 fallback.
 * 
 * @name_ko 구독 관련 기능(외부연계) 오류처리를 위한 fallback.
 * @author selim
 */
@Component
@Slf4j
public class IopToSaaSClientFallbackFactory implements FallbackFactory<IopToSaaSClient> {

	@Override
	public IopToSaaSClient create(Throwable cause) {
		log.error("Feign to SaaS failed: {}", cause.toString(), cause);
		return new IopToSaaSClient() {
			public Map<String,Object> createProdSubscrReq(SubscrSVO v) {
				log.info("오류 발생 SysClientFallback getSampleList()");
				return null;
			}

			public Map<String,Object> createProdSubscrCancelReq(SubscrSVO v) {
				log.info("오류 발생 SysClientFallback getSampleList()");
				return null;
			}
		};
	}
}
