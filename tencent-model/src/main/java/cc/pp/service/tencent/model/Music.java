package cc.pp.service.tencent.model;

public class Music {

	private String author;
	private String url;
	private String title;
	private long id;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Music{" + "id\'" + id + "\'" + ", author='" + author + '\'' + ", url='" + url + '\'' + ", title='"
				+ title + '\'' + '}';
	}

}
