package kr.go.iop.ci.sc.cmmn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 엑셀 유틸
 *
 * @author 세림_이너
 * @version 1.0
 * @since 2024. 8. 26.
 *
 *        <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자            수정내용
 * ----------    --------    ---------------------------
 * 2024. 8. 26.  PJH        최초 생성
 *        </pre>
 */
@Slf4j
@Component
public class ExcelUtil {

	private static final String DEFAULT_FONT = "맑은 고딕";
	private static final String CHARSET_UTF8 = "UTF-8";

	private ExcelUtil() {

	}
	
	
	public static void excelFileDownload(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> mapInfo, List<String> titleList, List<String> codeList, List<String[]> dataList) {
		try (ServletOutputStream sOutputStream = response.getOutputStream();
				XSSFWorkbook workbook = new XSSFWorkbook()) {

			// Sheet 생성
			XSSFSheet sheet1 = workbook.createSheet(mapInfo.get("sheetName"));

			// XSSFFont 폰트 설정
			XSSFFont fontExcelTitle = workbook.createFont();
			setXSSFFont(fontExcelTitle, (short) 10, (short) 8, true);

			XSSFFont fontTitle = workbook.createFont();
			setXSSFFont(fontTitle, (short) 10, (short) 9, true);

			XSSFFont fontConts = workbook.createFont();
			setXSSFFont(fontConts, (short) 10, (short) 8, false);

			// Style 설정
			XSSFCellStyle styleExcelTitle = workbook.createCellStyle();
			setXSSFCellStyle(styleExcelTitle, fontExcelTitle, (short) 9, HorizontalAlignment.CENTER);

			XSSFCellStyle styleTitle = workbook.createCellStyle();
			setXSSFCellStyle(styleTitle, fontTitle, (short) 22, HorizontalAlignment.CENTER);

			XSSFCellStyle validTitle = workbook.createCellStyle();
			setXSSFCellStyle(validTitle, fontTitle, (short) 22, HorizontalAlignment.CENTER);

			XSSFCellStyle styleContents = workbook.createCellStyle();
			setXSSFCellStyle(styleContents, fontConts, null, HorizontalAlignment.LEFT);
			styleContents.setWrapText(true); // 줄바꿈 

			// Data Row Cell
			XSSFRow row = null;
			int rowIndex = 101;
			int colIndex = 0;

			row = sheet1.createRow(0);
			row.setHeight((short) 800);

			// excelTitle Setting
			String excelTitle = mapInfo.get("excelTitle");
			boolean excelTitleUse = false;
			if (excelTitle != null && !"".equals(excelTitle)) {
				for (int i = 0; i < titleList.size(); i++) {
					setCellValue(row, i, styleExcelTitle, excelTitle);
					sheet1.setColumnWidth(colIndex++, 5000);
				}
				sheet1.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, titleList.size() - 1));

				row = sheet1.createRow(1);
				row.setHeight((short) 400);

				colIndex = 0;
				excelTitleUse = true;
			}

			// Title Setting
			colIndex = 0;
			for (String title : titleList) {
				setCellValue(row, colIndex, styleTitle, title);
				sheet1.autoSizeColumn(colIndex++);
			}

			// Data Setting
			if (dataList == null) {
				if (excelTitleUse) {
					for (int iRow = 2; iRow < rowIndex; iRow++) {
						row = sheet1.createRow(iRow);
						for (int iCol = 0; iCol < colIndex; iCol++) {
							setCellValue(row, iCol, styleContents, "");
						}
					}
				} else {
					for (int iRow = 1; iRow < rowIndex; iRow++) {
						row = sheet1.createRow(iRow);
						for (int iCol = 0; iCol < colIndex; iCol++) {
							setCellValue(row, iCol, styleContents, "");
						}
					}
				}

				// 콤보설정(데이터유효성)
				if (codeList != null && !codeList.isEmpty()) {
					setExcelValidation(sheet1, codeList, rowIndex);
				}
			} else {
				if (excelTitleUse) {
					rowIndex = 2;
				} else {
					rowIndex = 1;
				}
				for (String[] arrayData : dataList) {
					colIndex = 0;
					row = sheet1.createRow(rowIndex++);
					for (String data : arrayData) {
						setCellValue(row, colIndex++, styleContents, data);
					}
				}
			}

