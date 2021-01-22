package in.flyspark.gosec.entities;

public class Issues {
	private Cwe cwe;

	private String severity;

	private PrimaryLocation primaryLocation;

	private String effortMinutes;

	private String ruleId;

	private String type;

	private String engineId;

	public Cwe getCwe() {
		return cwe;
	}

	public void setCwe(Cwe cwe) {
		this.cwe = cwe;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public PrimaryLocation getPrimaryLocation() {
		return primaryLocation;
	}

	public void setPrimaryLocation(PrimaryLocation primaryLocation) {
		this.primaryLocation = primaryLocation;
	}

	public String getEffortMinutes() {
		return effortMinutes;
	}

	public void setEffortMinutes(String effortMinutes) {
		this.effortMinutes = effortMinutes;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEngineId() {
		return engineId;
	}

	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}

	@Override
	public String toString() {
		return "Issues [cwe = " + cwe + ", severity = " + severity + ", primaryLocation = " + primaryLocation + ", effortMinutes = " + effortMinutes + ", ruleId = " + ruleId + ", type = " + type + ", engineId = " + engineId + "]";
	}
}
