package cn.lu.uee.data.service;

import java.util.List;

/**
 * Created by lu on 2017/4/23.
 */
public interface SubjRankService {

    int loadSinaData(int year, int province, int category, int page);

    List<String> ranking(int category);

}
