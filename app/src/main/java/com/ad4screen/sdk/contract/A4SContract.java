package com.ad4screen.sdk.contract;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

public class A4SContract {

    public interface BeaconGeofenceGroupsColumns {
        public static final String SERVER_ID = "server_id";
    }

    public static class BeaconGeofenceGroups implements BaseColumns, BeaconGeofenceGroupsColumns {
        private BeaconGeofenceGroups() {
        }

        public static final Uri getBeaconsContentFilterUri(Context context) {
            return Uri.withAppendedPath(getContentUri(context), "beacon_groups/filter");
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "beacon_geofence_groups");
        }

        public static final Uri getGeofencesContentFilterUri(Context context) {
            return Uri.withAppendedPath(getContentUri(context), "geofence_groups/filter");
        }
    }

    public interface BeaconGroupsColumns {
        public static final String BEACON_ID = "beacon_id";
        public static final String GROUP_ID = "group_id";
    }

    public static class BeaconGroups implements BaseColumns, BeaconGroupsColumns {
        private BeaconGroups() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "beacon_groups");
        }

        public static final Uri getCustomContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "custom_beacon_groups");
        }
    }

    public interface BeaconParamsColumns {
        public static final String BEACON_ID = "beacon_id";
        public static final String KEY = "param_key";
        public static final String VALUE = "param_value";
    }

    public static class BeaconParams implements BaseColumns, BeaconParamsColumns {
        public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/beacon_param";
        public static String CONTENT_TYPE = "vnd.android.cursor.dir/beacon_param";

        private BeaconParams() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "beacon_params");
        }
    }

    public interface BeaconsColumns {
        public static final String DETECTED_TIME = "detected_time";
        public static final String EXTERNAL_ID = "external_id";
        public static final String MAJOR = "major";
        public static final String MINOR = "minor";
        public static final String NAME = "name";
        public static final String NOTIFIED_TIME = "notified_time";
        public static final String SERVER_ID = "server_id";
        public static final String UUID = "uuid";
    }

    public static class Beacons implements BaseColumns, BeaconsColumns {
        public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/beacon";
        public static String CONTENT_TYPE = "vnd.android.cursor.dir/beacon";

        private Beacons() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "beacons");
        }

        public static final Uri getGroupsContentFilterUri(Context context) {
            return Uri.withAppendedPath(getContentUri(context), "beacon_groups/filter");
        }
    }

    public interface GeofenceGroupsColumns {
        public static final String GEOFENCE_ID = "geofence_id";
        public static final String GROUP_ID = "group_id";
    }

    public static class GeofenceGroups implements BaseColumns, GeofenceGroupsColumns {
        private GeofenceGroups() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "geofence_groups");
        }

        public static final Uri getCustomContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "custom_geofence_groups");
        }
    }

    public interface GeofenceParamsColumns {
        public static final String GEOFENCE_ID = "geofence_id";
        public static final String KEY = "param_key";
        public static final String VALUE = "param_value";
    }

    public static class GeofenceParams implements BaseColumns, GeofenceParamsColumns {
        public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/geofence_param";
        public static String CONTENT_TYPE = "vnd.android.cursor.dir/geofence_param";

        private GeofenceParams() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "geofence_params");
        }
    }

    public interface GeofencesColumns {
        public static final String DETECTED_TIME = "detected_time";
        public static final String EXTERNAL_ID = "external_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String NAME = "name";
        public static final String NOTIFIED_TIME = "notified_time";
        public static final String RADIUS = "radius";
        public static final String SERVER_ID = "server_id";
    }

    public static class Geofences implements BaseColumns, GeofencesColumns {
        public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/geofence";
        public static String CONTENT_TYPE = "vnd.android.cursor.dir/geofence";

        private Geofences() {
        }

        public static final Uri getContentUri(Context context) {
            return Uri.withAppendedPath(A4SContract.getAuthorityUri(context), "geofences");
        }

        public static final Uri getGroupsContentFilterUri(Context context) {
            return Uri.withAppendedPath(getContentUri(context), "geofence_groups/filter");
        }
    }

    public static final String getAuthority(Context context) {
        return context.getPackageName() + ".ad4screen.provider";
    }

    public static final Uri getAuthorityUri(Context context) {
        return Uri.parse("content://" + getAuthority(context));
    }
}
