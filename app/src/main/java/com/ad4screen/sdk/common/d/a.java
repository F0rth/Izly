package com.ad4screen.sdk.common.d;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.BasePlugin;

public class a {
    public static <T> BasePlugin a(String str, int i) {
        try {
            BasePlugin basePlugin = (BasePlugin) Class.forName(str).newInstance();
            Log.debug("PluginLoader|" + str + " loaded");
            if (basePlugin == null || basePlugin.getPluginVersion() == i) {
                return basePlugin;
            }
            Log.error("PluginLoader|" + str + " version is too old ! Please update it");
            return null;
        } catch (ClassNotFoundException e) {
            Log.debug("PluginLoader|" + str + "ClassNotFoundException " + e);
            return null;
        } catch (InstantiationException e2) {
            Log.debug("PluginLoader|" + str + "InstantiationException " + e2);
            return null;
        } catch (IllegalAccessException e3) {
            Log.debug("PluginLoader|" + str + "IllegalAccessException " + e3);
            return null;
        }
    }
}
