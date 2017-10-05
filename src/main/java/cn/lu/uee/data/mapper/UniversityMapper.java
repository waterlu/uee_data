package cn.lu.uee.data.mapper;

import cn.lu.uee.data.domain.University;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by lu on 2017/5/7.
 */
@Mapper
@Repository
public interface UniversityMapper {

    @Insert("INSERT INTO `university`" +
            "(`university_code`, `university_name`, `university_english_name`, `university_category`, " +
            "`university_ranking`) " +
            "VALUES" +
            "(#{universityCode}, #{universityName}, #{universityEnglishName}, ${universityCategory}, " +
            "#{universityRanking})")
    int insert(University university);
}
