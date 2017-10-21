package android.support.v4.speech.tts;

import android.os.Build.VERSION;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import java.util.Locale;
import java.util.Set;

class TextToSpeechICSMR1 {
    public static final String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
    public static final String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";

    interface UtteranceProgressListenerICSMR1 {
        void onDone(String str);

        void onError(String str);

        void onStart(String str);
    }

    TextToSpeechICSMR1() {
    }

    static Set<String> getFeatures(TextToSpeech textToSpeech, Locale locale) {
        return VERSION.SDK_INT >= 15 ? textToSpeech.getFeatures(locale) : null;
    }

    static void setUtteranceProgressListener(TextToSpeech textToSpeech, final UtteranceProgressListenerICSMR1 utteranceProgressListenerICSMR1) {
        if (VERSION.SDK_INT >= 15) {
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                public final void onDone(String str) {
                    utteranceProgressListenerICSMR1.onDone(str);
                }

                public final void onError(String str) {
                    utteranceProgressListenerICSMR1.onError(str);
                }

                public final void onStart(String str) {
                    utteranceProgressListenerICSMR1.onStart(str);
                }
            });
        } else {
            textToSpeech.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
                public final void onUtteranceCompleted(String str) {
                    utteranceProgressListenerICSMR1.onStart(str);
                    utteranceProgressListenerICSMR1.onDone(str);
                }
            });
        }
    }
}
