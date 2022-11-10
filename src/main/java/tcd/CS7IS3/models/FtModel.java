package tcd.CS7IS3.models;

public class FtModel {
	String date, pub, docno, text; // Important to parse
	// Header contains majorly all the tags
	// Text also contains majorly all the tags
	String page, byline, headline, dateline, profile;

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPub() {
		return this.pub;
	}

	public void setPub(String pub) {
		this.pub = pub;
	}

	public String getDocno() {
		return this.docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getByline() {
		return this.byline;
	}

	public void setByline(String byline) {
		this.byline = byline;
	}

	public String getHeadline() {
		return this.headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDateline() {
		return this.dateline;
	}

	public void setDateline(String dateline) {
		this.dateline = dateline;
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}
