package cn.lu.uee.data.api;

import cn.lu.uee.data.domain.CollegeRank;
import cn.lu.uee.data.service.CollegeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by lu on 2017/4/23.
 */
@RestController
@RequestMapping("/score/college")
public class CollegeScoreController {

    @Autowired
    private CollegeRankService collegeRankService;

    @RequestMapping("/{year}/{province}/{category}")
    public int loadSubjScore(@PathVariable int year, @PathVariable int province, @PathVariable int category, @RequestParam(defaultValue = "0") int page) {
        return collegeRankService.loadSinaData(year, province, category, page);
    }

    @RequestMapping("/ranking/{category}")
    public List<CollegeRank> getRanking(@PathVariable int category) {
        return collegeRankService.ranking(category);
    }

}
