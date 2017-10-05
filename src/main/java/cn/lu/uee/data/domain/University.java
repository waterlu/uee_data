package cn.lu.uee.data.domain;

import java.util.Date;

/**
 * Created by lu on 2017/4/25.
 */
public class University {

    private long universityId;

    private String universityCode;

    private String universityName;

    private String universityEnglishName;

    private int universityCategory;

    private int universityRanking;

    private int deleteFlag = 0;

    private Date createTime;

    private Date updateTime;

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    public String getUniversityCode() {
        return universityCode;
    }

    public void setUniversityCode(String universityCode) {
        this.universityCode = universityCode;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityEnglishName() {
        return universityEnglishName;
    }

    public void setUniversityEnglishName(String universityEnglishName) {
        this.universityEnglishName = universityEnglishName;
    }

    public int getUniversityCategory() {
        return universityCategory;
    }

    public void setUniversityCategory(int universityCategory) {
        this.universityCategory = universityCategory;
    }

    public int getUniversityRanking() {
        return universityRanking;
    }

    public void setUniversityRanking(int universityRanking) {
        this.universityRanking = universityRanking;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
