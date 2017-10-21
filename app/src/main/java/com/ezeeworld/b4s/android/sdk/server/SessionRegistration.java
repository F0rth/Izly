package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class SessionRegistration {
    @JsonIgnore
    public Long _id;
    public boolean bAppActive;
    public boolean bImmediate;
    public Boolean bSessionClosed;
    public Date dDate;
    public double fEntryLatitude;
    public double fEntryLongitude;
    public double fExitLatitude;
    public double fExitLongitude;
    public int nAdvertisingTrackingEnabled;
    public Integer nDistance;
    public int nDuration;
    public int nEndCause;
    public int nInteractions;
    public Long nRangeEnteredTime;
    public int nRangeState;
    public int nSessionType;
    public int nVersion;
    public String sAcknowledgeData;
    public String sAdvertisingIdentifier;
    public String sAppId;
    public String sBeaconId;
    public String sCampaignId;
    public String sCategoryId;
    public String sClientId;
    public String sCountry;
    public String sCustomerId;
    public String sCustomerRef;
    public String sGlobalSessionId;
    public String sGroupId;
    public String sInteractionId;
    public String sNetwork;
    public String sOS;
    public String sSessionId;
    public String sShopId;
}
