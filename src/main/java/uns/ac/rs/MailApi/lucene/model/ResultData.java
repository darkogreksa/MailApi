package uns.ac.rs.MailApi.lucene.model;

public class ResultData {

    private String title;
    private String author;
    private int category;
    private String keywords;
    private String location;
    private String highlight;

    public ResultData() {
        super();
    }

    public ResultData(String title, String author, int category, String keywords, String location, String highlight) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.keywords = keywords;
        this.location = location;
        this.highlight = highlight;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

}
