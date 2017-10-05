package cn.lu.uee.data.service.impl;

import cn.lu.uee.data.domain.University;
import cn.lu.uee.data.mapper.UniversityMapper;
import cn.lu.uee.data.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lu on 2017/5/7.
 */
@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public int createUniversity(University university) {
        return universityMapper.insert(university);
    }
}
