package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteLog {
    @JsonProperty("a")
    public String applicationId;
    @JsonProperty("c")
    public String clientId;
    @JsonProperty("u")
    public String deviceId;
    @JsonProperty("l")
    public int lineNumber;
    @JsonProperty("m")
    public String message;
    @JsonProperty("f")
    public String methodName;
    @JsonIgnore
    public int priority;
}
