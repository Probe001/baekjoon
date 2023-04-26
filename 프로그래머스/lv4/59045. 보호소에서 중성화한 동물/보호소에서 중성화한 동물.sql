-- 문제 요약
# ANIMAL_INS : 보호소에 들어온 동물 정보
-- ANIMAL_ID : 아이디
-- ANIMAL_TYPE : 동물 종
-- DATETIME : 보호시작일
-- INTAKE_CONDITION : 보호시작시 상태
-- NAME : 이름
-- SEX_UPON_INTAKE : 성별, 중성화 상태
# ANIMAL_OUTS : 입양 보낸 동물 정보
-- ANIMAL_ID : FK
-- ANIMAL_TYPE
-- DATETIME
-- NAME
-- SEX_UPON_OUTCOME
# 요구사항
-- 들어올 당시에는 중성화 안됐지만 나갈때는 중성화 된 동물 조회
-- 아이디, 생물종, 이름 출력
SELECT DISTINCT I.ANIMAL_ID, I.ANIMAL_TYPE, I.NAME
FROM ANIMAL_INS as I JOIN ANIMAL_OUTS AS O
USING (ANIMAL_ID)
WHERE I.SEX_UPON_INTAKE NOT IN ("Spayed Female", "Neutered Female", "Spayed Male", "Neutered Male")
AND O.SEX_UPON_OUTCOME IN ("Spayed Female", "Neutered Female", "Spayed Male", "Neutered Male");