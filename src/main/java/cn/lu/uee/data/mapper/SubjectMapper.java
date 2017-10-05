package cn.lu.uee.data.mapper;

import cn.lu.uee.data.domain.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lu on 2017/4/29.
 */
@Mapper
public interface SubjectMapper {

    @Select("select * from subject")
    List<Subject> selectAll();

}
