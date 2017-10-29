package cn.lu.uee.data.mapper;

import cn.lu.uee.data.domain.SubjectRank;
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
public interface SubjectRankingMapper {

    @Insert("INSERT INTO `subject_ranking`" +
            "(UNIVERSITY_CODE, SUBJECT_CODE, PROVINCE_CODE, CATEGORY, YEAR, MAX_SCORE, AVERAGE_SCORE) " +
            "VALUES" +
            "(#{univCode}, #{subjCode}, #{provCode}, ${category}, #{year}, #{maxScore}, #{averageScore})")
    int insert(SubjectRank subjectScore);

    @Select("SELECT * FROM `subject_ranking`")
    List<SubjectRank> selectAll();

    @Select("SELECT r.university_name as universityName, r.subject_name as subjectName, r.province_name as provinceName, " +
            "r.`year`, r.max_score as maxScore, r.average_score as averageScore " +
            "FROM subject_ranking r, university u " +
            "WHERE r.university_code = u.university_code " +
            "AND r.category = #{category} AND r.province_code >= 5 AND r.province_code <= 14")
    List<SubjectRank> selectCategory(int category);
}
