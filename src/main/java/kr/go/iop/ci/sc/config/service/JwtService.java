package kr.go.iop.ci.sc.config.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.go.iop.ci.sc.cm.pc.svc.ProdCertService;
import kr.go.iop.ci.sc.cm.pc.svc.impl.vo.ApiCertKeyReqSVO;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private final ProdCertService apiCertKeyService;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.access-expiration:600000}") long accessTokenExpirationMs,
                      @Value("${jwt.refresh-expiration:2592000000}") long refreshTokenExpirationMs,
                      ProdCertService apiCertKeyService) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationMs = accessTokenExpirationMs; // 1시간
        this.refreshTokenExpirationMs = refreshTokenExpirationMs; // 30일
		this.apiCertKeyService = apiCertKeyService;
    }

    // Access Token 생성
    public String generateAccessToken(String clientId, String aplyKey) {
        return Jwts.builder()
                .setSubject(clientId)
                .claim("clientId", clientId)
                .claim("aplyKey", aplyKey)
                .claim("token_type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String clientId, String aplyKey) {
        return Jwts.builder()
                .setSubject(clientId)
                .claim("clientId", clientId)
                .claim("aplyKey", aplyKey)
                .claim("token_type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Access Token + Refresh Token 쌍으로 생성
    public Map<String, String> generateTokenPair(String clientId, String aplyKey) {
    	
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", generateAccessToken(clientId, aplyKey));
        tokens.put("refresh_token", generateRefreshToken(clientId, aplyKey));
        
        try {
        	
        	Map<String, String> params = new HashMap<>();
            params.put("clientId", clientId);
            params.put("aplyKey", aplyKey);
            params.put("access_token", tokens.get("access_token"));
            params.put("refresh_token", tokens.get("refresh_token"));
            
            insertTokenData(params);
            
        }catch (Exception e) {
        	throw new RuntimeException("토큰 생성 및 DB 저장 실패", e);
        }
        
        return tokens;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 토큰 만료는 별도 처리 Filter에서 처리
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 클라이언트 ID 추출 (만료된 토큰도 처리)
//    public String getClientIdFromToken(String token) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        } catch (ExpiredJwtException e) {
//            // 만료된 토큰에서도 clientId 추출 가능
//            return e.getClaims().getSubject();
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid token: getClientIdFromToken");
//        }
//    }
      public Map<String, String> getClientIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Map.of(
                    "clientId", claims.getSubject(),
                    "aplyKey", claims.get("aplyKey", String.class)
            );
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return Map.of(
                    "clientId", claims.getSubject(),
                    "aplyKey", claims.get("aplyKey", String.class)
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: getClientIdAndAplyKey");
        }
    }
    

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Invalid token expired");
        }
    }

    // 토큰 남은 시간 확인 (밀리초)
    public long getRemainingTimeMs(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.getTime() - System.currentTimeMillis();
        } catch (ExpiredJwtException e) {
            return -1; // 이미 만료됨
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: getRemainingTimeMs");
        }
    }

    // 토큰 타입 추출
    public String getTokenType(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.get("token_type", String.class);
        } catch (Exception e) {
            return null;
        }
    }
    
    //DB insert 
    public int insertTokenData(Map<String, String> params) {
    	
    	ApiCertKeyReqSVO vo = new ApiCertKeyReqSVO();
//    	vo.setGdsId(params.get("clientId"));
    	vo.setAplyKey(params.get("aplyKey"));
    	vo.setAplySrctKey(params.get("aplyKey"));
    	vo.setSrvrSeCd(params.get("aplyKey").startsWith("dev") ? "B0020001" : "B0020002"); //개발서버, 운영서버
    	vo.setJsonWebTokenKey(params.get("access_token"));
    	vo.setCertKey(params.get("refresh_token"));
    	vo.setCertPrgrsStpCd("A0050002"); //발급
    	vo.setApiCommScsYn("Y");
    	vo.setFrstCrtPrcrId("SYSTEM_ADMIN");
    	vo.setLastChgPrcrId("SYSTEM_ADMIN");
    	
    	apiCertKeyService.createJwtKey(vo);
    	
    	return 0;
    }
}