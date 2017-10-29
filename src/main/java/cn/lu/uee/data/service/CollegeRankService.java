package cn.lu.uee.data.service;

import cn.lu.uee.data.domain.CollegeRank;

import java.util.List;

/**
 * Created by lu on 2017/4/23.
 */
public interface CollegeRankService {

    int loadSinaData(int year, int province, int category, int page);

    List<CollegeRank> ranking(int category);

}
