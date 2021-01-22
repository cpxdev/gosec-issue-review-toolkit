package in.flyspark.gosec.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.gosec.entities.Issues;
import in.flyspark.gosec.entities.IssuesReport;
import in.flyspark.gosec.entities.PrimaryLocation;
import in.flyspark.gosec.entities.TextRange;
import in.flyspark.gosec.util.AppUtils;

public class ExcelService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelService.class.getSimpleName());

	public static boolean exportExcel(IssuesReport report) throws IOException {
		logger.debug("Exporting Excel");

		File file = new File(report.getFileName() + ".xlsx");

		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file);) {
			CellStyle commonStyle = workbook.createCellStyle();
			commonStyle.setAlignment(HorizontalAlignment.LEFT);
			commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			commonStyle.setWrapText(true);
			setFont(workbook.createFont(), commonStyle, false, 11, IndexedColors.BLACK.getIndex());
			setBorder(commonStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, commonStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

			CellStyle centeredStyle = workbook.createCellStyle();
			centeredStyle.setAlignment(HorizontalAlignment.CENTER);
			centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			centeredStyle.setWrapText(true);
			setFont(workbook.createFont(), centeredStyle, false, 11, IndexedColors.BLACK.getIndex());
			setBorder(centeredStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, centeredStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

			CellStyle boldStyle = workbook.createCellStyle();
			boldStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			boldStyle.setWrapText(true);
			setFont(workbook.createFont(), boldStyle, true, 11, IndexedColors.BLACK.getIndex());
			setBorder(boldStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, boldStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

			CellStyle severityCellStyle = workbook.createCellStyle();
			severityCellStyle.setWrapText(true);
			severityCellStyle.setAlignment(HorizontalAlignment.CENTER);
			severityCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setFont(workbook.createFont(), severityCellStyle, false, 12, IndexedColors.BLACK.getIndex());

			CellStyle typeCellStyle = workbook.createCellStyle();
			typeCellStyle.setWrapText(true);
			typeCellStyle.setAlignment(HorizontalAlignment.CENTER);
			typeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			setBorder(typeCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setFont(workbook.createFont(), typeCellStyle, false, 12, IndexedColors.BLACK.getIndex());

			CellStyle categoryStyle = workbook.createCellStyle();
			categoryStyle.setWrapText(true);
			categoryStyle.setAlignment(HorizontalAlignment.CENTER);
			categoryStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			setBorder(categoryStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setFont(workbook.createFont(), categoryStyle, true, 12, IndexedColors.WHITE.getIndex());
			setBGColor(workbook, categoryStyle, new Color(32, 55, 100), FillPatternType.SOLID_FOREGROUND);

			CellStyle reportHeaderStyle = workbook.createCellStyle();
			reportHeaderStyle.setWrapText(true);
			setFont(workbook.createFont(), reportHeaderStyle, true, 12, IndexedColors.WHITE.getIndex());
			setBorder(reportHeaderStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, reportHeaderStyle, new Color(32, 55, 100), FillPatternType.SOLID_FOREGROUND);
			reportHeaderStyle.setAlignment(HorizontalAlignment.CENTER);

			Cell firstCell = null;
			Cell lastCell = null;

			CellStyle summaryHeadaerStyle = workbook.createCellStyle();
			summaryHeadaerStyle.setWrapText(false);
			summaryHeadaerStyle.setAlignment(HorizontalAlignment.CENTER);
			summaryHeadaerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			setBorder(summaryHeadaerStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setFont(workbook.createFont(), summaryHeadaerStyle, true, 12, IndexedColors.WHITE.getIndex());
			setBGColor(workbook, summaryHeadaerStyle, new Color(0, 32, 96), FillPatternType.SOLID_FOREGROUND);

			Font hlink_font = workbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());

			CellStyle linkCellStyleCenter = workbook.createCellStyle();
			linkCellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			linkCellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			linkCellStyleCenter.setWrapText(true);
			setBorder(linkCellStyleCenter, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, linkCellStyleCenter, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);
			linkCellStyleCenter.setFont(hlink_font);

			// Summary Sheet
			logger.debug("Summary Sheet");

			int firstRow = 1;

			Sheet summarySheet = workbook.createSheet(AppUtils.SUMMARY);
			Row summaryHeaderRow = summarySheet.createRow((short) firstRow);

			Cell summaryHeaderCell = summaryHeaderRow.createCell(1);
			summaryHeaderCell.setCellValue(report.getProjectName());
			summaryHeaderCell.setCellStyle(summaryHeadaerStyle);

			Row exportedDateRow = summarySheet.createRow(summarySheet.getLastRowNum() + 2);
			Cell exportedDateCell = exportedDateRow.createCell(1);
			exportedDateCell.setCellValue("Report Exported On");
			exportedDateCell.setCellStyle(boldStyle);

			exportedDateCell = exportedDateRow.createCell(2);
			exportedDateCell.setCellValue(report.getExportedTimestamp());
			exportedDateCell.setCellStyle(boldStyle);

			// Issue Severities Summary
			logger.debug("Issue Severities Summary");

			// Issue Severity Header
			String[] severityColumns = null;
			severityColumns = new String[] { "Issue Severity", "Count" };

			Row severityHeaderRow = summarySheet.createRow(summarySheet.getLastRowNum() + 2);
			for (int i = 0; i < severityColumns.length; i++) {
				Cell cell = severityHeaderRow.createCell(i + 1);
				cell.setCellValue(severityColumns[i]);
				cell.setCellStyle(categoryStyle);

				if (i == 0)
					firstCell = cell;
				if (i == (severityColumns.length - 1))
					lastCell = cell;
			}

			// Issue Severity Body
			Map<String, Long> issueSeverities = new LinkedHashMap<>(report.getSeverity());
			int severityRowNumber = summarySheet.getLastRowNum() + 1; // i.e. 13
			for (Map.Entry<String, Long> entry : issueSeverities.entrySet()) {
				int severityColumnIndex = 1;
				Row severityRow = summarySheet.createRow(severityRowNumber++);

				Cell cellKey = severityRow.createCell(severityColumnIndex++);
				cellKey.setCellValue(entry.getKey());
				cellKey.setCellStyle(severityCellStyle);

				Cell cellValue = severityRow.createCell(severityColumnIndex++);
				cellValue.setCellValue(issueSeverities.get(entry.getKey()));
				cellValue.setCellStyle(severityCellStyle);

			}

			// Issue Severity Footer
			--severityRowNumber;
			summarySheet.getRow(severityRowNumber).getCell(1).setCellStyle(categoryStyle);
			summarySheet.getRow(severityRowNumber).getCell(2).setCellStyle(categoryStyle);

			// Issues Types Summary
			logger.debug("Issues Types Summary");

			// Issue Type Header
			String[] typeColumns = null;
			typeColumns = new String[] { "Issue Type", "Count" };

			Row typeHeaderRow = summarySheet.createRow(summarySheet.getLastRowNum() + 2);
			for (int i = 0; i < typeColumns.length; i++) {
				Cell cell = typeHeaderRow.createCell(i + 1);
				cell.setCellValue(typeColumns[i]);
				cell.setCellStyle(categoryStyle);
			}

			// Issue Type Body
			Map<String, Long> issyeTypes = new LinkedHashMap<>(report.getType());
			int typeRowNumber = summarySheet.getLastRowNum() + 1;
			for (Map.Entry<String, Long> entry : issyeTypes.entrySet()) {
				int typeColumnIndex = 1;
				Row typeRow = summarySheet.createRow(typeRowNumber++);

				Cell cellKey = typeRow.createCell(typeColumnIndex++);
				cellKey.setCellValue(entry.getKey());
				cellKey.setCellStyle(typeCellStyle);

				Cell cellValue = typeRow.createCell(typeColumnIndex++);
				cellValue.setCellValue(issyeTypes.get(entry.getKey()));
				cellValue.setCellStyle(typeCellStyle);

			}

			// Issue Type Footer
			--typeRowNumber;
			summarySheet.getRow(typeRowNumber).getCell(1).setCellStyle(categoryStyle);
			summarySheet.getRow(typeRowNumber).getCell(2).setCellStyle(categoryStyle);

			// Auto Resize Columns
			// 10 is random number
			for (int i = 1; i < 10; i++) {
				summarySheet.autoSizeColumn(i);
			}

			// Issue Details Sheet

			if (!report.getIssues().isEmpty()) {
				Sheet issueDetailsSheet = workbook.createSheet(AppUtils.REPORT);

				Row reportHeaderRow = issueDetailsSheet.createRow(0);

				String[] reportColumns = { "#", "Issue Type", "Issue Severity", "Component", "Line", "Message", "Solution" };
				for (int i = 0; i < reportColumns.length; i++) {
					Cell cell = reportHeaderRow.createCell(i);
					cell.setCellValue(reportColumns[i]);
					cell.setCellStyle(reportHeaderStyle);
					if (i == 0)
						firstCell = cell;
					if (i == (reportColumns.length - 1))
						lastCell = cell;
				}

				severityCellStyle = workbook.createCellStyle();
				severityCellStyle.setWrapText(true);
				severityCellStyle.setAlignment(HorizontalAlignment.CENTER);
				setBorder(severityCellStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);

				int reportRowNumber = 1;
				int reportColumnNumber = 0;
				for (Issues issue : report.getIssues()) {
					PrimaryLocation primeLoc = issue.getPrimaryLocation();
					TextRange textRange = primeLoc.getTextRange();
					reportColumnNumber = 0;
					Row reportRow = issueDetailsSheet.createRow(reportRowNumber);

					Cell cellSrNo = reportRow.createCell(reportColumnNumber++);
					cellSrNo.setCellValue(reportRowNumber);
					cellSrNo.setCellStyle(centeredStyle);

					Cell cellType = reportRow.createCell(reportColumnNumber++);
					cellType.setCellValue(issue.getType());
					cellType.setCellStyle(centeredStyle);

					Cell severityCell = reportRow.createCell(reportColumnNumber++);
					severityCell.setCellValue(issue.getSeverity());
					severityCell.setCellStyle(centeredStyle);

					Cell cellComponent = reportRow.createCell(reportColumnNumber++);
					cellComponent.setCellValue(primeLoc.getFilePath());
					cellComponent.setCellStyle(centeredStyle);

					Cell cellLine = reportRow.createCell(reportColumnNumber++);
					cellLine.setCellValue(textRange.getStartLine() + " - " + textRange.getEndLine());
					cellLine.setCellStyle(centeredStyle);

					Cell cellMessage = reportRow.createCell(reportColumnNumber++);
					cellMessage.setCellValue(primeLoc.getMessage());
					cellMessage.setCellStyle(centeredStyle);

					addLink(workbook, reportRow.createCell(reportColumnNumber++), linkCellStyleCenter, issue.getCwe().getURL());

					reportRowNumber++;
				}

				issueDetailsSheet.setAutoFilter(new CellRangeAddress(firstCell.getRowIndex(), lastCell.getRowIndex(), firstCell.getColumnIndex(), lastCell.getColumnIndex()));
				issueDetailsSheet.createFreezePane(0, 1);

				for (int i = 0; i < reportColumns.length; i++) {
					if (i == 0 || i == 4) {
						issueDetailsSheet.setColumnWidth(i, 2000);
					} else if (i == (reportColumns.length - 1)) {
						issueDetailsSheet.setColumnWidth(i, 12000);
					} else if (i == 3 || i == 5) {
						issueDetailsSheet.setColumnWidth(i, 20000);
					} else {
						issueDetailsSheet.setColumnWidth(i, 6000);
					}
				}
			} else {
				logger.debug("Issue list is empty");
			}

			workbook.write(fileOut);
			logger.debug("Excel Exported");
			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return false;
	}

	protected static void addLink(Workbook workbook, Cell cell, CellStyle cellStyle, String link) {
		try {
			String url = URLEncoder.encode(link, "UTF-8");
			createLink(workbook, cell, cellStyle, "Click Here", url);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected static void createLink(Workbook workbook, Cell cell, CellStyle cellStyle, String value, String linkData) {
		try {
			cell.setCellValue(value);
			Font hlink_font = workbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			cellStyle.setFont(hlink_font);
			XSSFHyperlink link = (XSSFHyperlink) workbook.getCreationHelper().createHyperlink(HyperlinkType.FILE);
			link.setAddress(linkData);
			cell.setHyperlink(link);
			cell.setCellStyle(cellStyle);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	protected static void setBordersToMergedCells(Sheet sheet, CellRangeAddress rangeAddress) {
		RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
	}

	protected static void setFont(Font font, CellStyle cellStyle, boolean isBold, int height, short color) {
		font.setBold(isBold);
		font.setFontHeightInPoints((short) height);
		font.setColor(color);
		cellStyle.setFont(font);
	}

	protected static void setBGColor(Workbook workbook, CellStyle cellStyle, Color color, FillPatternType fp) {
		byte[] rgb = new byte[] { (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue() };
		if (cellStyle instanceof XSSFCellStyle) {
			XSSFCellStyle xssfreportHeaderCellStyle = (XSSFCellStyle) cellStyle;
			xssfreportHeaderCellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
		} else if (cellStyle instanceof HSSFCellStyle) {
			cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
			HSSFWorkbook hssfworkbook = (HSSFWorkbook) workbook;
			HSSFPalette palette = hssfworkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LIME.getIndex(), rgb[0], rgb[1], rgb[2]);
		}
		cellStyle.setFillPattern(fp);

	}

	protected static void setBorder(CellStyle cellStyle, BorderStyle left, BorderStyle top, BorderStyle right, BorderStyle bottom) {
		cellStyle.setBorderLeft(left);
		cellStyle.setBorderTop(top);
		cellStyle.setBorderRight(right);
		cellStyle.setBorderBottom(bottom);
	}

}
