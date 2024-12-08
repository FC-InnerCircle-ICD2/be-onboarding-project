INSERT INTO SURVEY
VALUES (1, '정상적인 설문조사 데이터 저장', '설문조사 저장 테스트');
INSERT INTO survey_item (id, name, description, item_type, required, survey_id)
VALUES (1, '항목 이름 1', '항목 설명 1', 'LONG_TEXT', TRUE, 1),
       (2, '항목 이름 2', '항목 설명 2', 'SHORT_TEXT', FALSE, 1);
