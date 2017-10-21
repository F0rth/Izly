package com.thewingitapp.thirdparties.wingitlib.model;

import java.util.List;

public class WGTimelineEvents extends WGTimelineEventsDTO {
    public WGDetectedCity getDetectedCity() {
        return this.detectedCity;
    }

    public List<WGEvent> getEvents() {
        return this.events;
    }

    public Integer getLimitValue() {
        return this.limitValue;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public Integer getOffsetDay() {
        return this.offsetDay;
    }

    public Integer getTotalNumber() {
        return this.totalNumber;
    }
}
