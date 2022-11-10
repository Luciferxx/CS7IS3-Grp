package tcd.CS7IS3.models;
/*
IMP -> <TEXT> <DOCNO> 
<DATE1>
<TXT5>
<HEADER>
<FIG ...>
<F ...>
 */

public class FbisModel {
	String docno, text; // Important to parse
	// Header contains majorly all the tags
	// Text also contains majorly all the tags
	String txt5, date1, fig, header, f;

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

	public String getTxt5() {
		return this.txt5;
	}

	public void setTxt5(String txt5) {
		this.txt5 = txt5;
	}

	public String getDate1() {
		return this.date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getFig() {
		return this.fig;
	}

	public void setFig(String fig) {
		this.fig = fig;
	}

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getF() {
		return this.f;
	}

	public void setF(String f) {
		this.f = f;
	}
}
