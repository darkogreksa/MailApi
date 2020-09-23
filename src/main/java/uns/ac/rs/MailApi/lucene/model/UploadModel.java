package uns.ac.rs.MailApi.lucene.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;


public class UploadModel {

    private String title;

    private String keywords;

    private String author;
    private Integer publicationYear;
    private String MIME;
    private MultipartFile[] files;
    private String filename;
    private int category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
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



    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "UploadModel [title=" + title + ", keywords=" + keywords + ", author=" + author + ", publicationYear="
				+ publicationYear + ", MIME=" + MIME + ", files=" + Arrays.toString(files) + ", filename=" + filename
				+ ", category=" + category + "]";
	}

}
