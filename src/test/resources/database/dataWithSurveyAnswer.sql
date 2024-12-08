INSERT INTO SURVEY
VALUES (1, '정상적인 설문조사 데이터 저장', '설문조사 저장 테스트');
INSERT INTO survey_item (id, name, description, item_type, required, survey_id)
VALUES (1, '항목 이름 1', '항목 설명 1', 'LONG_TEXT', TRUE, 1),
       (2, '항목 이름 2', '항목 설명 2', 'SHORT_TEXT', FALSE, 1);
INSERT INTO survey_answer (phone_number, username, survey_id)
VALUES (821012345678, 'John Doe', 1);
INSERT INTO survey_answer_map_value (id, survey_item_id, survey_answer_survey_id, survey_answer_phone_number)
VALUES (1, 1001, 1, 821012345678);
INSERT INTO survey_answer_map_value_responses (survey_answer_map_value_id, responses)
VALUES (1, '응답 내용 1'),
       (1, '응답 내용 2'),
       (1, '응답 내용 3');
