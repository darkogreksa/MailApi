package uns.ac.rs.MailApi.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LegacyIntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;


public class IndexUnit {

   private String title;
   private String sender;
   private String receiver;
   private String text;
   private String pdf;

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

	public Document getLuceneDocument(){
        Document retVal = new Document();
        retVal.add(new TextField("title", title, Store.YES));
        retVal.add(new TextField("sender", sender, Store.YES));
        retVal.add(new StringField("receiver", receiver, Store.YES));
//        retVal.add(new TextField("filedate",filedate,Store.YES));
        retVal.add(new TextField("text", text, Store.YES));
        retVal.add(new TextField("pdf",pdf, Store.YES));
        return retVal;
    }

}
