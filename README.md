# uee_data

SELECT province_code,year,category,count(*) FROM subject_ranking group by province_code,year,category;
delete from subject_ranking where province_code='河南' and category = 1 and year = '2015';

http://localhost:8080/score/subject/2014/13/2

1   北京
2   天津
5   河北
6   河南
7   山东
8   山西
9   安徽
10  江西
13  湖北
14  湖南
15  广东
16  广西
17  云南
18  贵州
19  四川
20  陕西
23  黑龙江
24  吉林
25  辽宁
28  内蒙古
31  甘肃