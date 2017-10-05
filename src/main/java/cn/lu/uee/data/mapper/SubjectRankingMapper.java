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
}
