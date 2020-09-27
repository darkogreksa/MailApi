package uns.ac.rs.MailApi.lucene.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;


public class UploadModel {

	private String title;
	private String sender;
	private String receiver;
	private String text;
	private String pdf;
	private MultipartFile[] files;
	
	
	
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
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
		return "UploadModel [title=" + title + ", sender=" + sender + ", receiver=" + receiver + ", text=" + text
				+ ", pdf=" + pdf + "]";
	}
	
	

}
