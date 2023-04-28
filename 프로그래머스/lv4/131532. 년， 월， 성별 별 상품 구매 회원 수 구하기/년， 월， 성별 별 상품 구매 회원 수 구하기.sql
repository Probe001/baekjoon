-- 코드를 입력하세요
SELECT YEAR(S.SALES_DATE) AS YEAR, MONTH(S.SALES_DATE) AS MONTH, U.GENDER AS GENDER, COUNT(DISTINCT USER_ID) AS USERS
FROM USER_INFO AS U JOIN ONLINE_SALE AS S
USING (USER_ID)
WHERE U.GENDER IS NOT NULL
GROUP BY DATE_FORMAT(S.SALES_DATE, "%Y-%m"), U.GENDER
ORDER BY YEAR, MONTH, GENDER