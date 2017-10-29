package cn.lu.uee.data.domain;

/**
 * Created by lu on 2017/4/24.
 */
public class CollegeRank {

    /**
     *
     */
    private String universityName;

    /**
     *
     */
    private String provinceName;


    public String univCode;

    public String provCode;

    public int category;

    public int year;

    public int maxScore;

    public int averageScore;

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public String getUnivCode() {
        return univCode;
    }

    public void setUnivCode(String univCode) {
        this.univCode = univCode;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
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
