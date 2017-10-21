package com.tekle.oss.android.animation;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ViewAnimator;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import com.tekle.oss.android.animation.FlipAnimation.ScaleUpDownEnum;

public class AnimationFactory {

    public enum FlipDirection {
        LEFT_RIGHT,
        RIGHT_LEFT;

        public final float getEndDegreeForFirstView() {
            switch (this) {
                case LEFT_RIGHT:
                    return 90.0f;
                case RIGHT_LEFT:
                    return -90.0f;
                default:
                    return 0.0f;
            }
        }

        public final float getEndDegreeForSecondView() {
            return 0.0f;
        }

        public final float getStartDegreeForFirstView() {
            return 0.0f;
        }

        public final float getStartDegreeForSecondView() {
            switch (this) {
                case LEFT_RIGHT:
                    return -90.0f;
                case RIGHT_LEFT:
                    return 90.0f;
                default:
                    return 0.0f;
            }
        }

        public final FlipDirection theOtherDirection() {
            switch (this) {
                case LEFT_RIGHT:
                    return RIGHT_LEFT;
                case RIGHT_LEFT:
                    return LEFT_RIGHT;
                default:
                    return null;
            }
        }
    }

    public static void fadeIn(View view) {
        if (view != null) {
            view.startAnimation(fadeInAnimation(500, view));
        }
    }

    public static Animation fadeInAnimation(long j, long j2) {
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setDuration(j);
        alphaAnimation.setStartOffset(j2);
        return alphaAnimation;
    }

    public static Animation fadeInAnimation(long j, final View view) {
        Animation fadeInAnimation = fadeInAnimation(500, 0);
        fadeInAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                view.setVisibility(0);
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                view.setVisibility(8);
            }
        });
        return fadeInAnimation;
    }

    public static void fadeInThenOut(final View view, long j) {
        if (view != null) {
            view.setVisibility(0);
            Animation animationSet = new AnimationSet(true);
            Animation[] fadeInThenOutAnimation = fadeInThenOutAnimation(500, j);
            animationSet.addAnimation(fadeInThenOutAnimation[0]);
            animationSet.addAnimation(fadeInThenOutAnimation[1]);
            animationSet.setAnimationListener(new AnimationListener() {
                public final void onAnimationEnd(Animation animation) {
                    view.setVisibility(8);
                }

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    view.setVisibility(0);
                }
            });
            view.startAnimation(animationSet);
        }
    }

    public static Animation[] fadeInThenOutAnimation(long j, long j2) {
        return new Animation[]{fadeInAnimation(j, 0), fadeOutAnimation(j, j + j2)};
    }

    public static void fadeOut(View view) {
        if (view != null) {
            view.startAnimation(fadeOutAnimation(500, view));
        }
    }

    public static Animation fadeOutAnimation(long j, long j2) {
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setStartOffset(j2);
        alphaAnimation.setDuration(j);
        return alphaAnimation;
    }

    public static Animation fadeOutAnimation(long j, final View view) {
        Animation fadeOutAnimation = fadeOutAnimation(500, 0);
        fadeOutAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                view.setVisibility(8);
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                view.setVisibility(0);
            }
        });
        return fadeOutAnimation;
    }

    public static Animation[] flipAnimation(View view, View view2, FlipDirection flipDirection, long j, Interpolator interpolator) {
        float width = ((float) view.getWidth()) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
        float height = ((float) view.getHeight()) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
        Animation flipAnimation = new FlipAnimation(flipDirection.getStartDegreeForFirstView(), flipDirection.getEndDegreeForFirstView(), width, height, FlipAnimation.SCALE_DEFAULT, ScaleUpDownEnum.SCALE_DOWN);
        flipAnimation.setDuration(j);
        flipAnimation.setFillAfter(true);
        flipAnimation.setInterpolator(interpolator == null ? new AccelerateInterpolator() : interpolator);
        new AnimationSet(true).addAnimation(flipAnimation);
        flipAnimation = new FlipAnimation(flipDirection.getStartDegreeForSecondView(), flipDirection.getEndDegreeForSecondView(), width, height, FlipAnimation.SCALE_DEFAULT, ScaleUpDownEnum.SCALE_UP);
        flipAnimation.setDuration(j);
        flipAnimation.setFillAfter(true);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        flipAnimation.setInterpolator(interpolator);
        flipAnimation.setStartOffset(j);
        new AnimationSet(true).addAnimation(flipAnimation);
        return new Animation[]{r7, new AnimationSet(true)};
    }

    public static void flipTransition(ViewAnimator viewAnimator, FlipDirection flipDirection) {
        View currentView = viewAnimator.getCurrentView();
        int displayedChild = viewAnimator.getDisplayedChild();
        int childCount = (displayedChild + 1) % viewAnimator.getChildCount();
        Animation[] flipAnimation = flipAnimation(currentView, viewAnimator.getChildAt(childCount), childCount < displayedChild ? flipDirection.theOtherDirection() : flipDirection, 500, null);
        viewAnimator.setOutAnimation(flipAnimation[0]);
        viewAnimator.setInAnimation(flipAnimation[1]);
        viewAnimator.showNext();
    }

    public static void flipTransition(ViewAnimator viewAnimator, FlipDirection flipDirection, long j, final AnimationListener animationListener) {
        View currentView = viewAnimator.getCurrentView();
        int displayedChild = viewAnimator.getDisplayedChild();
        int childCount = (displayedChild + 1) % viewAnimator.getChildCount();
        Animation[] flipAnimation = flipAnimation(currentView, viewAnimator.getChildAt(childCount), childCount < displayedChild ? flipDirection.theOtherDirection() : flipDirection, j, null);
        viewAnimator.setOutAnimation(flipAnimation[0]);
        viewAnimator.setInAnimation(flipAnimation[1]);
        flipAnimation[0].setAnimationListener(new AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
                if (animationListener != null) {
                    animationListener.onAnimationStart(animation);
                }
            }
        });
        flipAnimation[1].setAnimationListener(new AnimationListener() {
            public final void onAnimationEnd(Animation animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd(animation);
                }
            }

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }
        });
        viewAnimator.showNext();
    }

    public static Animation inFromLeftAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }

    public static Animation inFromRightAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }

    public static Animation inFromTopAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, -1.0f, 2, 0.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }

    public static Animation outToLeftAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }

    public static Animation outToRightAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 1.0f, 2, 0.0f, 2, 0.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }

    public static Animation outToTopAnimation(long j, Interpolator interpolator) {
        Animation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, -1.0f);
        translateAnimation.setDuration(j);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        translateAnimation.setInterpolator(interpolator);
        return translateAnimation;
    }
}
