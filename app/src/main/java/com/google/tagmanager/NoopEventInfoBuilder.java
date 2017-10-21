package com.google.tagmanager;

class NoopEventInfoBuilder implements EventInfoBuilder {
    NoopEventInfoBuilder() {
    }

    public DataLayerEventEvaluationInfoBuilder createDataLayerEventEvaluationInfoBuilder() {
        return new NoopDataLayerEventEvaluationInfoBuilder();
    }

    public MacroEvaluationInfoBuilder createMacroEvaluationInfoBuilder() {
        return new NoopMacroEvaluationInfoBuilder();
    }

    public void processEventInfo() {
    }
}
