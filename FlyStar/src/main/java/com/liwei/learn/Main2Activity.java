package com.liwei.learn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
   private TextView target;
    private PointF startPosition;
    private Random mRandom;
private ConstraintLayout  m_constraintLayout;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mRandom = new Random();
        target = (TextView) findViewById(R.id.textView);
        startPosition = new PointF();
        startPosition.x = target.getX();
        startPosition.y = target.getY();
        btn = (Button) findViewById(R.id.button2);
        m_constraintLayout = (ConstraintLayout) findViewById(R.id.container);
    }

    public void start(View view){
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(getScaleAlphaAnimator(target),getBeisaierAnim(target));
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                m_constraintLayout.removeView(target);
                btn.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }
    private AnimatorSet getScaleAlphaAnimator(TextView target){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X,0.1f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.1f, 1.0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.1f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.playTogether(scaleX,scaleY,alpha);
        set.setInterpolator(new LinearInterpolator());
        return set;
    }

    private ValueAnimator getBeisaierAnim(final View target){
        //贝塞尔曲线中间的两个点
        final PointF pointf1 = randomPointF(3.0f);
        final PointF pointf2 = randomPointF(1.5f);

        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {//通过fraction 完成object(pointF)内部的值的变动
                PointF value = new PointF();
                float left = 1.0f-fraction;
                value.x = (float) (startValue.x * Math.pow(left, 3.0) + 3 * pointf1.x * Math.pow(left, 2) * fraction + 3 * pointf2.x * Math.pow(fraction, 2) * left + endValue.x * Math.pow(fraction, 3));
                value.y = (float) (startValue.y * Math.pow(left, 3.0) + 3 * pointf1.y * Math.pow(left, 2) * fraction + 3 * pointf2.y * Math.pow(fraction, 2) * left + endValue.y * Math.pow(fraction, 3));//三节贝塞尔曲线公式

                return value;
            }


        }, startPosition, new PointF(mRandom.nextInt(m_constraintLayout.getWidth()) * 1.0f, 0));
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(4000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                target.setX(point.x);
                target.setY(point.y);
                target.setAlpha(1.0f - animation.getAnimatedFraction() * animation.getAnimatedFraction());
            }
        });
        return animator;
    }
    /**
     * 随机贝塞尔曲线中间的点
     * @param scale
     * @return
     */
    private PointF randomPointF(float scale){
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(m_constraintLayout.getWidth());
        pointF.y = mRandom.nextInt((int) target.getY())/scale;

        return pointF;
    }
}
