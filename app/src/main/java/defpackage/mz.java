package defpackage;

import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.i18n.MessageBundle;

final class mz implements nk {
    mz() {
    }

    public final ni a(kr krVar, JSONObject jSONObject) throws JSONException {
        long j;
        int optInt = jSONObject.optInt("settings_version", 0);
        int optInt2 = jSONObject.optInt("cache_duration", 3600);
        JSONObject jSONObject2 = jSONObject.getJSONObject("app");
        String string = jSONObject2.getString("identifier");
        String string2 = jSONObject2.getString("status");
        String string3 = jSONObject2.getString("url");
        String string4 = jSONObject2.getString("reports_url");
        String string5 = jSONObject2.getString("ndk_reports_url");
        boolean optBoolean = jSONObject2.optBoolean("update_required", false);
        mr mrVar = null;
        if (jSONObject2.has("icon") && jSONObject2.getJSONObject("icon").has("hash")) {
            jSONObject2 = jSONObject2.getJSONObject("icon");
            mrVar = new mr(jSONObject2.getString("hash"), jSONObject2.getInt("width"), jSONObject2.getInt("height"));
        }
        mt mtVar = new mt(string, string2, string3, string4, string5, optBoolean, mrVar);
        JSONObject jSONObject3 = jSONObject.getJSONObject("session");
        ne neVar = new ne(jSONObject3.optInt("log_buffer_size", 64000), jSONObject3.optInt("max_chained_exception_depth", 8), jSONObject3.optInt("max_custom_exception_events", 64), jSONObject3.optInt("max_custom_key_value_pairs", 64), jSONObject3.optInt("identifier_mask", CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV), jSONObject3.optBoolean("send_session_without_crash", false), jSONObject3.optInt("max_complete_sessions_count", 4));
        JSONObject jSONObject4 = jSONObject.getJSONObject("prompt");
        nd ndVar = new nd(jSONObject4.optString(MessageBundle.TITLE_ENTRY, "Send Crash Report?"), jSONObject4.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), jSONObject4.optString("send_button_title", "Send"), jSONObject4.optBoolean("show_cancel_button", true), jSONObject4.optString("cancel_button_title", "Don't Send"), jSONObject4.optBoolean("show_always_send_button", true), jSONObject4.optString("always_send_button_title", "Always Send"));
        JSONObject jSONObject5 = jSONObject.getJSONObject("features");
        nb nbVar = new nb(jSONObject5.optBoolean("prompt_enabled", false), jSONObject5.optBoolean("collect_logged_exceptions", true), jSONObject5.optBoolean("collect_reports", true), jSONObject5.optBoolean("collect_analytics", false));
        JSONObject jSONObject6 = jSONObject.getJSONObject("analytics");
        mq mqVar = new mq(jSONObject6.optString("url", "https://e.crashlytics.com/spi/v2/events"), jSONObject6.optInt("flush_interval_secs", 600), jSONObject6.optInt("max_byte_size_per_file", lv.MAX_BYTE_SIZE_PER_FILE), jSONObject6.optInt("max_file_count_per_send", 1), jSONObject6.optInt("max_pending_send_file_count", 100), jSONObject6.optBoolean("forward_to_google_analytics", false), jSONObject6.optBoolean("analytics_include_purchase_events_in_forwarded_events", false), jSONObject6.optBoolean("track_custom_events", true), jSONObject6.optBoolean("track_predefined_events", true), jSONObject6.optInt("sampling_rate", 1), jSONObject6.optBoolean("flush_on_background", true));
        JSONObject jSONObject7 = jSONObject.getJSONObject("beta");
        mu muVar = new mu(jSONObject7.optString("update_endpoint", nj.a), jSONObject7.optInt("update_suspend_duration", 3600));
        long j2 = (long) optInt2;
        if (jSONObject.has("expires_at")) {
            j = jSONObject.getLong("expires_at");
        } else {
            j = krVar.a() + (j2 * 1000);
        }
        return new ni(j, mtVar, neVar, ndVar, nbVar, mqVar, muVar, optInt, optInt2);
    }
}
