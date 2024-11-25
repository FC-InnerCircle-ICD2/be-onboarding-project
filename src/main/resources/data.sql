INSERT INTO servey(name, description) VALUES ('[패스트캠퍼스] INNER CIRCLE 풀스택 개발 코스 사전 조사', '안녕하세요? 😊 패스트캠퍼스입니다. INNER CIRCLE 풀스택 개발 Course에 합격하신 것을 진심으로 축하드립니다.');

INSERT INTO question(name, description, type, required, servey_id) VALUES ('성함을 작성해 주세요.', null, 'SHORT_TYPE', 1, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('연락처를 작성해 주세요.', 'ex) 010-0000-0000', 'SHORT_TYPE', 1, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('이메일 주소를 작성해 주세요.', null, 'SHORT_TYPE', 1, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('최종 과정 합류 의사를 선택해 주세요.', null, 'SINGLE_LIST', 1, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('본 과정은 평일/주말 저녁 진행되는 온라인(비대면)과정입니다. 확인하셨습니까?', '- 실시간 비대면 온라인 기간 :   2024.11.23 (토) ~ 2025.03.08 (토)  *공휴일 제외 (ZOOM)', 'SINGLE_LIST', 1, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('본 과정은 참여도가 낮을 경우 수료기준에 따라 제적처리 될 수 있습니다. 확인하셨습니까?', null, 'SINGLE_LIST', 0, 1);
INSERT INTO question(name, description, type, required, servey_id) VALUES ('본 교육 과정을 지원하게 된 사유는 무엇인가요?', null, 'MULTI_LIST', 1, 1);

INSERT INTO option(number, question_id) VALUES (1, 4);
INSERT INTO option(number, question_id) VALUES (2, 4);
INSERT INTO option(number, question_id) VALUES (1, 5);
INSERT INTO option(number, question_id) VALUES (1, 6);
INSERT INTO option(number, question_id) VALUES (1, 7);
INSERT INTO option(number, question_id) VALUES (2, 7);
INSERT INTO option(number, question_id) VALUES (3, 7);
INSERT INTO option(number, question_id) VALUES (4, 7);
INSERT INTO option(number, question_id) VALUES (5, 7);
INSERT INTO option(number, question_id) VALUES (6, 7);
INSERT INTO option(number, question_id) VALUES (7, 7);
