package cn.lu.uee.data.mapper;

import cn.lu.uee.data.domain.SubjectRank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lu on 2017/4/29.
 */
@Mapper
public interface SubjectTypeRankingMapper {

    @Insert("INSERT INTO `subject_type_ranking`" +
            "(UNIVERSITY_CODE, SUBJECT_TYPE_CODE, PROVINCE_CODE, CATEGORY, YEAR, MAX_SCORE, AVERAGE_SCORE) " +
            "VALUES" +
            "(#{universityCode}, #{subjectTypeCode}, #{provinceCode}, ${category}, #{year}, #{maxScore}, #{averageScore})")
    int insert(SubjectRank subjectScore);
}
