package cn.lu.uee.data.api;

import cn.lu.uee.data.service.SubjRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lu on 2017/4/23.
 */
@RestController
@RequestMapping("/score/subject")
public class SubjScoreController {

    @Autowired
    private SubjRankService subjScoreService;

    @RequestMapping("/{year}/{province}/{category}")
    public int loadSubjScore(@PathVariable int year, @PathVariable int province, @PathVariable int category, @RequestParam(defaultValue = "0") int page) {
        return subjScoreService.loadSinaData(year, province, category, page);
    }
}
