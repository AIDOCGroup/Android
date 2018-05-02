package me.aidoc.client.entity.resp;

import java.util.List;

import me.aidoc.client.entity.BaseResp;

public class CaseCenterResp  extends BaseResp {
    /**
     * id : 1
     * createdOn : 1520393901000
     * updatedOn : 1520393901000
     * updatedTimestamp : 1520393901465
     * userId : 0
     * visitDay : 1514764800000
     * visitType : 1
     * visitHospial :
     * visitDepartment :
     * doctorName :
     * visitFee : 0.0
     * visitReason :
     * visitResult :
     * doctorAdvice :
     * doctorInfo :
     * visitRepost :
     * attachFileKeys : ["{\"name\":\"name1\",\"urls\":[\"url1\",\"url2\"]}"]
     */

    private int id;
    private long createdOn;
    private long updatedOn;
    private long updatedTimestamp;
    private int userId;
    private long visitDay;
    private int visitType;
    private String visitHospial;
    private String visitDepartment;
    private String doctorName;
    private double visitFee;
    private String visitReason;
    private String visitResult;
    private String doctorAdvice;
    private String doctorInfo;
    private String visitRepost;
    private List<String> attachFileKeys;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(long updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(long visitDay) {
        this.visitDay = visitDay;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
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

    public double getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(double visitFee) {
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

    public List<String> getAttachFileKeys() {
        return attachFileKeys;
    }

    public void setAttachFileKeys(List<String> attachFileKeys) {
        this.attachFileKeys = attachFileKeys;
    }
}
