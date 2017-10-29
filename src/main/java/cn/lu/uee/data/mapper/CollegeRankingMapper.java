package cn.lu.uee.data.mapper;

import cn.lu.uee.data.domain.CollegeRank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lu on 2017/4/24.
 */
@Mapper
@Repository
public interface CollegeRankingMapper {

    @Insert("INSERT INTO `college_ranking`" +
            "(UNIVERSITY_NAME, PROVINCE_NAME, CATEGORY, YEAR, MAX_SCORE, AVERAGE_SCORE) " +
            "VALUES" +
            "(#{universityName}, #{provinceName}, ${category}, #{year}, #{maxScore}, #{averageScore})")
    int insert(CollegeRank collegeRank);

    @Select("SELECT * FROM `college_ranking`")
    List<CollegeRank> selectAll();

    @Select("SELECT UNIVERSITY_NAME as universityName, CATEGORY, " +
            "PROVINCE_CODE as provCode, YEAR, " +
            "MAX_SCORE as maxScore, AVERAGE_SCORE as averageScore" +
            " FROM `college_ranking` where category=#{category} and delete_flag=0 and apply_flag=1")
    List<CollegeRank> select(int category);
}
