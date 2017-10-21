package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlz;

public final class zzl {
    public static zza<Boolean> zzaVY = zza.zzm("measurement.service_enabled", true);
    public static zza<Boolean> zzaVZ = zza.zzm("measurement.service_client_enabled", true);
    public static zza<String> zzaWa = zza.zzl("measurement.log_tag", "GMPM", "GMPM-SVC");
    public static zza<Long> zzaWb = zza.zze("measurement.ad_id_cache_time", 10000);
    public static zza<Long> zzaWc = zza.zze("measurement.monitoring.sample_period_millis", 86400000);
    public static zza<Long> zzaWd = zza.zze("measurement.config.cache_time", 86400000);
    public static zza<String> zzaWe = zza.zzN("measurement.config.url_scheme", "https");
    public static zza<String> zzaWf = zza.zzN("measurement.config.url_authority", "app-measurement.com");
    public static zza<Integer> zzaWg = zza.zzD("measurement.upload.max_bundles", 100);
    public static zza<Integer> zzaWh = zza.zzD("measurement.upload.max_batch_size", 65536);
    public static zza<Integer> zzaWi = zza.zzD("measurement.upload.max_bundle_size", 65536);
    public static zza<Integer> zzaWj = zza.zzD("measurement.upload.max_events_per_bundle", 1000);
    public static zza<Integer> zzaWk = zza.zzD("measurement.upload.max_events_per_day", 100000);
    public static zza<Integer> zzaWl = zza.zzD("measurement.upload.max_public_events_per_day", 50000);
    public static zza<Integer> zzaWm = zza.zzD("measurement.upload.max_conversions_per_day", 500);
    public static zza<Integer> zzaWn = zza.zzD("measurement.store.max_stored_events_per_app", 100000);
    public static zza<String> zzaWo = zza.zzN("measurement.upload.url", "https://app-measurement.com/a");
    public static zza<Long> zzaWp = zza.zze("measurement.upload.backoff_period", 43200000);
    public static zza<Long> zzaWq = zza.zze("measurement.upload.window_interval", 3600000);
    public static zza<Long> zzaWr = zza.zze("measurement.upload.interval", 3600000);
    public static zza<Long> zzaWs = zza.zze("measurement.upload.stale_data_deletion_interval", 86400000);
    public static zza<Long> zzaWt = zza.zze("measurement.upload.initial_upload_delay_time", 15000);
    public static zza<Long> zzaWu = zza.zze("measurement.upload.retry_time", 1800000);
    public static zza<Integer> zzaWv = zza.zzD("measurement.upload.retry_count", 6);
    public static zza<Long> zzaWw = zza.zze("measurement.upload.max_queue_time", 2419200000L);
    public static zza<Integer> zzaWx = zza.zzD("measurement.lifetimevalue.max_currency_tracked", 4);
    public static zza<Long> zzaWy = zza.zze("measurement.service_client.idle_disconnect_millis", 5000);

    public static final class zza<V> {
        private final V zzSA;
        private final zzlz<V> zzSB;
        private V zzSC;
        private final String zzvs;

        private zza(String str, zzlz<V> com_google_android_gms_internal_zzlz_V, V v) {
            zzx.zzz(com_google_android_gms_internal_zzlz_V);
            this.zzSB = com_google_android_gms_internal_zzlz_V;
            this.zzSA = v;
            this.zzvs = str;
        }

        static zza<Integer> zzD(String str, int i) {
            return zzo(str, i, i);
        }

        static zza<String> zzN(String str, String str2) {
            return zzl(str, str2, str2);
        }

        static zza<Long> zzb(String str, long j, long j2) {
            return new zza(str, zzlz.zza(str, Long.valueOf(j2)), Long.valueOf(j));
        }

        static zza<Boolean> zzb(String str, boolean z, boolean z2) {
            return new zza(str, zzlz.zzk(str, z2), Boolean.valueOf(z));
        }

        static zza<Long> zze(String str, long j) {
            return zzb(str, j, j);
        }

        static zza<String> zzl(String str, String str2, String str3) {
            return new zza(str, zzlz.zzv(str, str3), str2);
        }

        static zza<Boolean> zzm(String str, boolean z) {
            return zzb(str, z, z);
        }

        static zza<Integer> zzo(String str, int i, int i2) {
            return new zza(str, zzlz.zza(str, Integer.valueOf(i2)), Integer.valueOf(i));
        }

        public final V get() {
            return this.zzSC != null ? this.zzSC : (zzd.zzakE && zzlz.isInitialized()) ? this.zzSB.zzpX() : this.zzSA;
        }

        public final V get(V v) {
            return this.zzSC != null ? this.zzSC : v == null ? (zzd.zzakE && zzlz.isInitialized()) ? this.zzSB.zzpX() : this.zzSA : v;
        }

        public final String getKey() {
            return this.zzvs;
        }
    }
}
