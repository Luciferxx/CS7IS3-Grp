package tcd.CS7IS3.models;

public class Fr94Model {
    String date, fr, docno, text; // Important to parse
    // Header contains majorly all the tags
    // Text also contains majorly all the tags
    String footcite, cfrno, rindock, usDept, usBureau, imports,
            doctitle, agency, action, summary, address, further, supplem,
            signer, signjob, frFiling, billing, footnote, footname;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFr() {
        return this.fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
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

    public String getFootcite() {
        return this.footcite;
    }

    public void setFootcite(String footcite) {
        this.footcite = footcite;
    }

    public String getCfrno() {
        return this.cfrno;
    }

    public void setCfrno(String cfrno) {
        this.cfrno = cfrno;
    }

    public String getRindock() {
        return this.rindock;
    }

    public void setRindock(String rindock) {
        this.rindock = rindock;
    }

    public String getUsDept() {
        return this.usDept;
    }

    public void setUsDept(String usDept) {
        this.usDept = usDept;
    }

    public String getUsBureau() {
        return this.usBureau;
    }

    public void setUsBureau(String usBureau) {
        this.usBureau = usBureau;
    }

    public String getImports() {
        return this.imports;
    }

    public void setImports(String imports) {
        this.imports = imports;
    }

    public String getDoctitle() {
        return this.doctitle;
    }

    public void setDoctitle(String doctitle) {
        this.doctitle = doctitle;
    }

    public String getAgency() {
        return this.agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFurther() {
        return this.further;
    }

    public void setFurther(String further) {
        this.further = further;
    }

    public String getSupplem() {
        return this.supplem;
    }

    public void setSupplem(String supplem) {
        this.supplem = supplem;
    }

    public String getSigner() {
        return this.signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignjob() {
        return this.signjob;
    }

    public void setSignjob(String signjob) {
        this.signjob = signjob;
    }

    public String getFrFiling() {
        return this.frFiling;
    }

    public void setFrFiling(String frFiling) {
        this.frFiling = frFiling;
    }

    public String getBilling() {
        return this.billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public String getFootnote() {
        return this.footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    public String getFootname() {
        return this.footname;
    }

    public void setFootname(String footname) {
        this.footname = footname;
    }
}
