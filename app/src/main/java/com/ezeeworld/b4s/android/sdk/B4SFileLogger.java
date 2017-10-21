package com.ezeeworld.b4s.android.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;

public class B4SFileLogger {
    private static String a = "B4SSDK_Log";

    @TargetApi(19)
    public static void addRecordToLog(Context context, String str) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/B4SLogs/");
            if (!file.exists()) {
                Log.d("B4S FILE LOGGER ", "->>>>>>>>>>>>>>> Dir created:" + file.mkdirs());
            }
            try {
                File file2 = new File(file.getAbsolutePath(), "PJLog.txt");
                if (file2.exists()) {
                    Log.d("B4S FILE LOGGER ", "[" + file2.getAbsolutePath() + "] >>>>>>>>>>>>>>> File exists size= " + file2.length() + " msg=" + str);
                } else {
                    file2.createNewFile();
                    Log.d("B4S FILE LOGGER ", "->>>>>>>>>>>>>>> File created ");
                }
                Writer bufferedWriter = new BufferedWriter(new FileWriter(file2, true), 1024);
                bufferedWriter.write(DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str + "\n");
                bufferedWriter.close();
            } catch (Exception e) {
                Log.e("B4S FILE LOGGER ", "ex: " + e);
                e.printStackTrace();
            }
        } catch (Exception e2) {
            Log.e("B4S FILE LOGGER ", "e: " + e2);
            e2.printStackTrace();
        }
    }
}
