package uns.ac.rs.MailApi.lucene.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LegacyIntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;


public class IndexUnit {

    private String text;
    private String title;
    private List<String> keywords = new ArrayList<String>();
    private String filename;
    private String filedate;
    private Integer publicationYear;
    private String author;
    private String MIME;
    private int category;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getKeywords() {
        return keywords;
    }
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFiledate() {
        return filedate;
    }
    public void setFiledate(String filedate) {
        this.filedate = filedate;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMIME() {
        return MIME;
    }

    public void setMIME(String MIME) {
        this.MIME = MIME;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }



    public Document getLuceneDocument(){
        Document retVal = new Document();
        retVal.add(new TextField("text", text, Store.YES));
        retVal.add(new TextField("title", title, Store.YES));
        for (String keyword : keywords) {
            retVal.add(new TextField("keyword", keyword, Store.YES));
        }
        retVal.add(new StringField("filename", filename, Store.YES));
//        retVal.add(new TextField("filedate",filedate,Store.YES));
        retVal.add(new TextField("author", author, Store.YES));
        retVal.add(new LegacyIntField("category",category, Store.YES));
        return retVal;
    }

}
