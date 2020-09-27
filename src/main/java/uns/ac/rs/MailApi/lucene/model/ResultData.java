package uns.ac.rs.MailApi.lucene.model;

public class ResultData {

	private String title;
	private String sender;
	private String receiver;
	private String text;
	private String pdf;

    public ResultData() {
        super();
    }

	public ResultData(String title, String sender, String receiver, String text, String pdf) {
		super();
		this.title = title;
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.pdf = pdf;
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

    
}
