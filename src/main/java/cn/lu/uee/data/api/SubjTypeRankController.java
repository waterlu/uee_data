package cn.lu.uee.data.api;

import cn.lu.uee.data.service.SubjTypeRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lu on 2017/4/29.
 */
@RestController
@RequestMapping("/score/subjTypeRank")
public class SubjTypeRankController {

    @Autowired
    private SubjTypeRankService subjTypeRankService;

    @RequestMapping("/sort")
    public String sort() throws Exception {
        return subjTypeRankService.sort();
    }

}
