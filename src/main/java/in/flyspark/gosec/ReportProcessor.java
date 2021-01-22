package in.flyspark.gosec;

import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import in.flyspark.gosec.entities.IssuesReport;
import in.flyspark.gosec.service.ExcelService;
import in.flyspark.gosec.service.ReportService;
import in.flyspark.gosec.util.AppUtils;

public class ReportProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ReportProcessor.class.getSimpleName());

	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				System.out.println("Provide - InputJsonFileName ApplicationName(without space)");
			}
			JsonReader reader = new JsonReader(new FileReader(args[0]));
			IssuesReport issuesReport = new Gson().fromJson(reader, IssuesReport.class);

			issuesReport.setProjectName(args[1]);

			String fileNameTimestamp = AppUtils.getCurrentDateTime(AppUtils.FILE_FORMAT);
			String exportedTimestamp = AppUtils.getCurrentDateTime(AppUtils.REPORT_DATE_FORMAT);
			String fileName = AppUtils.getFileName(issuesReport.getProjectName(), fileNameTimestamp);

			issuesReport.setExportedTimestamp(exportedTimestamp);
			issuesReport.setFileName(fileName);

			ReportService.processFilter(issuesReport.getIssues());
			issuesReport.setSeverity(ReportService.getSeverity());
			issuesReport.setType(ReportService.getType());

			ExcelService.exportExcel(issuesReport);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
