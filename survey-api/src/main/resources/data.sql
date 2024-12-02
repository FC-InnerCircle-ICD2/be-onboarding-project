INSERT INTO survey_form (id, title, description, version, created_at) VALUES ('test1', 'title-1', 'description-1', 1, CURRENT_TIMESTAMP);
INSERT INTO survey_item (description,is_required,name,survey_form_id,type,id, created_at) VALUES ('item-description-1', false, 'item-name-1', 'test1', 'LONG_ANSWER', 'item-test-1', CURRENT_TIMESTAMP);

