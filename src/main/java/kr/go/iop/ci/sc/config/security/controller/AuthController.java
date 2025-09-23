package kr.go.iop.ci.sc.config.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.go.iop.ci.sc.config.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

//    @Autowired
//    private JwtService jwtService;
//
//    // Access Token + Refresh Token 발급
//    @PostMapping("/token")
//    public ResponseEntity<Map<String, Object>> generateTokens(@RequestBody Map<String, String> request) {
//    	
//    	// 토큰 파라미터가 없는 경우
//        if (request == null || request.isEmpty()) {
//            return ResponseEntity.status(400).body(Map.of(
//                "error", "Token parameter is required",
//                "message", "Please include clientId and aplyKey in the request body"
//            ));
//        }
//    	
//    	String clientId = request.get("clientId");
//        String aplyKey = request.get("aplyKey");
//    	
//        try {
//        	
//            Map<String, String> tokens = jwtService.generateTokenPair(clientId, aplyKey);
//            
//            Map<String, Object> response = new HashMap<>();
//            response.put("access_token", tokens.get("access_token"));
//            response.put("refresh_token", tokens.get("refresh_token"));
//            response.put("token_type", "Bearer");
//            response.put("access_token_expires_in", 600); // 1시간  3600  10분 600
//            response.put("refresh_token_expires_in", 2592000); // 30일
//            
//            return ResponseEntity.ok(response);
//            
//        } catch (Exception e) {
//            // JWT 발급 또는 DB 저장 중 에러 발생 시
//            return ResponseEntity.status(401).body(Map.of("error", "DB error"));
//        }
//    }
//
//    // Refresh Token으로 새로운 Access Token 발급
//    @PostMapping("/refresh")
//    public ResponseEntity<Map<String, Object>> refreshAccessToken(@RequestBody Map<String, String> requestBody) {
//        String refreshToken = requestBody.get("refreshToken");
//        // Refresh Token 파라미터가 없는 경우
//        if (refreshToken == null || refreshToken.trim().isEmpty()) {
//            return ResponseEntity.status(400).body(Map.of(
//                "error", "Refresh token parameter is required",
//                "message", "Please provide a refresh token parameter: /auth/refresh?refreshToken=your_refresh_token_here"
//            ));
//        }
//
//        // Refresh Token 유효성 검사
//        if (!jwtService.validateToken(refreshToken)) {
//            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
//        }
//
//        // Refresh Token이 만료되었는지 확인
//        if (jwtService.isTokenExpired(refreshToken)) {
//            return ResponseEntity.status(401).body(Map.of("error", "Refresh token expired"));
//        }
//
//        // Refresh Token에서 clientId 추출
//        Map<String, String> tMap = jwtService.getClientIdFromToken(refreshToken);
//        String clientId = tMap.get("clientId");
//        String aplyId = tMap.get("aplyId");
//
//        // 새로운 Access Token 생성
//        String newAccessToken = jwtService.generateAccessToken(clientId, aplyId);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("access_token", newAccessToken);
//        response.put("token_type", "Bearer");
//        response.put("expires_in", 600);
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 토큰 검증
//    @GetMapping("/verify")
//    public ResponseEntity<Map<String, Object>> verifyToken(@RequestParam(required = false) String token) {
//        // 토큰 파라미터가 없는 경우
//        if (token == null || token.trim().isEmpty()) {
//            return ResponseEntity.status(400).body(Map.of(
//                "error", "Token parameter is required",
//                "message", "Please provide a token parameter: /auth/verify?token=your_token_here"
//            ));
//        }
//        
//        Map<String, String> tMap = jwtService.getClientIdFromToken(token);
//        String rtnClientId = tMap.get("clientId");
//
//        boolean isValid = jwtService.validateToken(token); 
//        boolean isExpired = jwtService.isTokenExpired(token);
//        String clientId = isValid ? rtnClientId : null;
//        long remainingTime = isValid ? jwtService.getRemainingTimeMs(token) : -1;
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("valid", isValid);
//        response.put("expired", isExpired);
//        response.put("clientId", clientId);
//        response.put("remaining_time_ms", remainingTime);
//        response.put("remaining_time_minutes", remainingTime > 0 ? remainingTime / 60000 : 0);
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 토큰 정보 조회
//    @GetMapping("/token-info")
//    public ResponseEntity<Map<String, Object>> getTokenInfo(@RequestParam(required = false) String token) {
//        // 토큰 파라미터가 없는 경우
//        if (token == null || token.trim().isEmpty()) {
//            return ResponseEntity.status(400).body(Map.of(
//                "error", "Token parameter is required",
//                "message", "Please provide a token parameter: /auth/token-info?token=your_token_here"
//            ));
//        }
//
//        try {
//            // 토큰 유효성 검사
//            if (!jwtService.validateToken(token)) {
//                return ResponseEntity.status(400).body(Map.of(
//                    "error", "Invalid token format",
//                    "message", "The provided token is not a valid JWT token"
//                ));
//            }
//            
//            Map<String, String> tMap = jwtService.getClientIdFromToken(token);
//            String rtnClientId = tMap.get("clientId");
//            
//            String clientId = rtnClientId;
//            boolean isExpired = jwtService.isTokenExpired(token);
//            long remainingTime = jwtService.getRemainingTimeMs(token);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("clientId", clientId);
//            response.put("expired", isExpired);
//            response.put("remaining_time_ms", remainingTime);
//            response.put("remaining_time_minutes", remainingTime > 0 ? remainingTime / 60000 : 0);
//            response.put("token_type", isExpired ? "Expired" : "Valid");
//            response.put("token_length", token.length());
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(Map.of(
//                "error", "Token processing failed",
//                "message", e.getMessage()
//            ));
//        }
//    }
}