package com.survey.www.commons.config.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroup.NotNullGroup.class, ValidationGroup.NotBlankGroup.class, ValidationGroup.PatternGroup.class})
public interface ValidationSequence {
}
