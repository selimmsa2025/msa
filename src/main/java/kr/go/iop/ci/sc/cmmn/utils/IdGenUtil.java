package kr.go.iop.ci.sc.cmmn.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * ID 생성 유틸 클래스
 *
 * @author 세림_이너
 * @version 1.0
 * @since 2024. 8. 26.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2024. 8. 26.  PJH        최초 생성
 * </pre>
 */
@Component
public class IdGenUtil {
    private IdGenUtil(){
        
    }
    

    /**
     * UUID 방식의 id 생성
     * @return
     */
    public static String getUUID(){    
        UUID guid = UUID.randomUUID();
        return StringUtils.replace(guid.toString(), "-", "");
    }
    
    /**
     * UUID 방식의 id 생성 + prifix 추가
     * @param prifix
     * @return
     */
    public static String getUUID(String prifix){
        return (prifix + getUUID());
    }
}
