package kr.go.iop.ci.sc.cmmn.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * ServiceImpl에 트랜잭셔널 처리
 *
 * @author MSA팀
 * @version 1.0
 * @since 2023.12.07
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2023.12.07    양정숙        최초 생성
 * </pre>
 */
@RequiredArgsConstructor
@Configuration
public class TransactionalConfig {

	private final PlatformTransactionManager transactionManager;
	private static final int TX_METHOD_TIMEOUT = 30;
	private static final String AOP_POINTCUT_EXPRESSION = "execution(* kr.go..service.impl.*ServiceImpl.*(..))";
	
	@Bean
	public TransactionInterceptor txAdvice() {
		TransactionInterceptor txAdvice = new TransactionInterceptor();
		Properties txAttributes = new Properties();
		List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
		rollbackRules.add(new RollbackRuleAttribute(Exception.class));

		DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(
				TransactionDefinition.PROPAGATION_REQUIRED);
		readOnlyAttribute.setReadOnly(true);
		readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);

		RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(
				TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
		writeAttribute.setTimeout(TX_METHOD_TIMEOUT);

		String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
		String writeTransactionAttributesDefinition = writeAttribute.toString();

		txAttributes.setProperty("get*", readOnlyTransactionAttributesDefinition);
		txAttributes.setProperty("*", writeTransactionAttributesDefinition);

		txAdvice.setTransactionAttributes(txAttributes);
		txAdvice.setTransactionManager(transactionManager);

		return txAdvice;
	}

	@Bean
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}
}
