package cn.lu.uee.data.service.impl;

import cn.lu.uee.data.domain.Subject;
import cn.lu.uee.data.domain.SubjectRank;
import cn.lu.uee.data.mapper.SubjectMapper;
import cn.lu.uee.data.mapper.SubjectRankingMapper;
import cn.lu.uee.data.mapper.SubjectTypeRankingMapper;
import cn.lu.uee.data.service.SubjTypeRankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lu on 2017/4/29.
 */
@Service
public class SubjTypeRankServiceImpl implements SubjTypeRankService {

    private final Logger logger = LoggerFactory.getLogger(SubjTypeRankServiceImpl.class);

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectRankingMapper subjectRankingMapper;

    @Autowired
    private SubjectTypeRankingMapper subjectTypeRankingMapper;

    public String sort() throws Exception {
        Map<String, String> code2Name = new HashMap<>();
        Map<String, String> name2type = new HashMap<>();

        logger.info("load subjects");
        List<Subject> subjectList = subjectMapper.selectAll();
        for (Subject subject : subjectList) {
            if (subject.getSubjectName().length() == 4) {
                code2Name.put(subject.getSubjectCode(), subject.getSubjectName());
            }
            String type = subject.getSubjectCode().substring(0, 4);
            if (code2Name.containsKey(type)) {
                name2type.put(subject.getSubjectName(), code2Name.get(type));
            } else {
                logger.error("miss " + type);
            }
        }

        logger.info("load subject ranking");
        Map<String, List<SubjectRank>> univ = new HashMap<>();
        List<SubjectRank> subjectRankList = subjectRankingMapper.selectAll();
        for (SubjectRank rank : subjectRankList) {
            String subjType = name2type.get(rank.getSubjCode());
            if (subjType == null) {
                logger.error("miss " + rank.getSubjCode());
            } else {
                String key = rank.getUnivCode() + "|" + subjType;
                if (univ.containsKey(key)) {
                    List<SubjectRank> temp = univ.get(key);
                    temp.add(rank);
                } else {
                    List<SubjectRank> temp = new ArrayList<>();
                    temp.add(rank);
                    univ.put(key, temp);
                }
            }
        }

        logger.info("sort");
        for(Map.Entry<String, List<SubjectRank>> entry : univ.entrySet()) {
            String key = entry.getKey();
            List<SubjectRank> temp = entry.getValue();
            String [] data = key.split("|");
            String univCode = data[0];
            String subjCode = data[1];
            int max = 0;
            int total = 0;
            int count = 0;
            for (SubjectRank rank : temp) {
                if (rank.getMaxScore() > max) {
                    max = rank.getMaxScore();
                }
                total += rank.getAverageScore();
                count += 1;
            }
            int average = total / count;
            logger.info(univCode + "\t" + subjCode + "\t" + max + "\t" + average);
        }

        return "OK";
    }
}
