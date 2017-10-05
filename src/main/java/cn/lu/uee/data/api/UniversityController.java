package cn.lu.uee.data.api;

import cn.lu.uee.data.domain.University;
import cn.lu.uee.data.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by lu on 2017/4/25.
 */
@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create(@RequestBody University university) {
        universityService.createUniversity(university);
        return"OK";
    }

    @RequestMapping("/html")
    public String helloHtml(Map<String,Object> map){
        map.put("hello", "from TemplateController.helloHtml");
        return"university";
    }
}