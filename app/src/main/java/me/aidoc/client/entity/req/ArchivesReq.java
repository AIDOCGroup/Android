package me.aidoc.client.entity.req;

import me.aidoc.client.entity.BaseResp;

public class ArchivesReq extends BaseResp {

    private int sex;
    private int height;
    private int weight;
    private int maritalSttus;
    private int birthStatus;

    private String birthday;
    private String surgicalTrauma;//手术外伤
    private String medicalHistory;//家族病史
    private String drugAllergy;//药物过敏
    private String otherAllergy;//其他过敏
    private String personalHabits;//个人习惯
    private String otherHabits;//其他习惯

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaritalSttus() {
        return maritalSttus;
    }

    public void setMaritalSttus(int maritalSttus) {
        this.maritalSttus = maritalSttus;
    }

    public int getBirthStatus() {
        return birthStatus;
    }

    public void setBirthStatus(int birthStatus) {
        this.birthStatus = birthStatus;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSurgicalTrauma() {
        return surgicalTrauma;
    }

    public void setSurgicalTrauma(String surgicalTrauma) {
        this.surgicalTrauma = surgicalTrauma;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getDrugAllergy() {
        return drugAllergy;
    }

    public void setDrugAllergy(String drugAllergy) {
        this.drugAllergy = drugAllergy;
    }

    public String getOtherAllergy() {
        return otherAllergy;
    }

    public void setOtherAllergy(String otherAllergy) {
        this.otherAllergy = otherAllergy;
    }

    public String getPersonalHabits() {
        return personalHabits;
    }

    public void setPersonalHabits(String personalHabits) {
        this.personalHabits = personalHabits;
    }

    public String getOtherHabits() {
        return otherHabits;
    }

    public void setOtherHabits(String otherHabits) {
        this.otherHabits = otherHabits;
    }
}
