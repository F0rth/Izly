package com.google.android.gms.measurement.internal;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzpz.zza;
import com.google.android.gms.internal.zzpz.zzb;
import com.google.android.gms.internal.zzpz.zze;
import com.google.android.gms.internal.zzqb;
import com.google.android.gms.internal.zzqb.zzf;
import com.google.android.gms.internal.zzqb.zzg;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

class zzc extends zzz {
    zzc(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    private Boolean zza(zzb com_google_android_gms_internal_zzpz_zzb, zzqb.zzb com_google_android_gms_internal_zzqb_zzb, long j) {
        Boolean zzac;
        if (com_google_android_gms_internal_zzpz_zzb.zzaZz != null) {
            zzac = new zzs(com_google_android_gms_internal_zzpz_zzb.zzaZz).zzac(j);
            if (zzac == null) {
                return null;
            }
            if (!zzac.booleanValue()) {
                return Boolean.valueOf(false);
            }
        }
        Set hashSet = new HashSet();
        for (com.google.android.gms.internal.zzpz.zzc com_google_android_gms_internal_zzpz_zzc : com_google_android_gms_internal_zzpz_zzb.zzaZx) {
            if (TextUtils.isEmpty(com_google_android_gms_internal_zzpz_zzc.zzaZE)) {
                zzAo().zzCF().zzj("null or empty param name in filter. event", com_google_android_gms_internal_zzqb_zzb.name);
                return null;
            }
            hashSet.add(com_google_android_gms_internal_zzpz_zzc.zzaZE);
        }
        Map arrayMap = new ArrayMap();
        for (com.google.android.gms.internal.zzqb.zzc com_google_android_gms_internal_zzqb_zzc : com_google_android_gms_internal_zzqb_zzb.zzbae) {
            if (hashSet.contains(com_google_android_gms_internal_zzqb_zzc.name)) {
                if (com_google_android_gms_internal_zzqb_zzc.zzbai != null) {
                    arrayMap.put(com_google_android_gms_internal_zzqb_zzc.name, com_google_android_gms_internal_zzqb_zzc.zzbai);
                } else if (com_google_android_gms_internal_zzqb_zzc.zzaZo != null) {
                    arrayMap.put(com_google_android_gms_internal_zzqb_zzc.name, com_google_android_gms_internal_zzqb_zzc.zzaZo);
                } else if (com_google_android_gms_internal_zzqb_zzc.zzamJ != null) {
                    arrayMap.put(com_google_android_gms_internal_zzqb_zzc.name, com_google_android_gms_internal_zzqb_zzc.zzamJ);
                } else {
                    zzAo().zzCF().zze("Unknown value for param. event, param", com_google_android_gms_internal_zzqb_zzb.name, com_google_android_gms_internal_zzqb_zzc.name);
                    return null;
                }
            }
        }
        for (com.google.android.gms.internal.zzpz.zzc com_google_android_gms_internal_zzpz_zzc2 : com_google_android_gms_internal_zzpz_zzb.zzaZx) {
            CharSequence charSequence = com_google_android_gms_internal_zzpz_zzc2.zzaZE;
            if (TextUtils.isEmpty(charSequence)) {
                zzAo().zzCF().zzj("Event has empty param name. event", com_google_android_gms_internal_zzqb_zzb.name);
                return null;
            }
            Object obj = arrayMap.get(charSequence);
            if (obj instanceof Long) {
                if (com_google_android_gms_internal_zzpz_zzc2.zzaZC == null) {
                    zzAo().zzCF().zze("No number filter for long param. event, param", com_google_android_gms_internal_zzqb_zzb.name, charSequence);
                    return null;
                }
                zzac = new zzs(com_google_android_gms_internal_zzpz_zzc2.zzaZC).zzac(((Long) obj).longValue());
                if (zzac == null) {
                    return null;
                }
                if (!zzac.booleanValue()) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof Float) {
                if (com_google_android_gms_internal_zzpz_zzc2.zzaZC == null) {
                    zzAo().zzCF().zze("No number filter for float param. event, param", com_google_android_gms_internal_zzqb_zzb.name, charSequence);
                    return null;
                }
                zzac = new zzs(com_google_android_gms_internal_zzpz_zzc2.zzaZC).zzi(((Float) obj).floatValue());
                if (zzac == null) {
                    return null;
                }
                if (!zzac.booleanValue()) {
                    return Boolean.valueOf(false);
                }
            } else if (obj instanceof String) {
                if (com_google_android_gms_internal_zzpz_zzc2.zzaZB == null) {
                    zzAo().zzCF().zze("No string filter for String param. event, param", com_google_android_gms_internal_zzqb_zzb.name, charSequence);
                    return null;
                }
                zzac = new zzae(com_google_android_gms_internal_zzpz_zzc2.zzaZB).zzfp((String) obj);
                if (zzac == null) {
                    return null;
                }
                if (!zzac.booleanValue()) {
                    return Boolean.valueOf(false);
                }
            } else if (obj == null) {
                zzAo().zzCK().zze("Missing param for filter. event, param", com_google_android_gms_internal_zzqb_zzb.name, charSequence);
                return Boolean.valueOf(false);
            } else {
                zzAo().zzCF().zze("Unknown param type. event, param", com_google_android_gms_internal_zzqb_zzb.name, charSequence);
                return null;
            }
        }
        return Boolean.valueOf(true);
    }

    private Boolean zza(zze com_google_android_gms_internal_zzpz_zze, zzg com_google_android_gms_internal_zzqb_zzg) {
        Boolean bool = null;
        com.google.android.gms.internal.zzpz.zzc com_google_android_gms_internal_zzpz_zzc = com_google_android_gms_internal_zzpz_zze.zzaZM;
        if (com_google_android_gms_internal_zzpz_zzc == null) {
            zzAo().zzCF().zzj("Missing property filter. property", com_google_android_gms_internal_zzqb_zzg.name);
            return bool;
        } else if (com_google_android_gms_internal_zzqb_zzg.zzbai != null) {
            if (com_google_android_gms_internal_zzpz_zzc.zzaZC != null) {
                return new zzs(com_google_android_gms_internal_zzpz_zzc.zzaZC).zzac(com_google_android_gms_internal_zzqb_zzg.zzbai.longValue());
            }
            zzAo().zzCF().zzj("No number filter for long property. property", com_google_android_gms_internal_zzqb_zzg.name);
            return bool;
        } else if (com_google_android_gms_internal_zzqb_zzg.zzaZo != null) {
            if (com_google_android_gms_internal_zzpz_zzc.zzaZC != null) {
                return new zzs(com_google_android_gms_internal_zzpz_zzc.zzaZC).zzi(com_google_android_gms_internal_zzqb_zzg.zzaZo.floatValue());
            }
            zzAo().zzCF().zzj("No number filter for float property. property", com_google_android_gms_internal_zzqb_zzg.name);
            return bool;
        } else if (com_google_android_gms_internal_zzqb_zzg.zzamJ == null) {
            zzAo().zzCF().zzj("User property has no value, property", com_google_android_gms_internal_zzqb_zzg.name);
            return bool;
        } else if (com_google_android_gms_internal_zzpz_zzc.zzaZB != null) {
            return new zzae(com_google_android_gms_internal_zzpz_zzc.zzaZB).zzfp(com_google_android_gms_internal_zzqb_zzg.zzamJ);
        } else {
            if (com_google_android_gms_internal_zzpz_zzc.zzaZC == null) {
                zzAo().zzCF().zzj("No string or number filter defined. property", com_google_android_gms_internal_zzqb_zzg.name);
                return bool;
            }
            zzs com_google_android_gms_measurement_internal_zzs = new zzs(com_google_android_gms_internal_zzpz_zzc.zzaZC);
            if (com_google_android_gms_internal_zzpz_zzc.zzaZC.zzaZG.booleanValue()) {
                if (zzeR(com_google_android_gms_internal_zzqb_zzg.zzamJ)) {
                    try {
                        float parseFloat = Float.parseFloat(com_google_android_gms_internal_zzqb_zzg.zzamJ);
                        if (!Float.isInfinite(parseFloat)) {
                            return com_google_android_gms_measurement_internal_zzs.zzi(parseFloat);
                        }
                        zzAo().zzCF().zze("User property value exceeded Float value range. property, value", com_google_android_gms_internal_zzqb_zzg.name, com_google_android_gms_internal_zzqb_zzg.zzamJ);
                        return bool;
                    } catch (NumberFormatException e) {
                        zzAo().zzCF().zze("User property value exceeded Float value range. property, value", com_google_android_gms_internal_zzqb_zzg.name, com_google_android_gms_internal_zzqb_zzg.zzamJ);
                        return bool;
                    }
                }
                zzAo().zzCF().zze("Invalid user property value for Float number filter. property, value", com_google_android_gms_internal_zzqb_zzg.name, com_google_android_gms_internal_zzqb_zzg.zzamJ);
                return bool;
            } else if (zzeQ(com_google_android_gms_internal_zzqb_zzg.zzamJ)) {
                try {
                    return com_google_android_gms_measurement_internal_zzs.zzac(Long.parseLong(com_google_android_gms_internal_zzqb_zzg.zzamJ));
                } catch (NumberFormatException e2) {
                    zzAo().zzCF().zze("User property value exceeded Long value range. property, value", com_google_android_gms_internal_zzqb_zzg.name, com_google_android_gms_internal_zzqb_zzg.zzamJ);
                    return bool;
                }
            } else {
                zzAo().zzCF().zze("Invalid user property value for Long number filter. property, value", com_google_android_gms_internal_zzqb_zzg.name, com_google_android_gms_internal_zzqb_zzg.zzamJ);
                return bool;
            }
        }
    }

    void zza(String str, zza[] com_google_android_gms_internal_zzpz_zzaArr) {
        zzCj().zzb(str, com_google_android_gms_internal_zzpz_zzaArr);
    }

    zzqb.zza[] zza(String str, zzqb.zzb[] com_google_android_gms_internal_zzqb_zzbArr, zzg[] com_google_android_gms_internal_zzqb_zzgArr) {
        int intValue;
        Map map;
        Map map2;
        zzqb.zza com_google_android_gms_internal_zzqb_zza;
        zzqb.zza com_google_android_gms_internal_zzqb_zza2;
        BitSet bitSet;
        BitSet bitSet2;
        int i;
        int intValue2;
        zzx.zzcM(str);
        Set hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        Map arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (com_google_android_gms_internal_zzqb_zzbArr != null) {
            ArrayMap arrayMap4 = new ArrayMap();
            for (zzqb.zzb com_google_android_gms_internal_zzqb_zzb : com_google_android_gms_internal_zzqb_zzbArr) {
                zzi com_google_android_gms_measurement_internal_zzi;
                zzi zzI = zzCj().zzI(str, com_google_android_gms_internal_zzqb_zzb.name);
                if (zzI == null) {
                    zzAo().zzCF().zzj("Event aggregate wasn't created during raw event logging. event", com_google_android_gms_internal_zzqb_zzb.name);
                    com_google_android_gms_measurement_internal_zzi = new zzi(str, com_google_android_gms_internal_zzqb_zzb.name, 1, 1, com_google_android_gms_internal_zzqb_zzb.zzbaf.longValue());
                } else {
                    com_google_android_gms_measurement_internal_zzi = zzI.zzCB();
                }
                zzCj().zza(com_google_android_gms_measurement_internal_zzi);
                long j = com_google_android_gms_measurement_internal_zzi.zzaVP;
                map = (Map) arrayMap4.get(com_google_android_gms_internal_zzqb_zzb.name);
                if (map == null) {
                    map = zzCj().zzL(str, com_google_android_gms_internal_zzqb_zzb.name);
                    if (map == null) {
                        map = new ArrayMap();
                    }
                    arrayMap4.put(com_google_android_gms_internal_zzqb_zzb.name, map);
                    map2 = map;
                } else {
                    map2 = map;
                }
                zzAo().zzCK().zze("Found audiences. event, audience count", com_google_android_gms_internal_zzqb_zzb.name, Integer.valueOf(map2.size()));
                for (Integer intValue3 : map2.keySet()) {
                    int intValue4 = intValue3.intValue();
                    if (hashSet.contains(Integer.valueOf(intValue4))) {
                        zzAo().zzCK().zzj("Skipping failed audience ID", Integer.valueOf(intValue4));
                    } else {
                        com_google_android_gms_internal_zzqb_zza = (zzqb.zza) arrayMap.get(Integer.valueOf(intValue4));
                        if (com_google_android_gms_internal_zzqb_zza == null) {
                            com_google_android_gms_internal_zzqb_zza = new zzqb.zza();
                            arrayMap.put(Integer.valueOf(intValue4), com_google_android_gms_internal_zzqb_zza);
                            com_google_android_gms_internal_zzqb_zza.zzbac = Boolean.valueOf(false);
                            com_google_android_gms_internal_zzqb_zza2 = com_google_android_gms_internal_zzqb_zza;
                        } else {
                            com_google_android_gms_internal_zzqb_zza2 = com_google_android_gms_internal_zzqb_zza;
                        }
                        List<zzb> list = (List) map2.get(Integer.valueOf(intValue4));
                        bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue4));
                        bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue4));
                        if (bitSet == null) {
                            bitSet = new BitSet();
                            arrayMap2.put(Integer.valueOf(intValue4), bitSet);
                            bitSet2 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue4), bitSet2);
                        }
                        if (com_google_android_gms_internal_zzqb_zza2.zzbab == null && !com_google_android_gms_internal_zzqb_zza2.zzbac.booleanValue()) {
                            zzf zzC = zzCj().zzC(str, intValue4);
                            if (zzC == null) {
                                com_google_android_gms_internal_zzqb_zza2.zzbac = Boolean.valueOf(true);
                            } else {
                                com_google_android_gms_internal_zzqb_zza2.zzbab = zzC;
                                for (i = 0; i < zzC.zzbaH.length * 64; i++) {
                                    if (zzaj.zza(zzC.zzbaH, i)) {
                                        zzAo().zzCK().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue4), Integer.valueOf(i));
                                        bitSet.set(i);
                                        bitSet2.set(i);
                                    }
                                }
                            }
                        }
                        for (zzb com_google_android_gms_internal_zzpz_zzb : list) {
                            if (zzAo().zzQ(2)) {
                                zzAo().zzCK().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue4), com_google_android_gms_internal_zzpz_zzb.zzaZv, com_google_android_gms_internal_zzpz_zzb.zzaZw);
                                zzAo().zzCK().zzj("Filter definition", com_google_android_gms_internal_zzpz_zzb);
                            }
                            if (com_google_android_gms_internal_zzpz_zzb.zzaZv.intValue() > 256) {
                                zzAo().zzCF().zzj("Invalid event filter ID > 256. id", com_google_android_gms_internal_zzpz_zzb.zzaZv);
                            } else if (!bitSet2.get(com_google_android_gms_internal_zzpz_zzb.zzaZv.intValue())) {
                                Boolean zza = zza(com_google_android_gms_internal_zzpz_zzb, com_google_android_gms_internal_zzqb_zzb, j);
                                zzAo().zzCK().zzj("Event filter result", zza);
                                if (zza == null) {
                                    hashSet.add(Integer.valueOf(intValue4));
                                } else {
                                    bitSet2.set(com_google_android_gms_internal_zzpz_zzb.zzaZv.intValue());
                                    if (zza.booleanValue()) {
                                        bitSet.set(com_google_android_gms_internal_zzpz_zzb.zzaZv.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (com_google_android_gms_internal_zzqb_zzgArr != null) {
            Map arrayMap5 = new ArrayMap();
            for (zzg com_google_android_gms_internal_zzqb_zzg : com_google_android_gms_internal_zzqb_zzgArr) {
                map = (Map) arrayMap5.get(com_google_android_gms_internal_zzqb_zzg.name);
                if (map == null) {
                    map = zzCj().zzM(str, com_google_android_gms_internal_zzqb_zzg.name);
                    if (map == null) {
                        map = new ArrayMap();
                    }
                    arrayMap5.put(com_google_android_gms_internal_zzqb_zzg.name, map);
                    map2 = map;
                } else {
                    map2 = map;
                }
                zzAo().zzCK().zze("Found audiences. property, audience count", com_google_android_gms_internal_zzqb_zzg.name, Integer.valueOf(map2.size()));
                for (Integer intValue32 : map2.keySet()) {
                    intValue = intValue32.intValue();
                    if (hashSet.contains(Integer.valueOf(intValue))) {
                        zzAo().zzCK().zzj("Skipping failed audience ID", Integer.valueOf(intValue));
                    } else {
                        com_google_android_gms_internal_zzqb_zza = (zzqb.zza) arrayMap.get(Integer.valueOf(intValue));
                        if (com_google_android_gms_internal_zzqb_zza == null) {
                            com_google_android_gms_internal_zzqb_zza = new zzqb.zza();
                            arrayMap.put(Integer.valueOf(intValue), com_google_android_gms_internal_zzqb_zza);
                            com_google_android_gms_internal_zzqb_zza.zzbac = Boolean.valueOf(false);
                            com_google_android_gms_internal_zzqb_zza2 = com_google_android_gms_internal_zzqb_zza;
                        } else {
                            com_google_android_gms_internal_zzqb_zza2 = com_google_android_gms_internal_zzqb_zza;
                        }
                        List<zze> list2 = (List) map2.get(Integer.valueOf(intValue));
                        bitSet = (BitSet) arrayMap2.get(Integer.valueOf(intValue));
                        bitSet2 = (BitSet) arrayMap3.get(Integer.valueOf(intValue));
                        if (bitSet == null) {
                            bitSet = new BitSet();
                            arrayMap2.put(Integer.valueOf(intValue), bitSet);
                            bitSet2 = new BitSet();
                            arrayMap3.put(Integer.valueOf(intValue), bitSet2);
                        }
                        if (com_google_android_gms_internal_zzqb_zza2.zzbab == null && !com_google_android_gms_internal_zzqb_zza2.zzbac.booleanValue()) {
                            zzf zzC2 = zzCj().zzC(str, intValue);
                            if (zzC2 == null) {
                                com_google_android_gms_internal_zzqb_zza2.zzbac = Boolean.valueOf(true);
                            } else {
                                com_google_android_gms_internal_zzqb_zza2.zzbab = zzC2;
                                for (i = 0; i < zzC2.zzbaH.length * 64; i++) {
                                    if (zzaj.zza(zzC2.zzbaH, i)) {
                                        bitSet.set(i);
                                        bitSet2.set(i);
                                    }
                                }
                            }
                        }
                        for (zze com_google_android_gms_internal_zzpz_zze : list2) {
                            if (zzAo().zzQ(2)) {
                                zzAo().zzCK().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(intValue), com_google_android_gms_internal_zzpz_zze.zzaZv, com_google_android_gms_internal_zzpz_zze.zzaZL);
                                zzAo().zzCK().zzj("Filter definition", com_google_android_gms_internal_zzpz_zze);
                            }
                            if (com_google_android_gms_internal_zzpz_zze.zzaZv == null || com_google_android_gms_internal_zzpz_zze.zzaZv.intValue() > 256) {
                                zzAo().zzCF().zzj("Invalid property filter ID. id", String.valueOf(com_google_android_gms_internal_zzpz_zze.zzaZv));
                                hashSet.add(Integer.valueOf(intValue));
                                break;
                            } else if (bitSet2.get(com_google_android_gms_internal_zzpz_zze.zzaZv.intValue())) {
                                zzAo().zzCK().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue), com_google_android_gms_internal_zzpz_zze.zzaZv);
                            } else {
                                Boolean zza2 = zza(com_google_android_gms_internal_zzpz_zze, com_google_android_gms_internal_zzqb_zzg);
                                zzAo().zzCK().zzj("Property filter result", zza2);
                                if (zza2 == null) {
                                    hashSet.add(Integer.valueOf(intValue));
                                } else {
                                    bitSet2.set(com_google_android_gms_internal_zzpz_zze.zzaZv.intValue());
                                    if (zza2.booleanValue()) {
                                        bitSet.set(com_google_android_gms_internal_zzpz_zze.zzaZv.intValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        zzqb.zza[] com_google_android_gms_internal_zzqb_zzaArr = new zzqb.zza[arrayMap2.size()];
        int i2 = 0;
        for (Integer intValue322 : arrayMap2.keySet()) {
            intValue2 = intValue322.intValue();
            if (!hashSet.contains(Integer.valueOf(intValue2))) {
                com_google_android_gms_internal_zzqb_zza = (zzqb.zza) arrayMap.get(Integer.valueOf(intValue2));
                zzqb.zza com_google_android_gms_internal_zzqb_zza3 = com_google_android_gms_internal_zzqb_zza == null ? new zzqb.zza() : com_google_android_gms_internal_zzqb_zza;
                com_google_android_gms_internal_zzqb_zzaArr[i2] = com_google_android_gms_internal_zzqb_zza3;
                com_google_android_gms_internal_zzqb_zza3.zzaZr = Integer.valueOf(intValue2);
                com_google_android_gms_internal_zzqb_zza3.zzbaa = new zzf();
                com_google_android_gms_internal_zzqb_zza3.zzbaa.zzbaH = zzaj.zza((BitSet) arrayMap2.get(Integer.valueOf(intValue2)));
                com_google_android_gms_internal_zzqb_zza3.zzbaa.zzbaG = zzaj.zza((BitSet) arrayMap3.get(Integer.valueOf(intValue2)));
                zzCj().zza(str, intValue2, com_google_android_gms_internal_zzqb_zza3.zzbaa);
                i2++;
            }
        }
        return (zzqb.zza[]) Arrays.copyOf(com_google_android_gms_internal_zzqb_zzaArr, i2);
    }

    boolean zzeQ(String str) {
        return Pattern.matches("[+-]?[0-9]+", str);
    }

    boolean zzeR(String str) {
        return Pattern.matches("[+-]?(([0-9]+\\.?)|([0-9]*\\.[0-9]+))", str);
    }

    protected void zziJ() {
    }
}
