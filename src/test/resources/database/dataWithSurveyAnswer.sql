-- CREATE TABLE IF NOT EXISTS SURVEY (
--     ID BIGINT PRIMARY KEY,
--     TITLE VARCHAR(255) NOT NULL,
--     DESCRIPTION VARCHAR(255) NOT NULL
-- );
-- CREATE TABLE IF NOT EXISTS survey_item (
--      id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- 기본 키, 시퀀스 방식으로 ID 자동 생성
--      name VARCHAR(255) NOT NULL,                            -- 항목 이름
--      description VARCHAR(255) NOT NULL,                     -- 항목 설명
--      item_type VARCHAR(255),                                -- 설문조사 유형 (EnumType.STRING)
--      required BOOLEAN,                                      -- 필수 여부
--      survey_id BIGINT,                                      -- 설문조사 ID (외래 키)
--      CONSTRAINT fk_survey FOREIGN KEY (survey_id) REFERENCES survey (id) ON DELETE CASCADE -- 외래 키 제약조건
-- );
-- CREATE TABLE IF NOT EXISTS survey_item_item_content_list (
--    survey_item_id BIGINT NOT NULL,                         -- survey_item의 ID (외래 키)
--    item_content_list TEXT,                                 -- 항목의 입력 내용
--    CONSTRAINT fk_survey_item FOREIGN KEY (survey_item_id) REFERENCES survey_item (id) ON DELETE CASCADE -- 외래 키 제약조건
-- );
-- CREATE TABLE IF NOT EXISTS survey_answer (
--     survey_id BIGINT NOT NULL,         -- 설문조사 ID
--     phone_number BIGINT NOT NULL,     -- 핸드폰 번호
--     username VARCHAR(255),            -- 사용자 이름
--     PRIMARY KEY (survey_id, phone_number) -- 복합키
-- );
-- CREATE TABLE IF NOT EXISTS survey_answer_map_value (
--     id BIGINT GENERATED BY DEFAULT AS SEQUENCE PRIMARY KEY, -- 기본 키, SEQUENCE 방식으로 자동 생성
--     question_id BIGINT NOT NULL,                           -- 질문 ID
--     survey_answer_surveyid BIGINT NOT NULL,                -- 설문조사 ID (외래 키)
--     survey_answer_phonenumber BIGINT NOT NULL,             -- 응답자 전화번호 (외래 키)
--     CONSTRAINT fk_survey_answer FOREIGN KEY (survey_answer_surveyid, survey_answer_phonenumber)
--         REFERENCES survey_answer (survey_id, phone_number) ON DELETE CASCADE
-- );
-- CREATE TABLE IF NOT EXISTS survey_answer_map_value_responses (
--     survey_answer_map_value_id BIGINT NOT NULL, -- 부모 테이블 ID
--     response TEXT NOT NULL,                     -- 응답 내용
--     CONSTRAINT fk_map_value_responses FOREIGN KEY (survey_answer_map_value_id)
--         REFERENCES survey_answer_map_value (id) ON DELETE CASCADE
-- );
INSERT INTO SURVEY
VALUES (1, '정상적인 설문조사 데이터 저장', '설문조사 저장 테스트');
INSERT INTO survey_item (id, name, description, item_type, required, survey_id)
VALUES (1, '항목 이름 1', '항목 설명 1', 'LONG_TEXT', TRUE, 1),
       (2, '항목 이름 2', '항목 설명 2', 'SHORT_TEXT', FALSE, 1);
INSERT INTO survey_answer (phone_number, username, survey_id)
VALUES (821012345678, 'John Doe', 1);
INSERT INTO survey_answer_map_value (id, survey_item_id, survey_answer_surveyid, survey_answer_phonenumber)
VALUES (1, 1001, 1, 821012345678);
INSERT INTO survey_answer_map_value_responses (survey_answer_map_value_id, responses)
VALUES (1, '응답 내용 1'),
       (1, '응답 내용 2'),
       (1, '응답 내용 3');
