package me.aidoc.client.entity.req;

/**
 * 基础病历上传
 */
public class CaseUpReq {

    /**
     * visitDay :
     * visitType : 就诊类型0:门诊，1：急诊，2：住院，3取消
     * visitHospial :
     * visitDepartment :
     * doctorName :
     * visitFee :
     * visitReason :
     * visitResult :
     * doctorAdvice :
     * doctorInfo :
     * visitRepost :
     * attachFileKeyJson : [{"name":"name1",urls:["url1","url2"]}]
     */

    private String visitDay;
    private String visitType;
    private String visitHospial;
    private String visitDepartment;
    private String doctorName;
    private String visitFee;
    private String visitReason;
    private String visitResult;
    private String doctorAdvice;
    private String doctorInfo;
    private String visitRepost;
    private String attachFileKeyJson;

    public String getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(String visitDay) {
        this.visitDay = visitDay;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitHospial() {
        return visitHospial;
    }

    public void setVisitHospial(String visitHospial) {
        this.visitHospial = visitHospial;
    }

    public String getVisitDepartment() {
        return visitDepartment;
    }

    public void setVisitDepartment(String visitDepartment) {
        this.visitDepartment = visitDepartment;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(String visitFee) {
        this.visitFee = visitFee;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getVisitRepost() {
        return visitRepost;
    }

    public void setVisitRepost(String visitRepost) {
        this.visitRepost = visitRepost;
    }

    public String getAttachFileKeyJson() {
        return attachFileKeyJson;
    }

    public void setAttachFileKeyJson(String attachFileKeyJson) {
        this.attachFileKeyJson = attachFileKeyJson;
    }
}
