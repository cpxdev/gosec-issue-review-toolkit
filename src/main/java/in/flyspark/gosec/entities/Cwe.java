package in.flyspark.gosec.entities;

public class Cwe {
	private String ID;

	private String URL;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

	@Override
	public String toString() {
		return "Cwe [ID = " + ID + ", URL = " + URL + "]";
	}
}
