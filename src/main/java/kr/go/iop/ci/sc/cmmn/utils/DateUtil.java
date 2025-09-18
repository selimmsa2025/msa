package kr.go.iop.ci.sc.cmmn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Date 생성 유틸 클래스
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
public class DateUtil {
    private DateUtil(){
        
    }

    private static final String LONGFORMAT  = "yyyyMMddHHmmss";
    
    public static String getDateString(Date date, String format) {
        if(date != null){ 
            format = StringUtils.replace(format, "Y", "y");
            format = StringUtils.replace(format, "D", "d");
            format = StringUtils.replace(format, "A", "a");
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.format(date);                
        } else {
            return "";
        }
    }
    
    /**
     * 
     * @Method Name : getNowDate
     * @Description : 서버 기준 시간 반환
     * <pre>
     * </pre>
     * @return
     */
    public static Date getNowDate() {
        Calendar cal = Calendar.getInstance(); 
        return new Date(cal.getTimeInMillis());
    } 
    
    /**
     * 
     * @Method Name : getNowDateString
     * @Description : 서버 기준 시간 반환
     * <pre>
     * </pre>
     * @return
     */
    public static String getNowDateString() {
        Calendar cal  = Calendar.getInstance();
        Date     date = new Date(cal.getTimeInMillis());
        return getDateString(date, LONGFORMAT);
    }
    
    /**
     * 
     * @Method Name : getNowDateString
     * @Description : 서버 기준 시간 반환
     * <pre>
     * </pre>
     * @param format
     * @return
     */
    public static String getNowDateString(String format) {
        Calendar cal  = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        return getDateString(date, format);
    }
    
    /**
     * 
     * @Method Name : getNowDateString
     * @Description : 서버 기준 시간 반환
     * <pre>
     * </pre>
     * @param format
     * @param count
     * @return
     */
    public static String getNowDateString(String format, int count) {
        Calendar cal  = Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        date = new Date(date.getTime()+(1000*60*60*24*count));
        return getDateString(date, format);
    }
    
    /**
     * 날짜계산 - 특정일에 수를 더하여 반환
     * @param date(Date)
     * @param type(Y/M/D/h/m)
     * @param count 
     * @return
     */
    public static Date getAddDate(Date date, String type, int count) {
        if(date == null || StringUtils.isEmpty(type)){
            return date;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);            
            switch(type.charAt(0)){
            case 'Y' : cal.add(Calendar.YEAR,   count);  break;
            case 'M' : cal.add(Calendar.MONTH,  count);  break; 
            case 'D' : cal.add(Calendar.DATE,   count);  break;
            case 'h' : cal.add(Calendar.HOUR,   count);  break;
            case 'm' : cal.add(Calendar.MINUTE, count);  break;
            case 's' : cal.add(Calendar.SECOND, count);  break;
            default  : break; 
            }                 
            return cal.getTime();      
        }
    }
    
    public static Date getStringToDate(String strDate, String format) throws ParseException {
        if(strDate == null || "".equals(strDate)) {
            return null;
        }
        if(format == null || "".equals(format)) {
            format = LONGFORMAT;
        }
    
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(strDate);
    }
    
    /**
     * 현재일자와 비교하여 현재일자 이전일경우 FALSE, 이후일경우 TRUE 반환 
     * @param date(Date)
     * @return
     */
    public static boolean isCompareDate(Date date) {
        return isCompareDate(date, getNowDate());
    }
    
    /**
     * 두날짜를 비교하여 종료일자 이전일경우 FALSE, 이후일경우 TRUE 반환 
     * @param date1(Date)
     * @param date2(Date)
     * @return
     */
    public static boolean isCompareDate(Date date1, Date date2) {
        if (date1 == null || date2 == null){
            return false;
        } else {
            return date2.after(date1);
        }
    }
}