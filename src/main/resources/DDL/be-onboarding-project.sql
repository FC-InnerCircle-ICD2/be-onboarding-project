-- Survey 테이블
CREATE TABLE Survey (
    survey_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- SurveyVersion 테이블
CREATE TABLE SurveyVersion (
    version_id INT AUTO_INCREMENT PRIMARY KEY,
    survey_id INT NOT NULL,
    version_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (survey_id) REFERENCES Survey(survey_id)
);

-- SurveyItem 테이블
CREATE TABLE SurveyItem (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    version_id INT NOT NULL,
    item_number INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    input_type VARCHAR(20) CHECK (input_type IN ('SHORT_TEXT', 'LONG_TEXT', 'SINGLE_CHOICE', 'MULTIPLE_CHOICE')) NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (version_id) REFERENCES SurveyVersion(version_id)
);

-- SurveyItemOption 테이블
CREATE TABLE SurveyItemOption (
    option_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    option_number INT NOT NULL,
    option_text VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (item_id) REFERENCES SurveyItem(item_id)
);

-- Response 테이블
CREATE TABLE Response (
    response_id INT AUTO_INCREMENT PRIMARY KEY,
    version_id INT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    respondent_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (version_id) REFERENCES SurveyVersion(version_id)
);

-- ResponseItem 테이블
CREATE TABLE ResponseItem (
    response_item_id INT AUTO_INCREMENT PRIMARY KEY,
    response_id INT NOT NULL,
    item_id INT NOT NULL,
    `value` TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (response_id) REFERENCES Response(response_id),
    FOREIGN KEY (item_id) REFERENCES SurveyItem(item_id)
);
