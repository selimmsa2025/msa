/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.feignapi;

import java.text.ParseException;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.go.iop.ci.sc.cmmn.config.feignconfig.StandardApiConfig;
import kr.go.iop.ci.sc.cp.cpm.svc.vo.SubscrSVO;
import kr.go.iop.ci.sc.feignapi.fallback.IopToSaaSClientFallbackFactory;

/**
 * 구독 관련 기능을(외부연계) 이용하기 위한 feignclient.
 * 
 * @name_ko 구독 관련 기능을(외부연계) feignclient.
 * @author selim
 */
@FeignClient(name = "IopToSaaSClient", url = "${svc-url.iop.backend.tosaas}", configuration = StandardApiConfig.class, fallbackFactory = IopToSaaSClientFallbackFactory.class)
public interface IopToSaaSClient {

	/**
	 * iop-to-saas 구독요청
	 * 
	 * @param subscrSVO
	 * @return 
	 * @throws ParseException
	 */
	@PostMapping(value = "/catalog/v1/saas/prod/create-subscr")
	public Map<String,Object> createProdSubscrReq(@RequestBody SubscrSVO subscrSVO);

	/**
	 * iop-to-saas 구독취소요청
	 * 
	 * @param subscrSVO
	 * @return 
	 * @throws ParseException
	 */
	@PostMapping(value = "/catalog/v1/saas/prod/cancel-subscr")
	public Map<String,Object> createProdSubscrCancelReq(@RequestBody SubscrSVO subscrSVO);
}
