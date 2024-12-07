package com.survey.www.commons.config.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FunctionContributorConfig implements FunctionContributor {
  @Override
  public void contributeFunctions(FunctionContributions functionContributions) {
    functionContributions.getFunctionRegistry()
                         .register("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

    functionContributions.getFunctionRegistry()
                         .register("date_format", new StandardSQLFunction("date_format", StandardBasicTypes.STRING));
  }
}
