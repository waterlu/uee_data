package cn.lu.uee.data.service.impl;

import cn.lu.uee.data.domain.CollegeRank;
import cn.lu.uee.data.domain.SubjectRank;
import cn.lu.uee.data.mapper.CollegeRankingMapper;
import cn.lu.uee.data.service.CollegeRankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by lu on 2017/10/21.
 */
@Service
public class CollegeRankServiceImpl implements CollegeRankService {

    private final Logger logger = LoggerFactory.getLogger(CollegeRankServiceImpl.class);

    @Value("${sina.kaoshi.college.addr}")
    private String uri;

    @Autowired
    private CollegeRankingMapper collegeRankingMapper;

    @Override
    public int loadSinaData(int year, int province, int category, int page) {
        String sYear = Integer.toString(year);
        String provId = Integer.toString(province);
        String wl = Integer.toString(category);

        String urlName = uri.replaceAll("#year", sYear).replaceAll("#provid", provId).replaceAll("#wl", wl);

        URL url = null;
        int count = 0;
        List<CollegeRank> collegeDataList = new ArrayList<>();
        try {
            for(int i=page; i<50; i++) {
                logger.info("page=" + (i + 1));
                String uri = urlName + "&page=" + (i + 1);
                url = new URL(uri);
                URLConnection connection = url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "utf-8");
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                StringBuffer buffer = new StringBuffer();
                while (line != null) {
                    line = reader.readLine();
                    buffer.append(line);
                }
                in.close();
                parseTable(collegeDataList, buffer.toString());
                if (collegeDataList.size() == 0) {
                    break;
                }
                for (CollegeRank collegeRank : collegeDataList) {
                    collegeRankingMapper.insert(collegeRank);
                }
                count += collegeDataList.size();
                collegeDataList.clear();
            }
            logger.info("done");
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return count;
        }
    }

    @Override
    public List<CollegeRank> ranking(int category) {
        List<CollegeRank> collegeDataList = collegeRankingMapper.select(category);
        Map<String, CollegeScore> collegeScoreMap = new HashMap<>();
        for (CollegeRank collegeRank : collegeDataList) {
            CollegeScore collegeScore = null;
            String name = collegeRank.getUniversityName();
            if (collegeScoreMap.containsKey(name)) {
                collegeScore = collegeScoreMap.get(name);
            } else {
                collegeScore = new CollegeScore();
                collegeScore.name = name;
                collegeScoreMap.put(name, collegeScore);
            }

            int maxScore = collegeRank.getMaxScore();
            int averageScore = collegeRank.getAverageScore();
            String key = String.format("%s-%s", collegeRank.getYear(), collegeRank.getProvCode());

            if (!collegeScore.maxScore.containsKey(key)) {
                collegeScore.maxScore.put(key, maxScore);
            }

            if (!collegeScore.averageScore.containsKey(key)) {
                collegeScore.averageScore.put(key, averageScore);
            }
        }

//        List<CollegeRank> collegeList = new ArrayList<>();
//        for (CollegeScore collegeScore : collegeScoreMap.values()) {
//
//            CollegeRank college = new CollegeRank();
//            college.setUniversityName(collegeScore.name);
//            college.setCategory(collegeScore.maxScore.size());
//
//            if (college.getCategory() >= 27) {
//                List<Integer> scoreList = new ArrayList<>();
//                scoreList.addAll(collegeScore.maxScore.values());
//                Collections.sort(scoreList, new Comparator<Integer>() {
//                    @Override
//                    public int compare(Integer o1, Integer o2) {
//                        return o2 - o1;
//                    }
//                });
//                int score = 0;
//                for (int i=0; i<27; i++) {
//                    score += scoreList.get(i);
//                }
//                college.setMaxScore(score);
//
//                scoreList = new ArrayList<>();
//                scoreList.addAll(collegeScore.averageScore.values());
//                Collections.sort(scoreList, new Comparator<Integer>() {
//                    @Override
//                    public int compare(Integer o1, Integer o2) {
//                        return o2 - o1;
//                    }
//                });
//                score = 0;
//                for (int i=0; i<27; i++) {
//                    score += scoreList.get(i);
//                }
//                college.setAverageScore(score);
//
//                collegeList.add(college);
//            }
//        }

        List<CollegeRank> collegeList = new ArrayList<>();
        for (CollegeScore collegeScore : collegeScoreMap.values()) {

            CollegeRank college = new CollegeRank();
            college.setUniversityName(collegeScore.name);

            college.setCategory(collegeScore.maxScore.size());

            List<Integer> scoreList = new ArrayList<>();
            scoreList.addAll(collegeScore.maxScore.values());
            int score = 0;
            for (int i=0; i<scoreList.size(); i++) {
                score += scoreList.get(i);
            }
            college.setMaxScore(score / scoreList.size());

            scoreList = new ArrayList<>();
            scoreList.addAll(collegeScore.averageScore.values());
            score = 0;
            for (int i=0; i<scoreList.size(); i++) {
                score += scoreList.get(i);
            }
            college.setAverageScore(score / scoreList.size());

            collegeList.add(college);
        }

//        Collections.sort(collegeList, new Comparator<CollegeRank>() {
//            @Override
//            public int compare(CollegeRank o1, CollegeRank o2) {
//                int a1 = o1.getAverageScore() + o1.getMaxScore() / 2;
//                int a2 = o2.getAverageScore() + o2.getMaxScore() / 2;
//                if (a1 == a2) {
//                    return o2.getAverageScore() - o1.getAverageScore();
//                } else {
//                    return a2 - a1;
//                }
//            }
//        });

        Collections.sort(collegeList, new Comparator<CollegeRank>() {
            @Override
            public int compare(CollegeRank o1, CollegeRank o2) {
                int a1 = o1.getAverageScore();
                int a2 = o2.getAverageScore();
                if (a1 == a2) {
                    return o2.getMaxScore() - o1.getMaxScore();
                } else {
                    return a2 - a1;
                }
            }
        });

//        for (CollegeRank college : collegeList) {
//            int score = college.getAverageScore() + college.getMaxScore() / 2;
//            int average = college.getAverageScore() / 27;
//            System.out.println(college.getUniversityName() + "\t" + average + "\t" +
//                    score + "\t" + college.getCategory());
//        }

        for (CollegeRank college : collegeList) {
            int score = college.getMaxScore();
            int average = college.getAverageScore();
            System.out.println(college.getUniversityName() + "\t" + average + "\t" +
                    score + "\t" + college.getCategory());
        }

        return collegeList;
    }

    public class CollegeScore {

        public String name;

        public Map<String, Integer> maxScore = new HashMap<>();

        public Map<String, Integer> averageScore = new HashMap<>();
    }

    private boolean parseTable(List<CollegeRank> collegeDataList, String data) throws IOException
    {
        String BEG = "<table class=\"tbL2\">";
        String END = "</table>";
        int pos1 = data.indexOf(BEG);
        int pos2 = data.indexOf(END, pos1);
        data = data.substring(pos1 + BEG.length(), pos2);
        if (data.trim().length() == 0)
        {
            return false;
        }
        while (data != null && data.trim().length() > 0)
        {
            data = parseRow(collegeDataList, data);
        }
        return true;
    }

    private String parseRow(List<CollegeRank> collegeDataList, String data) throws IOException
    {
        String BEG = "<tr class=\"tbl2tbody\">";
        String END = "</tr>";
        int pos1 = data.indexOf(BEG);
        if (pos1 < 0) {
            return null;
        }
        int pos2 = data.indexOf(END, pos1);
        String tr = data.substring(pos1 + BEG.length(), pos2);
        String left = data.substring(pos2 + END.length());
        int count = 0;
        CollegeRank collegeData = new CollegeRank();
        while (tr != null && tr.trim().length() > 0)
        {
            tr = parseTd(tr, collegeData, count++);
        }
        collegeDataList.add(collegeData);
        return left;
    }

    private String parseTd(String data, CollegeRank collegeData, int col)
    {
        try
        {
            String BEG = "<td";
            String END = "</td>";
            int pos1 = data.indexOf(BEG);
            int pos2 = data.indexOf(END);
            String td = data.substring(pos1 + BEG.length(), pos2);
            pos1 = td.indexOf(">");
            td = td.substring(pos1 + 1);
            data = data.substring(pos2 + END.length());

            String message = "";
            pos1 = td.indexOf("<");
            if (pos1 >= 0)
            {
                pos2 = td.indexOf(">");
                int pos3 = td.indexOf("</");
                td = td.substring(pos2 + 1, pos3);
                message = td;
            }
            else
            {
                message = td;
            }

            if (col == 0)
            {
                collegeData.setUniversityName(message);
            }
            else if (col == 1)
            {
                collegeData.setProvinceName(message);
            }
            else if (col == 2)
            {
                if (message.equalsIgnoreCase("文科")) {
                    collegeData.setCategory(1);
                } else if (message.equalsIgnoreCase("理科")) {
                    collegeData.setCategory(2);
                } else {
                    collegeData.setCategory(0);
                }
            }
            else if (col == 4)
            {
                int iValue = Integer.parseInt(message);
                collegeData.setYear(iValue);
            }
            else if (col == 5)
            {
                float fValue = Float.parseFloat(message);
                int iValue = (int) fValue;
                collegeData.setMaxScore(iValue);
            }
            else if (col == 6)
            {
                float fValue = Float.parseFloat(message);
                int iValue = (int) fValue;
                collegeData.setAverageScore(iValue);
            }
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
