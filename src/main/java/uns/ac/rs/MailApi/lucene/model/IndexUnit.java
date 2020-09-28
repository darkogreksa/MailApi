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
   private String contactName;
   private String concactLastName;
   private String contactNote;

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getConcactLastName() {
        return concactLastName;
    }

    public void setConcactLastName(String concactLastName) {
        this.concactLastName = concactLastName;
    }

    public String getContactNote() {
        return contactNote;
    }

    public void setContactNote(String contactNote) {
        this.contactNote = contactNote;
    }

    public Document getLuceneDocument(){
        Document retVal = new Document();
        if (title != null) {
            System.out.println("kada se mejl indeksira cuva u fajl sistemu indexfiles po kljucu" + title);

            retVal.add(new TextField("title", title, Store.YES));
            retVal.add(new TextField("sender", sender, Store.YES));
            retVal.add(new TextField("receiver", receiver, Store.YES));;
            retVal.add(new TextField("text", text, Store.YES));
            retVal.add(new TextField("pdf", pdf, Store.YES));
        }
        if (contactName != null) {
            System.out.println("kada se kontakt indeksira" + contactName);
            retVal.add(new TextField("contactName", contactName, Store.YES));
            retVal.add(new TextField("contactLastName", concactLastName, Store.YES));
            retVal.add(new TextField("contactNote", contactNote, Store.YES));
        }
        return retVal;
    }

    @Override
    public String toString() {
        return "IndexUnit{" +
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
