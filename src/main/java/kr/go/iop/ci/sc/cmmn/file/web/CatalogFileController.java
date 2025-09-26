/*
 * Copyright (c) 2025 Intelligent On-nara BPS Platform
 * All Rights Reserved. Confidential.
 * 
 * All information including the intellectual and technical concepts contained herein is, 
 * and remains the property of Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 * Unauthorized use, dissemination, or reproduction of this material is strictly forbidden 
 * useless prior written permission is obtained from Ministry of the Interior and Safety & SAMSUNG SDS Consortium.
 */
package kr.go.iop.ci.sc.cmmn.file.web;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.go.iop.ci.sc.cmmn.file.mapper.vo.CatalogFileDVO;
import kr.go.iop.ci.sc.cmmn.file.svc.CatalogFileService;
import kr.go.iop.ci.sc.cmmn.file.svc.vo.CatalogFileSVO;
import kr.go.iop.ci.sc.cmmn.utils.CurrentUserUtils;
import kr.go.iop.ci.sc.cmmn.utils.ResponseUtils;
import kr.go.iop.ci.sc.cmmn.vo.ApiResponseVO;
import kr.go.iop.ci.sc.cmmn.vo.UserVO;
import kr.go.iop.ci.sc.config.info.FileInfo;
import lombok.extern.slf4j.Slf4j;
/**
 * 첨부파일 처리를 위한 Controller.
 * @name_ko 첨부파일 컨트롤러
 * @author lsh
 */
@Slf4j
@RestController
@Tag(name = "첨부파일", description = "첨부파일 관련 API")
public class CatalogFileController {

    private final CatalogFileService catalogFileService;
    private final FileInfo fileInfo;

    public CatalogFileController(CatalogFileService catalogFileService, FileInfo fileInfo) {
        this.catalogFileService = catalogFileService;
        this.fileInfo = fileInfo;
    }

