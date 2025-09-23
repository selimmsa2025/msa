package kr.go.iop.ci.sc.cmmn.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

/**
 * 마이크로서비스명 판단
 *
 * @author MSA팀
 * @version 1.0
 * @since 2024.07.10
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2024.07.10    양정숙        최초 생성
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(value="spring.application.name")
public @interface MicroService {

	@AliasFor(annotation=ConditionalOnProperty.class, attribute="havingValue")
	String value() default "";
}
