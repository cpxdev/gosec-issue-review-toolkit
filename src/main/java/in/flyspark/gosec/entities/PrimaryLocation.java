package in.flyspark.gosec.entities;

public class PrimaryLocation {
	private String filePath;

	private TextRange textRange;

	private String message;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public TextRange getTextRange() {
		return textRange;
	}

	public void setTextRange(TextRange textRange) {
		this.textRange = textRange;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PrimaryLocation [filePath = " + filePath + ", textRange = " + textRange + ", message = " + message + "]";
	}
}