    /**
     * 파일 업로드
     * @param fileSVO 파일 SVO
     * @return
     */
    @PostMapping("/v1/cmmn/file/create")
    public ApiResponseVO createFile(@ModelAttribute CatalogFileSVO fileSVO) {
        log.info("##### createFile1 호출");
        fileSVO = this.setUser(fileSVO);
        log.info("##### createFile2 호출");
        List<MultipartFile> files = fileSVO.getUploadFile();
        log.info("##### createFile3 호출");

        if (files == null || files.isEmpty()) {
            return ResponseUtils.build("파일이 비어있습니다.");
        }

        //TOBE 추후 변경될 가능성있음(컬럼 데이터타입 꼭 확인) : FILE_시퀀스
//        if (fileSVO.getAtchFileGroupNo() == null || fileSVO.getAtchFileGroupNo().isEmpty()) {
//            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHH"));
//            String random = String.format("%04d", (int) (Math.random() * 10000));
//            String groupNo = dateTime + random;
//            fileSVO.setAtchFileGroupNo(groupNo);
//        }
        if (fileSVO.getAtchFileGroupNo() == null || fileSVO.getAtchFileGroupNo().isEmpty()) {
        String atchFileGroupNo = catalogFileService.selectMaxFileGroupSn();
        fileSVO.setAtchFileGroupNo(atchFileGroupNo);
        }
        log.info("##### createFile4 호출");
        
        Long maxFileSn = catalogFileService.selectMaxFileSn(fileSVO);

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            fileSVO.setAtchFileSn((maxFileSn != null ? maxFileSn : 0)+i+1);
            try {
                if (file.isEmpty()) continue;

                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || !originalFileName.contains(".")) {
                    return ResponseUtils.build("유효하지 않은 파일 이름입니다.");
                }

                // 확장자 체크
                String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();
                List<String> allowedExtensions = List.of(fileInfo.getAllowedFileExt().toLowerCase().split("\\|"));
                if (!allowedExtensions.contains(extension)) {
                    return ResponseUtils.build("허용되지 않은 파일 확장자입니다.");
                }

                // 용량 체크
                long fileSize = file.getSize();
                long maxSize = Long.parseLong(fileInfo.getUploadSizeLimit());
                if (fileSize > maxSize) {
                    return ResponseUtils.build("파일 용량이 제한(" + maxSize + " byte)을 초과했습니다.");
                }

                // 저장 경로 생성
                LocalDate now = LocalDate.now();
                String datePath = now.format(DateTimeFormatter.ofPattern("yyyy"+File.separator+"MM"+File.separator+"dd"));
                String savedFileName = UUID.randomUUID() + "." + extension;

                String servicePath;
                    servicePath = "ci"+File.separator+"pm";

                Path fullSavePath = FileSystems.getDefault().getPath(
                        fileInfo.getUploadBasePath(),
                        fileInfo.getUploadFilePath(),
                        fileInfo.getSystemFilePath(),
                        servicePath,
                        datePath
                ).normalize();
                Files.createDirectories(fullSavePath);

                // 파일 저장
                Path dest = fullSavePath.resolve(savedFileName);
                try (FileOutputStream fos = new FileOutputStream(dest.toFile())) {
                    fos.write(file.getBytes());
                }

                // DB 저장용 경로
                String atchFilePathNm = fileInfo.getUploadFilePath() + fileInfo.getSystemFilePath()
                        + (servicePath.isEmpty() ? "" : File.separator + servicePath) + File.separator + datePath;

                fileSVO.setOrgnlFileNm(originalFileName);
                fileSVO.setAtchFileNm(savedFileName);
                fileSVO.setAtchFileCn("첨부파일");
                fileSVO.setExtnNm(extension);
                fileSVO.setFileSz(fileSize);
                fileSVO.setAtchFilePathNm(atchFilePathNm);
//                fileSVO.setFrstCrtPrcrId(fileSVO.getFrstCrtPrcrId());
//                fileSVO.setLastChgPrcrId(fileSVO.getFrstCrtPrcrId());
                fileSVO.setUseYn("Y");
                System.out.println("#######"+fileSVO.getFrstCrtPrcrId());

                int result = catalogFileService.insertFile(fileSVO);

                if (result > 0) {
                    resultList.add(Map.of(
                            "atchFileSn", fileSVO.getAtchFileSn(),
                            "originalFileName", originalFileName
                    ));
                }

            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
                return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("message", "파일 업로드 중 오류 발생"));
            } catch (Exception e) {
                log.error("알 수 없는 오류", e);
                return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("message", "시스템 오류 발생"));
            }
        }

        return ResponseUtils.build(HttpStatus.OK, Map.of(
                "message", "파일 등록 성공",
                "files", resultList,
                "atchFileGroupNo", fileSVO.getAtchFileGroupNo()
        ));
    }


    /**
     * 파일 조회
     * @param fileSVO 파일 SVO
     * @return
     */
    @PostMapping("/v1/cmmn/file/list")
    @Operation(summary = "첨부파일 조회", description = "첨부파일 조회")
    public ApiResponseVO getFileList(@RequestBody CatalogFileSVO fileSVO) {
        log.debug("##### getFileList 호출");
        List<CatalogFileDVO> fileList = catalogFileService.selectFileList(fileSVO);
        return ResponseUtils.build(HttpStatus.OK, fileList);
    }
    
    /**
     * 파일 삭제
     * @param fileSVO 파일 SVO
     * @return
     */
    @PostMapping("/v1/cmmn/file/delete")
    public ApiResponseVO deleteFile(@RequestBody CatalogFileSVO fileSVO) {

        try {
        	fileSVO = this.setUser(fileSVO);
            // DB에서 파일 정보 조회
            CatalogFileDVO fileDetail = catalogFileService.selectFileInfo(fileSVO);
            if (fileDetail == null) {
                return ResponseUtils.build(HttpStatus.NOT_FOUND, Map.of("message", "파일 정보를 찾을 수 없습니다."));
            }

            // 실제 경로 계산
            Path filePath = FileSystems.getDefault().getPath(
                    fileInfo.getUploadBasePath(),
                    fileDetail.getAtchFilePathNm(),
                    fileDetail.getAtchFileNm()
            ).normalize();

            // 물리적 파일 삭제
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.error("파일 시스템 삭제 실패: {}", filePath, e);
                return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("message", "파일 시스템에서 삭제 실패"));
            }

            // DB use_yn='N' 업데이트
            int deleteResult = catalogFileService.deleteFile(fileSVO);

            if (deleteResult > 0) {
                return ResponseUtils.build(HttpStatus.OK, Map.of("message", "파일 삭제 성공"));
            } else {
                return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("message", "DB 삭제 실패"));
            }

        } catch (Exception e) {
            log.error("파일 삭제 처리 중 오류", e);
            return ResponseUtils.build(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("message", "파일 삭제 중 오류 발생"));
        }
    }


    /**
     * 파일 다운로드
     * @param atchFileSn
     * @return
     */
    @GetMapping("/v1/cmmn/file/dwnld/{atchFileGroupNo}/{atchFileSn}")
    @Operation(summary = "첨부파일 다운로드", description = "첨부파일 다운로드")
    public ResponseEntity<?> dwnldFile(@PathVariable String atchFileGroupNo,
    								   @PathVariable Long atchFileSn) {
        log.debug("##### dwnldFile 호출, atchFileSn={}", atchFileSn);

        try {
            CatalogFileSVO fileSVO = new CatalogFileSVO();
            fileSVO.setAtchFileSn(atchFileSn);
            fileSVO.setAtchFileGroupNo(atchFileGroupNo);

            CatalogFileDVO fileDVO = catalogFileService.selectFileInfo(fileSVO);

            if (fileDVO == null) {
                log.warn("파일 정보 조회 실패 - atchFileSn: {}", atchFileSn);
                return ResponseEntity.notFound().build();
            }

            Path filePath = FileSystems.getDefault().getPath(
                    fileInfo.getUploadBasePath(),
                    fileDVO.getAtchFilePathNm(),
                    fileDVO.getAtchFileNm()
            ).normalize();

            if (!Files.exists(filePath)) {
                log.warn("서버에 파일이 존재하지 않음: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                log.error("파일 리소스를 읽을 수 없음: {}", filePath);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            String encodedFileName = URLEncoder.encode(fileDVO.getOrgnlFileNm(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            log.error("파일 다운로드 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 다운로드 실패");
        } catch (Exception e) {
            log.error("알 수 없는 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시스템 오류 발생");
        }
    }
    
    /**
     * 썸네일 출력
     * @param atchFileGroupNo
     * @return
     */
    @GetMapping("/v1/cmmn/file/view/{atchFileGroupNo}")
    @Operation(summary = "썸네일 출력", description = "썸네일 출력")
    public ResponseEntity<?> viewFile(@PathVariable String atchFileGroupNo) {
        CatalogFileSVO fileSVO = new CatalogFileSVO();
        fileSVO.setAtchFileGroupNo(atchFileGroupNo);
        CatalogFileDVO fileDVO = catalogFileService.selectFileInfo(fileSVO);

        if (fileDVO == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = FileSystems.getDefault().getPath(
            fileInfo.getUploadBasePath(),
            fileDVO.getAtchFilePathNm(),
            fileDVO.getAtchFileNm()
        ).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String mimeType = Files.probeContentType(filePath);
            
            // 한글 파일명 처리
            String encodedFilename = URLEncoder.encode(fileDVO.getOrgnlFileNm(), StandardCharsets.UTF_8)
                                               .replaceAll("\\+", "%20");

            String contentDisposition = "inline; filename=\"" +
                new String(fileDVO.getOrgnlFileNm().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) +
                "\"; filename*=UTF-8''" + encodedFilename;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        // 호출 예시 : <img src={`http://localhost:8000/saas-be-catalog/v1/file/${item.atchFileSn}/view`} alt="썸네일" />
    }
    /**
     * 이미지 단건 출력
     * @param atchFileGroupNo, atchFileSn
     * @return
     */
    @GetMapping("/v1/cmmn/file/view/{atchFileGroupNo}/{atchFileSn}")
    @Operation(summary = "이미지 단건 출력", description = "이미지 단건 출력")
    public ResponseEntity<?> viewFileList(@PathVariable String atchFileGroupNo
    								, @PathVariable long atchFileSn) {
        CatalogFileSVO fileSVO = new CatalogFileSVO();
        fileSVO.setAtchFileGroupNo(atchFileGroupNo);
        fileSVO.setAtchFileSn(atchFileSn);
        CatalogFileDVO fileDVO = catalogFileService.selectFileInfo(fileSVO);

        if (fileDVO == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = FileSystems.getDefault().getPath(
            fileInfo.getUploadBasePath(),
            fileDVO.getAtchFilePathNm(),
            fileDVO.getAtchFileNm()
        ).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String mimeType = Files.probeContentType(filePath);
            
            // 한글 파일명 처리
            String encodedFilename = URLEncoder.encode(fileDVO.getOrgnlFileNm(), StandardCharsets.UTF_8)
                                               .replaceAll("\\+", "%20");

            String contentDisposition = "inline; filename=\"" +
                new String(fileDVO.getOrgnlFileNm().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) +
                "\"; filename*=UTF-8''" + encodedFilename;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
    /**
     * 이미지리스트출력
     * @param atchFileGroupNo
     * @return
     */
    
    @GetMapping("/v1/cmmn/file/list-img/{atchFileGroupNo}")
    @Operation(summary = "이미지리스트 출력", description = "이미지리스트 출력")
    public ResponseEntity<?> viewGroupImgs(@PathVariable String atchFileGroupNo) {
        CatalogFileSVO fileSVO = new CatalogFileSVO();
        fileSVO.setAtchFileGroupNo(atchFileGroupNo);
        List<CatalogFileDVO> fileList = catalogFileService.selectFileList(fileSVO);

        if (fileList == null || fileList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            List<BufferedImage> images = new ArrayList<>();
            for (CatalogFileDVO fileDVO : fileList) {
                Path filePath = FileSystems.getDefault().getPath(
                    fileInfo.getUploadBasePath(),
                    fileDVO.getAtchFilePathNm(),
                    fileDVO.getAtchFileNm()
                ).normalize();
                images.add(ImageIO.read(filePath.toFile()));
            }

            int width = images.stream().mapToInt(BufferedImage::getWidth).max().orElse(0);
            int height = images.stream().mapToInt(BufferedImage::getHeight).sum();

            BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = combined.getGraphics();

            int y = 0;
            for (BufferedImage img : images) {
                g.drawImage(img, 0, y, null);
                y += img.getHeight();
            }
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(combined, "png", baos);
            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    /**
     * 파일 상세조회
     * @param fileSVO 파일 SVO
     * @return
     */
    @PostMapping("/v1/cmmn/file/info")
    @Operation(summary = "첨부파일 상세조회", description = "첨부파일 상세조회")
    public ApiResponseVO getFileInfo(@RequestBody CatalogFileSVO fileSVO) {
        log.debug("getFileInfo");
        CatalogFileDVO fileDVO = catalogFileService.selectFileInfo(fileSVO);
        return ResponseUtils.build(fileDVO);
    }
    
    private CatalogFileSVO setUser(@RequestBody CatalogFileSVO fileVO) {
      
//		UserVO userVO = CurrentUserUtils.getCurrentUser2();
//
//		fileVO.setFrstCrtPrcrId(userVO.getAccountId());
//		fileVO.setLastChgPrcrId(userVO.getAccountId());
		
		//추후삭제
		fileVO.setFrstCrtPrcrId("sysadmin");
		fileVO.setLastChgPrcrId("sysadmin");
		
      return fileVO;
  }

    
}