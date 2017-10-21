package com.google.tagmanager;

import com.google.tagmanager.ResourceUtil.ExpandedFunctionCall;
import java.util.Set;

class NoopRuleEvaluationStepInfoBuilder implements RuleEvaluationStepInfoBuilder {
    NoopRuleEvaluationStepInfoBuilder() {
    }

    public ResolvedRuleBuilder createResolvedRuleBuilder() {
        return new NoopResolvedRuleBuilder();
    }

    public void setEnabledFunctions(Set<ExpandedFunctionCall> set) {
    }
}
