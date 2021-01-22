package in.flyspark.gosec.entities;

import java.util.List;
import java.util.Map;

public class IssuesReport {
	private String projectName;
	private String exportedTimestamp;
	private String fileName;
	private Map<String, Long> severity;
	private Map<String, Long> type;
	private List<Issues> issues;

	public Map<String, Long> getSeverity() {
		return severity;
	}

	public void setSeverity(Map<String, Long> severity) {
		this.severity = severity;
	}

	public Map<String, Long> getType() {
		return type;
	}

	public void setType(Map<String, Long> type) {
		this.type = type;
	}

	public String getExportedTimestamp() {
		return exportedTimestamp;
	}

	public void setExportedTimestamp(String exportedTimestamp) {
		this.exportedTimestamp = exportedTimestamp;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public List<Issues> getIssues() {
		return issues;
	}

	public void setIssues(List<Issues> issues) {
		this.issues = issues;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String toString() {
		return "IssuesReport [applicationName=" + projectName + ", exportedTimestamp=" + exportedTimestamp + ", fileName=" + fileName + ", severity=" + severity + ", type=" + type + ", issues=" + issues + "]";
	}

}
