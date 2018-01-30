package com.fesskiev.programmingsamples.android.view;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fesskiev.programmingsamples.R;


/**
 * touch event system excplanation
 * http://balpha.de/2013/07/android-development-what-i-wish-i-had-known-earlier/
 * http://codetheory.in/understanding-android-input-touch-events/
 * https://www.youtube.com/watch?v=EZAoJU-nUyI
 */
public class ViewsActivity extends AppCompatActivity {

    private static final String TAG = ViewsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        View rootView = getWindow().findViewById(android.R.id.content);
//        rootView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            Log.e("test", "view h: " + v.getHeight() + " w: " + v.getWidth());
//        });
//        rootView.animate()
//                .scaleX(0.5f)
//                .scaleY(0.5f)
//                .setDuration(1000)
//                .setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        getWindow().setLayout(rootView.getWidth() / 2, rootView.getHeight() / 2);
//                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                })
//                .start();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.wtf(TAG, "Activity onTouchEvent");
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