			// 컬럼 크기 마지막에 맞춰주려고 밖으로 따로 뺌
			for (int i = 0; i < titleList.size(); i++) {
//				sheet1.autoSizeColumn(i);
//				sheet1.setColumnWidth(i, (sheet1.getColumnWidth(i)) + 1000);
				sheet1.autoSizeColumn(i, true);
			    int w = sheet1.getColumnWidth(i) + 1000;           // 패딩
			    int max = 255 * 256 - 1;                           // POI 최대 허용
			    sheet1.setColumnWidth(i, Math.min(w, max));        // ★ 한도 내로 캡
			}

			setHeaderFileName(request, response, mapInfo.get("fileName"));

			// excel 파일 저장
			response.setContentType(
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=" + CHARSET_UTF8);
			response.setCharacterEncoding(CHARSET_UTF8);
			response.setHeader("Content-Transfer-Encoding", "binary");

			workbook.write(sOutputStream);
			sOutputStream.flush();
		} catch (IOException e) {
			log.error("[IOException] excelFileDownload ");
		}
	}

	public static void excelFileDownload(HttpServletRequest request, HttpServletResponse response,
			Map<String, String> mapInfo, List<String> apiTitleList, List<String> apiDataList,
			Map<String, List<String[]>> sectionDataMap, List<String> sectionHeader) {
		try (ServletOutputStream sOutputStream = response.getOutputStream();
				XSSFWorkbook workbook = new XSSFWorkbook()) {

			XSSFSheet sheet1 = workbook.createSheet(mapInfo.get("sheetName"));

			XSSFFont fontBold = workbook.createFont();
			setXSSFFont(fontBold, (short) 10, (short) 9, true);

			XSSFFont fontNormal = workbook.createFont();
			setXSSFFont(fontNormal, (short) 10, (short) 8, false);

			XSSFFont fontExcelTitle = workbook.createFont();
			setXSSFFont(fontExcelTitle, (short) 10, (short) 8, true);

			XSSFCellStyle styleTitle = workbook.createCellStyle();
			setXSSFCellStyle(styleTitle, fontBold, (short) 22, HorizontalAlignment.LEFT);

			XSSFCellStyle styleHeader = workbook.createCellStyle();
			setXSSFCellStyle(styleHeader, fontBold, (short) 22, HorizontalAlignment.CENTER);

			XSSFCellStyle styleExcelTitle = workbook.createCellStyle();
			setXSSFCellStyle(styleExcelTitle, fontExcelTitle, (short) 9, HorizontalAlignment.CENTER);

			XSSFCellStyle styleContents = workbook.createCellStyle();
			setXSSFCellStyle(styleContents, fontNormal, null, HorizontalAlignment.LEFT);
			styleContents.setWrapText(true); // 줄바꿈 
			
			XSSFCellStyle styleSectionTitle = setXSSFCellParamStyle(workbook, true, HorizontalAlignment.LEFT);

			int rowIndex = 0;
			XSSFRow row;

			int sectionCols = (sectionHeader != null) ? sectionHeader.size() : 0;
			int maxCols = Math.max(sectionCols, 4);

//			for (int i = 0; i < sectionHeader.size(); i++) {
//				sheet1.autoSizeColumn(i);
//				sheet1.setColumnWidth(i, sheet1.getColumnWidth(i) + 1000);
//			}

			String excelTitle = mapInfo.get("excelTitle");
			if (excelTitle != null && !excelTitle.trim().isEmpty()) {
				// 제목 행 생성 + 높이 크게
				XSSFRow titleRow = sheet1.createRow(rowIndex++);
				titleRow.setHeight((short) 800);

				// 0 ~ (maxCols-1)까지 같은 제목 입력 + 기본 너비 지정
				for (int c = 0; c < maxCols; c++) {
					setCellValue(titleRow, c, styleExcelTitle, excelTitle);
					sheet1.setColumnWidth(c, 5000);
				}

				// 제목 행 전체 병합
				sheet1.addMergedRegion(
						new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, maxCols - 1));
			}

			if (apiTitleList != null && apiDataList != null) {
				for (int i = 0; i < apiTitleList.size(); i++) {
					row = sheet1.createRow(rowIndex++);

					setCellValue(row, 0, styleHeader, apiTitleList.get(i));

					setCellValue(row, 1, styleContents, apiDataList.get(i));

					CellRangeAddress mergeRange = new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 3);
					sheet1.addMergedRegion(mergeRange);

					for (int c = 1; c <= 3; c++) {
						Cell cell = row.getCell(c);
						if (cell == null) {
							cell = row.createCell(c);
						}
						cell.setCellStyle(styleContents);
					}

					RegionUtil.setBorderTop(BorderStyle.THIN, mergeRange, sheet1);
					RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRange, sheet1);
					RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRange, sheet1);
					RegionUtil.setBorderRight(BorderStyle.THIN, mergeRange, sheet1);
				}
				rowIndex++;
			}
			if (sectionDataMap != null) {
				for (Map.Entry<String, List<String[]>> entry : sectionDataMap.entrySet()) {
					String sectionTitle = entry.getKey();
					List<String[]> sectionData = entry.getValue();

					row = sheet1.createRow(rowIndex++);
					setCellValue(row, 0, styleSectionTitle, sectionTitle);
					sheet1.addMergedRegion(
							new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, sectionHeader.size() - 1));

					row = sheet1.createRow(rowIndex++);
					int colIndex = 0;
					for (String header : sectionHeader) {
						setCellValue(row, colIndex++, styleHeader, header);
					}

					for (String[] arr : sectionData) {
						row = sheet1.createRow(rowIndex++);
						colIndex = 0;
						for (String val : arr) {
							setCellValue(row, colIndex++, styleContents, val);
						}
					}

					rowIndex++;
				}
			}
