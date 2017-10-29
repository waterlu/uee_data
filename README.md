update college_ranking set province_code = 5 where province_name = '河北';
update college_ranking set province_code = 6 where province_name = '河南';
update college_ranking set province_code = 7 where province_name = '山东';
update college_ranking set province_code = 8 where province_name = '山西';
update college_ranking set province_code = 9 where province_name = '安徽';
update college_ranking set province_code = 10 where province_name = '江西';
update college_ranking set province_code = 13 where province_name = '湖北';
update college_ranking set province_code = 14 where province_name = '湖南';
update college_ranking set province_code = 15 where province_name = '广东';
update college_ranking set province_code = 19 where province_name = '四川';

SELECT province_code, year, university_name, COUNT(average_score)
FROM college_ranking
WHERE delete_flag = 0 AND category = 2
GROUP BY province_code, year, university_name


        SELECT province_code, year, university_name, COUNT(average_score) AS c
        FROM college_ranking
        WHERE delete_flag = 0 AND category = 2
        GROUP BY province_code, year, university_name
        ORDER BY c DESC, province_code