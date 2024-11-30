package net.gentledot.survey.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Slf4j
@TestComponent
public class IntegrationTestDatabaseClearing {

    private final DataSource dataSource;

    public IntegrationTestDatabaseClearing(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public void clearAllH2Database() {

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.execute("SET REFERENTIAL_INTEGRITY FALSE");

            statement.executeUpdate("TRUNCATE TABLE survey");
            statement.executeUpdate("TRUNCATE TABLE survey_question");
            statement.executeUpdate("TRUNCATE TABLE survey_question_option");
            statement.executeUpdate("TRUNCATE TABLE survey_answer");
            statement.executeUpdate("TRUNCATE TABLE survey_answer_submission");

            statement.executeUpdate("ALTER TABLE survey_question ALTER COLUMN id RESTART WITH 1");
            statement.executeUpdate("ALTER TABLE survey_question_option ALTER COLUMN id RESTART WITH 1");
            statement.executeUpdate("ALTER TABLE survey_answer ALTER COLUMN id RESTART WITH 1");
            statement.executeUpdate("ALTER TABLE survey_answer_submission ALTER COLUMN id RESTART WITH 1");

            statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
