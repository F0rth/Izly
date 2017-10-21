package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;
import com.nineoldandroids.animation.Animator;

public class AnimatorUtil {
    private AnimatorUtil() {
    }

    @NonNull
    public static Animator[] concatAnimators(@NonNull Animator[] animatorArr, @NonNull Animator[] animatorArr2, @NonNull Animator animator) {
        int i = 0;
        Animator[] animatorArr3 = new Animator[((animatorArr.length + animatorArr2.length) + 1)];
        int i2 = 0;
        while (i2 < animatorArr.length) {
            animatorArr3[i2] = animatorArr[i2];
            i2++;
        }
        int length = animatorArr2.length;
        while (i < length) {
            animatorArr3[i2] = animatorArr2[i];
            i2++;
            i++;
        }
        animatorArr3[animatorArr3.length - 1] = animator;
        return animatorArr3;
    }
}
