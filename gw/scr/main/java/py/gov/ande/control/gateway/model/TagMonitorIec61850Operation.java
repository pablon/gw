package py.gov.ande.control.gateway.model;
// Generated 07/08/2016 10:31:56 AM by Hibernate Tools 5.1.0.Alpha1

/**
 * TagMonitorIec61850Operation generated by hbm2java
 */
public class TagMonitorIec61850Operation implements java.io.Serializable {

	private int id;
	private BufferedRcbOperation bufferedRcbOperation;
	private IedOperation iedOperation;
	private InformationType informationType;
	private Normalization normalization;
	private ReportingCapability reportingCapability;
	private UnbufferedRcbOperation unbufferedRcbOperation;
	private Boolean use;
	private String name;
	private String telegramAddress;
	private Boolean buffered;
	private Boolean unbuffered;

	public TagMonitorIec61850Operation() {
	}

	public TagMonitorIec61850Operation(int id, IedOperation iedOperation, String telegramAddress) {
		this.id = id;
		this.iedOperation = iedOperation;
		this.telegramAddress = telegramAddress;
	}

	public TagMonitorIec61850Operation(int id, BufferedRcbOperation bufferedRcbOperation, IedOperation iedOperation,
			InformationType informationType, Normalization normalization, ReportingCapability reportingCapability,
			UnbufferedRcbOperation unbufferedRcbOperation, Boolean use, String name, String telegramAddress,
			Boolean buffered, Boolean unbuffered) {
		this.id = id;
		this.bufferedRcbOperation = bufferedRcbOperation;
		this.iedOperation = iedOperation;
		this.informationType = informationType;
		this.normalization = normalization;
		this.reportingCapability = reportingCapability;
		this.unbufferedRcbOperation = unbufferedRcbOperation;
		this.use = use;
		this.name = name;
		this.telegramAddress = telegramAddress;
		this.buffered = buffered;
		this.unbuffered = unbuffered;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BufferedRcbOperation getBufferedRcbOperation() {
		return this.bufferedRcbOperation;
	}

	public void setBufferedRcbOperation(BufferedRcbOperation bufferedRcbOperation) {
		this.bufferedRcbOperation = bufferedRcbOperation;
	}

	public IedOperation getIedOperation() {
		return this.iedOperation;
	}

	public void setIedOperation(IedOperation iedOperation) {
		this.iedOperation = iedOperation;
	}

	public InformationType getInformationType() {
		return this.informationType;
	}

	public void setInformationType(InformationType informationType) {
		this.informationType = informationType;
	}

	public Normalization getNormalization() {
		return this.normalization;
	}

	public void setNormalization(Normalization normalization) {
		this.normalization = normalization;
	}

	public ReportingCapability getReportingCapability() {
		return this.reportingCapability;
	}

	public void setReportingCapability(ReportingCapability reportingCapability) {
		this.reportingCapability = reportingCapability;
	}

	public UnbufferedRcbOperation getUnbufferedRcbOperation() {
		return this.unbufferedRcbOperation;
	}

	public void setUnbufferedRcbOperation(UnbufferedRcbOperation unbufferedRcbOperation) {
		this.unbufferedRcbOperation = unbufferedRcbOperation;
	}

	public Boolean getUse() {
		return this.use;
	}

	public void setUse(Boolean use) {
		this.use = use;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelegramAddress() {
		return this.telegramAddress;
	}

	public void setTelegramAddress(String telegramAddress) {
		this.telegramAddress = telegramAddress;
	}

	public Boolean getBuffered() {
		return this.buffered;
	}

	public void setBuffered(Boolean buffered) {
		this.buffered = buffered;
	}

	public Boolean getUnbuffered() {
		return this.unbuffered;
	}

	public void setUnbuffered(Boolean unbuffered) {
		this.unbuffered = unbuffered;
	}

}