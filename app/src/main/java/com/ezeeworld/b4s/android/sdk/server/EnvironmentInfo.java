package com.ezeeworld.b4s.android.sdk.server;

import java.util.List;

public class EnvironmentInfo {
    public List<Category> categories;
    public List<Group> groups;
    public String md5;
    public List<String> udids;

    static class Request {
        public String md5;
        public String sAppId;
        public String sClientId;

        Request() {
        }
    }
}
