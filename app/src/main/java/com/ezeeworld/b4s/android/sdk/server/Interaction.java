package com.ezeeworld.b4s.android.sdk.server;

import java.util.Date;

public class Interaction {
    public String[] aCategoryIds;
    public String[] aGroupIds;
    public String[] aShopIds;
    public boolean bCustomPushEnabled;
    public boolean bFridayEnabled;
    public boolean bGeoNotifyIfBeacon;
    public boolean bIncomingInteraction;
    public boolean bMondayEnabled;
    public boolean bOutgoingInteraction;
    public boolean bSaturdayEnabled;
    public boolean bSendOnlyIfNotOpened;
    public boolean bSundayEnabled;
    public boolean bThursdayEnabled;
    public boolean bTuesdayEnabled;
    public boolean bWednesdayEnabled;
    public Date dEndDate;
    public Date dStartDate;
    public int nAfterScanTimeout;
    public int nDistModel;
    public int nEndTime;
    public int nHoursFrom;
    public int nHoursTo;
    public int nNextWaveDelay;
    public int nNotificationPresenceDuration;
    public int nNotificationsMaxCount;
    public int nRangeMax;
    public int nRangeMin;
    public int nRangeOut;
    public int nStartTime;
    public Conditions oConditions;
    public String sCampaignId;
    public String sCampaignName;
    public String sCampaignType;
    public String sChecksum;
    public String sInteractionId;
    public String sName;
    public String sNotificationType;
    public String sPushData;
    public String sPushText;

    public static class Condition {
        public boolean bDefaultCondition;
        public boolean bNot;
        public int nTimeout;
        public int nType;
        public String sObjectId;
    }

    public static class Conditions {
        public Condition[] aConditions;
        public int nVersion;
        public String sScheme;
    }
}
