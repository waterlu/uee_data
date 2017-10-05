package cn.lu.uee.data.service.impl;

import cn.lu.uee.data.domain.SubjectRank;
import cn.lu.uee.data.mapper.SubjectRankingMapper;
import cn.lu.uee.data.service.SubjRankService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2017/4/23.
 */
@Service
public class SubjRankServiceImpl implements SubjRankService {

    private final Logger logger = LoggerFactory.getLogger(SubjRankServiceImpl.class);

    @Value("${sina.kaoshi.addr}")
    private String uri;

    @Autowired
    private SubjectRankingMapper subjectScoreMapper;

    @Override
    public int loadSinaData(int year, int province, int category, int page) {
        String sYear = Integer.toString(year);
        String provId = Integer.toString(province);
        String wl = Integer.toString(category);

        String urlName = uri.replaceAll("#year", sYear).replaceAll("#provid", provId).replaceAll("#wl", wl);

        URL url = null;
        int count = 0;
        List<SubjectRank> subjDataList = new ArrayList<>();
        try {
            for(int i=page; i<500; i++) {
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
                parseTable(subjDataList, buffer.toString());
                if (subjDataList.size() == 0) {
                    break;
                }
                for (SubjectRank subjectScore : subjDataList) {
                    subjectScoreMapper.insert(subjectScore);
                }
                count += subjDataList.size();
                subjDataList.clear();
            }
            logger.info("done");
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return count;
        }
    }

    private boolean parseTable(List<SubjectRank> subjDataList, String data) throws IOException
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
            data = parseRow(subjDataList, data);
        }
        return true;
    }

    private String parseRow(List<SubjectRank> subjDataList, String data) throws IOException
    {
        String BEG = "<tr>";
        String END = "</tr>";
        int pos1 = data.indexOf(BEG);
        if (pos1 < 0) {
            return null;
        }
        int pos2 = data.indexOf(END, pos1);
        String tr = data.substring(pos1 + BEG.length(), pos2);
        String left = data.substring(pos2 + END.length());
        int count = 0;
        SubjectRank subjData = new SubjectRank();
        while (tr != null && tr.trim().length() > 0)
        {
            tr = parseTd(tr, subjData, count++);
        }
        subjDataList.add(subjData);
        return left;
    }

    private String parseTd(String data, SubjectRank subjData, int col)
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
                subjData.setSubjCode(message);
            }
            else if (col == 1)
            {
                subjData.setUnivCode(message);
            }
            else if (col == 2)
            {
                float fValue = Float.parseFloat(message);
                int iValue = (int) fValue;
                subjData.maxScore = iValue;
            }
            else if (col == 3)
            {
                float fValue = Float.parseFloat(message);
                int iValue = (int) fValue;
                subjData.averageScore = iValue;
            }
            else if (col == 4)
            {
                subjData.setProvCode(message);
            }
            else if (col == 5)
            {
                if (message.equalsIgnoreCase("文科")) {
                    subjData.setCategory(1);
                } else if (message.equalsIgnoreCase("理科")) {
                    subjData.setCategory(2);
                } else {
                    subjData.setCategory(0);
                }
            }
            else if (col == 7)
            {
                int iValue = Integer.parseInt(message);
                subjData.year = iValue;
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
