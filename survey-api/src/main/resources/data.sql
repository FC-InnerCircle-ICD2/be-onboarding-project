INSERT INTO survey_form (id, title, description, version, created_at) VALUES ('test1', 'title-1', 'description-1', 1, CURRENT_TIMESTAMP);

INSERT INTO survey_item (id, name, is_required, type, description, survey_form_id, created_at) VALUES ('item-test-1', 'item-name-1', false, 'SINGLE_SELECT', 'item-description-1', 'test1',  CURRENT_TIMESTAMP);

-- INSERT INTO survey_item (description,is_required,name,survey_form_id,type,id, created_at) VALUES ('item-description-1', false, 'item-name-1', 'test1', 'LONG_ANSWER', 'item-test-1', CURRENT_TIMESTAMP);

INSERT INTO survey_option (id, name, survey_item_id, created_at) VALUES ('option-1', '떡볶이', 'item-test-1', CURRENT_TIMESTAMP);
INSERT INTO survey_option (id, name, survey_item_id, created_at) VALUES ('option-2','치킨', 'item-test-1', CURRENT_TIMESTAMP);
