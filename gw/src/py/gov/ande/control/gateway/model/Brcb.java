package py.gov.ande.control.gateway.model;
// Generated 12/07/2016 12:48:11 AM by Hibernate Tools 5.1.0.Alpha1

/**
 * Brcb generated by hbm2java
 */
public class Brcb implements java.io.Serializable {

	private int id;
	private int iedId;
	private String description;
	private String dataset;

	public Brcb() {
	}

	public Brcb(int id, int iedId, String description) {
		this.id = id;
		this.iedId = iedId;
		this.description = description;
	}

	public Brcb(int id, int iedId, String description, String dataset) {
		this.id = id;
		this.iedId = iedId;
		this.description = description;
		this.dataset = dataset;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIedId() {
		return this.iedId;
	}

	public void setIedId(int iedId) {
		this.iedId = iedId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataset() {
		return this.dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

}