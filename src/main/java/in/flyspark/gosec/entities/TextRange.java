package in.flyspark.gosec.entities;

public class TextRange {
	private String endLine;

	private String startLine;

	public String getEndLine() {
		return endLine;
	}

	public void setEndLine(String endLine) {
		this.endLine = endLine;
	}

	public String getStartLine() {
		return startLine;
	}

	public void setStartLine(String startLine) {
		this.startLine = startLine;
	}

	@Override
	public String toString() {
		return "TextRange [endLine = " + endLine + ", startLine = " + startLine + "]";
	}
}