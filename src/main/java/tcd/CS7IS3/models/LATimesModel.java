package tcd.CS7IS3.models;

public class LATimesModel {
    String docno, docid, date, text; // Important to parse
    // Header contains majorly all the tags
    // Text also contains majorly all the tags
    String section, length, headline, byline, graphic, type,
            subject, correctionDate, correction;

    public String getDocno() {
        return this.docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getDocid() {
        return this.docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeadline() {
        return this.headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getByline() {
        return this.byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getGraphic() {
        return this.graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCorrection() {
        return this.correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getCorrectionDate() {
        return this.correction;
    }

    public void setCorrectionDate(String correctionDate) {
        this.correctionDate = correctionDate;
    }
}