//			for (int i = 0; i < maxCols; i++) {
//				sheet1.autoSizeColumn(i);
//				sheet1.setColumnWidth(i, sheet1.getColumnWidth(i) + 1000);
//			}
	        // ★ 마지막 한 번만 autoSize + 하드 캡(255*256 - 1)
	        for (int i = 0; i < maxCols; i++) {
	            sheet1.autoSizeColumn(i, true);
	            int w = sheet1.getColumnWidth(i) + 1000;  // 여유
	            int max = 255 * 256 - 1;                  // POI 한도
	            sheet1.setColumnWidth(i, Math.min(w, max));
	        }
			
			setHeaderFileName(request, response, mapInfo.get("fileName"));
			response.setContentType(
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=" + CHARSET_UTF8);
			response.setCharacterEncoding(CHARSET_UTF8);
			response.setHeader("Content-Transfer-Encoding", "binary");

			workbook.write(sOutputStream);
			sOutputStream.flush();

		} catch (IOException e) {
			log.error("[IOException] excelFileDownload ", e);
		}
	}

	private static void setXSSFCellStyle(XSSFCellStyle xssfCellStyle, XSSFFont xssfFont, Short fillforegroundColor,
			HorizontalAlignment horizontalAlignment) {
		if (fillforegroundColor != null) {
			xssfCellStyle.setFillForegroundColor(fillforegroundColor);
			xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		xssfCellStyle.setBorderTop(BorderStyle.THIN);// 얇은 테두리[POI 1.10]
		xssfCellStyle.setBorderBottom(BorderStyle.THIN);// 얇은 테두리[POI 1.10]
		xssfCellStyle.setBorderRight(BorderStyle.THIN);// 얇은 테두리[POI 1.10]
		xssfCellStyle.setBorderLeft(BorderStyle.THIN);// 얇은 테두리[POI 1.10]
		xssfCellStyle.setAlignment(horizontalAlignment); // 정렬[POI 1.10]
		xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 정렬 - 세로[POI 1.10]
		xssfCellStyle.setWrapText(true);
		xssfCellStyle.setFont(xssfFont);
	}

	public static XSSFCellStyle setXSSFCellParamStyle(XSSFWorkbook workbook, boolean bold, HorizontalAlignment align) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(align);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		style.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
		style.setFillPattern(FillPatternType.NO_FILL);

		style.setBorderTop(BorderStyle.NONE);
		style.setBorderBottom(BorderStyle.NONE);
		style.setBorderLeft(BorderStyle.NONE);
		style.setBorderRight(BorderStyle.NONE);

		XSSFFont font = workbook.createFont();
		font.setBold(bold);
		style.setFont(font);

		return style;
	}

	private static void setXSSFFont(XSSFFont xssfFont, Short fontHeightInPoints, Short color, boolean isBold) {
		xssfFont.setFontHeightInPoints(fontHeightInPoints);
		xssfFont.setFontName(DEFAULT_FONT);
		xssfFont.setColor(color); // BLACK
		if (isBold) {
			xssfFont.setBold(true); // 굵게[POI 1.10]
		}
	}

	private static XSSFCell setCellValue(XSSFRow row, int index, XSSFCellStyle xssfCellStyle, String value) {
		XSSFCell cell = row.createCell(index);
		cell.setCellStyle(xssfCellStyle);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * 
	 * @Method Name : setHeaderFileName
	 * @Description : 파일명 세팅
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 * @param cell
	 * @return
	 */
	private static void setHeaderFileName(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String header = request.getHeader("User-Agent");

		String dispositionPrefix = "attachment; filename=\"";
		String encodedFilename = "";

		try {
			if (header.contains("MSIE") || header.contains("Trident")) {
				encodedFilename = URLEncoder.encode(fileName, CHARSET_UTF8).replace("+", "%20");
			} else if (header.contains("Opera")) {
				encodedFilename = "\""
						+ new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
			} else if (header.contains("Chrome")) {
				StringBuilder sb = new StringBuilder();
				for (char c : fileName.toCharArray()) {
					if (c > '~') {
						sb.append(URLEncoder.encode("" + c, CHARSET_UTF8));
					} else {
						sb.append(c);
					}
				}
				encodedFilename = sb.toString();
			} else if (header.contains("Safari")) {
				encodedFilename = URLEncoder.encode(fileName, CHARSET_UTF8).replace("+", "%20");
				dispositionPrefix = "attachment; filename*=" + CHARSET_UTF8 + "''";
			} else {
				encodedFilename = "\""
						+ new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
			}

		} catch (UnsupportedEncodingException e) {
			encodedFilename = "\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
					+ "\"";
		}

		response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename + "\"");
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
	}

	/**
	 * 
	 * @Method Name : setExcelValidation
	 * @Description : 엑셀의 셀의 유효성데이터를 설정한다. (콤보박스처리)
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 *              `
	 * @param sheet
	 * @param codeList
	 * @param rowIndex
	 */
	public static void setExcelValidation(XSSFSheet sheet, List<String> codeList, int rowIndex) {
		int colIndex = 0;
		for (String code : codeList) {
			if (!org.apache.commons.lang3.StringUtils.isEmpty(code) && !"NONE".equals(code)) {
				String[] arrCodeValue = org.springframework.util.StringUtils.commaDelimitedListToStringArray(code);
				CellRangeAddressList codeRangeAddress = new CellRangeAddressList(1, rowIndex, colIndex, colIndex); // 적용할
																													// 범위

				XSSFDataValidationHelper dyHelper = new XSSFDataValidationHelper(sheet);
				XSSFDataValidationConstraint codeConstraint = (XSSFDataValidationConstraint) dyHelper
						.createExplicitListConstraint(arrCodeValue);
				XSSFDataValidation codeValidation = (XSSFDataValidation) dyHelper.createValidation(codeConstraint,
						codeRangeAddress);
				sheet.addValidationData(codeValidation);
			}
			colIndex++;
		}
	}

	/**
	 * 
	 * @Method Name : getExcelRowToArray
	 * @Description : 엑셀의 열을 스크링 배열로 변환한다.
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 * @param row
	 * @return
	 */
	public static String[] getExcelRowToArray(HSSFRow row) {
		String[] result = null;
		int check = 0;
		int count = 0;

		if (row != null) {
			count = row.getPhysicalNumberOfCells(); // 7 , 6
			// count = row.getLastCellNum(); // 7 , 6
			result = new String[count];
			for (int c = 0; c < count; c++) {
				result[c] = getExcelCellValue(row.getCell(c)).trim();
				if (!org.apache.commons.lang3.StringUtils.isEmpty(result[c])) {
					check++;
				}
			}
		}

		return check == 0 ? null : result;
	}

	/**
	 * 
	 * @Method Name : getExcelRowToArray
	 * @Description : 엑셀의 열을 스크링 배열로 변환한다.
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 * @param row
	 * @return
	 */
	public static String[] getExcelRowToArrayXSSF(XSSFRow row) {
		String[] result = null;
		int check = 0;
		int count = 0;
		if (row != null) {
			// count = row.getPhysicalNumberOfCells(); // 7 , 6
			count = row.getLastCellNum(); // 7 , 6
			result = new String[count];
			for (int c = 0; c < count; c++) {
				result[c] = getExcelCellValue(row.getCell(c)).trim();
				if (!org.apache.commons.lang3.StringUtils.isEmpty(result[c])) {
					check++;
				}
			}
		}

		return check == 0 ? null : result;
	}

	/**
	 * 
	 * @Method Name : getExcelCellValue
	 * @Description : 셀 값 읽는다.
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 * @param cell
	 * @return
	 */
	public static String getExcelCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			CellType type = cell.getCellType();
			if (CellType.BLANK.name().equals(type.name())) {
				value = "";
			} else if (CellType.STRING.name().equals(type.name())) {
				value = cell.getStringCellValue();
			} else if (CellType.BOOLEAN.name().equals(type.name())) {
				value = "" + cell.getBooleanCellValue();
			} else if (CellType.ERROR.name().equals(type.name())) {
				value = "" + cell.getErrorCellValue();
			} else if (CellType.FORMULA.name().equals(type.name())) {
				value = cell.getCellFormula();
			} else if (CellType.NUMERIC.name().equals(type.name())) {
				value = String.valueOf((int) cell.getNumericCellValue());
			}
		}
		return value;
	}

	/**
	 * 
	 * @Method Name : getExcelDataRead
	 * @Description : 엑셀파일의 정보를 읽어서 저장할 리스트로 만든다
	 * 
	 *              <pre>
	 * 
	 *              </pre>
	 * 
	 * @param data
	 * @param keysList
	 * @param checkMap
	 * @return
	 */
	public static Map<String, String> getExcelDataRead(String[] data, List<String> keysList,
			Map<String, String> checkMap) {
		// 입력한 열정보 맵형태로 리턴
		Map<String, String> resultMap = new HashMap<>();
		int colIndex = 0;
		for (String key : keysList) {
			// 데이터 설정
			if (colIndex >= data.length) {
				break;
			}

			String value = data[colIndex++];
			String keyValue = "";
			if (value == null || "".equals(value)) {
				value = "";
			}
			if (value.indexOf("]") != -1) {
				keyValue = value.substring(1, value.indexOf("]"));
			} else {
				keyValue = value;
			}

			// 데이타 오류체크
			String check = checkMap.get(key);
			if (check != null && !"".equals(check) && check.indexOf("notempty") > -1 && StringUtils.isEmpty(value)) {
				StringBuilder result = new StringBuilder();
				result.append("INPUT_ERROR").append("[").append(key).append("]");
				resultMap.put("resultCode", result.toString());
				return resultMap;
			}

			resultMap.put(key, keyValue);
		}

		return resultMap;
	}

	public static List<Map<String, String>> excelFileUpload(MultipartFile file, List<String> keysList,
			Map<String, String> checkMap) throws IllegalStateException, IOException {
		List<Map<String, String>> dataList = new ArrayList<>();

		// File Upload
		String path = "/data/";
		String fileID = IdGenUtil.getUUID();
		String originalFilename = file.getOriginalFilename();
		String fileExc = org.springframework.util.StringUtils.getFilenameExtension(originalFilename);

		if (!"xls".equalsIgnoreCase(fileExc) && !"xlsx".equalsIgnoreCase(fileExc)) {
			log.error("[excelFileUpload File Extension Error ]");
			return null;
		}

		// 해당 디렉토리가 존재하지 않으면 디렉토리를 생성한다.
		File nFile = new File(path);
		if (!nFile.exists()) {
			nFile.mkdirs();
		}

		String nFileName = fileID.concat(".").concat(fileExc);

		nFile = new File(path.concat(nFileName));

		file.transferTo(nFile);

		String fileName = path.concat(nFileName);

		if ("xls".equalsIgnoreCase(fileExc)) {
			dataList = getHSSFExcelDateList(fileName, keysList, checkMap);
		} else {
			dataList = getXSSFExcelDateList(fileName, keysList, checkMap);
		}

		// 엑셀 업로드 후 엑셀 파일 삭제
		Path paths = Paths.get(path.concat(nFileName));
		Files.delete(paths);

		return dataList;
	}

	private static List<Map<String, String>> getXSSFExcelDateList(String fileName, List<String> keysList,
			Map<String, String> checkMap) throws IOException {
		List<Map<String, String>> dataList = new ArrayList<>();

		try (FileInputStream fInputStream = new FileInputStream(fileName);
				XSSFWorkbook workbook = new XSSFWorkbook(fInputStream);) {

			// Sheet 수 만큼 추출
			int sheetNumber = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++) {
				XSSFSheet sheet1 = workbook.getSheetAt(i);

				// 유효한 열에 대한 정보를 추출하여 필수체크 및 유효성에 따라 맵으로 변환
				for (int index = 1; index < sheet1.getPhysicalNumberOfRows(); index++) {
					String[] arrayData = getExcelRowToArrayXSSF(sheet1.getRow(index));
					if (arrayData != null) {
						dataList.add(getExcelDataRead(arrayData, keysList, checkMap));
					}
				}
			}

		}

		return dataList;
	}

	private static List<Map<String, String>> getHSSFExcelDateList(String fileName, List<String> keysList,
			Map<String, String> checkMap) throws IOException {
		List<Map<String, String>> dataList = new ArrayList<>();

		try (FileInputStream fInputStream = new FileInputStream(fileName);
				POIFSFileSystem poiFile = new POIFSFileSystem(fInputStream);
				HSSFWorkbook workbook = new HSSFWorkbook(poiFile);) {

			// Sheet 수 만큼 추출
			int sheetNumber = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumber; i++) {
				HSSFSheet sheet1 = workbook.getSheetAt(i);

				// 유효한 열에 대한 정보를 추출하여 필수체크 및 유효성에 따라 맵으로 변환
				for (int index = 1; index < sheet1.getPhysicalNumberOfRows(); index++) {
					String[] arrayData = getExcelRowToArray(sheet1.getRow(index));
					if (arrayData != null) {
						dataList.add(getExcelDataRead(arrayData, keysList, checkMap));
					}
				}
			}

		}
		return dataList;
	}
}
