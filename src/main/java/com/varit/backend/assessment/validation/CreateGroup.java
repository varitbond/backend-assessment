package com.varit.backend.assessment.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, CreateGroup.class})
public interface CreateGroup {
}
