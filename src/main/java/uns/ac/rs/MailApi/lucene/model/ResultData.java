package uns.ac.rs.MailApi.lucene.model;

public class ResultData {

	private String title;
	private String sender;
	private String receiver;
	private String text;
	private String pdf;
	private String contactName;
	private String concactLastName;
	private String contactNote;

    public ResultData() {
    }

	public ResultData(String title, String sender, String receiver, String text, String pdf, String contactName, String concactLastName, String contactNote) {
		this.title = title;
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.pdf = pdf;
		this.contactName = contactName;
		this.concactLastName = concactLastName;
		this.contactNote = contactNote;
	}

	public ResultData(String title, String sender, String receiver, String text, String pdf) {
		this.title = title;
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.pdf = pdf;
	}

	public ResultData(String contactName, String concactLastName, String contactNote) {
		this.contactName = contactName;
		this.concactLastName = concactLastName;
		this.contactNote = contactNote;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	@Override
	public String toString() {
		return "ResultData{" +
				"title='" + title + '\'' +
				", sender='" + sender + '\'' +
				", receiver='" + receiver + '\'' +
				", text='" + text + '\'' +
				", pdf='" + pdf + '\'' +
				", contactName='" + contactName + '\'' +
				", concactLastName='" + concactLastName + '\'' +
				", contactNote='" + contactNote + '\'' +
				'}';
	}
}
