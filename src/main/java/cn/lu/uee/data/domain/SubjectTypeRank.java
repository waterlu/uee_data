package cn.lu.uee.data.domain;

/**
 * Created by lu on 2017/4/29.
 */
public class SubjectTypeRank {

    public String universityCode;

    public String subjectTypeCode;

    public String provinceCode;

    public int category;

    public int year;

    public int maxScore;

    public int averageScore;

    public String getUniversityCode() {
        return universityCode;
    }

    public void setUniversityCode(String universityCode) {
        this.universityCode = universityCode;
    }

    public String getSubjectTypeCode() {
        return subjectTypeCode;
    }

    public void setSubjectTypeCode(String subjectTypeCode) {
        this.subjectTypeCode = subjectTypeCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
}
