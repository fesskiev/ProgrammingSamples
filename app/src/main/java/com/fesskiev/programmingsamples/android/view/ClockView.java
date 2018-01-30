package com.fesskiev.programmingsamples.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.fesskiev.programmingsamples.R;

import java.util.TimeZone;

import static android.os.Looper.getMainLooper;

public class ClockView extends View {

    private TimeHandlerThread timeHandlerThread;
    private Paint textPaint;
    private Paint circlePaint;
    private Paint markBoldPaint;
    private Paint markThinPaint;
    private Paint secondsPaint;
    private Paint minutesPaint;
    private Paint hoursPaint;

    private float cx;
    private float cy;

    private int radiusClockDigits;
    private int radiusInnerCircle;
    private int radiusMarkBold;

    private int secondsLineSize;
    private int minutesLineSize;
    private int hoursLineSize;
    private int markLineSize;

    private float secondsAngle;
    private float minutesAngle;
    private float hoursAngle;

    private Handler UIHandler = new Handler(getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TimeHandlerThread.CALCULATE_TIME_RESULT) {
                invalidate();
                postDelayed(() -> timeHandlerThread.calculateTime(), 1000);
            }
        }
    };

    public ClockView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        Resources res = getResources();
        final int markBoldStrokeWidth = res.getDimensionPixelSize(R.dimen.mark_bold_width);
        final int markThinStrokeWidth = res.getDimensionPixelSize(R.dimen.mark_thin_width);

        final int circleStrokeWidth = res.getDimensionPixelSize(R.dimen.circle_stroke_width);
        final int secondsStrokeWidth = res.getDimensionPixelSize(R.dimen.seconds_stroke_width);
        final int minutesStrokeWidth = res.getDimensionPixelSize(R.dimen.minutes_stroke_width);
        final int hoursStrokeWidth = res.getDimensionPixelSize(R.dimen.hours_stroke_width);
        final int clockTextSize = res.getDimensionPixelSize(R.dimen.clock_text_size);
        secondsLineSize = res.getDimensionPixelSize(R.dimen.seconds_line_size);
        minutesLineSize = res.getDimensionPixelSize(R.dimen.minutes_line_size);
        markLineSize = res.getDimensionPixelSize(R.dimen.mark_line_size);
        hoursLineSize = res.getDimensionPixelSize(R.dimen.hours_line_size);

        radiusClockDigits = res.getDimensionPixelSize(R.dimen.radius_clock_digits);
        radiusInnerCircle = res.getDimensionPixelSize(R.dimen.radius_inner_circle);
        radiusMarkBold = res.getDimensionPixelSize(R.dimen.radius_outer_circle);

        textPaint = new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.black));
        textPaint.setTextSize(clockTextSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(context.getResources().getColor(R.color.red));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStrokeWidth(circleStrokeWidth);
        circlePaint.setAntiAlias(true);

        markBoldPaint = new Paint();
        markBoldPaint.setColor(context.getResources().getColor(R.color.primary_dark));
        markBoldPaint.setStyle(Paint.Style.STROKE);
        markBoldPaint.setStrokeJoin(Paint.Join.ROUND);
        markBoldPaint.setStrokeCap(Paint.Cap.ROUND);
        markBoldPaint.setStrokeWidth(markBoldStrokeWidth);
        markBoldPaint.setAntiAlias(true);

        markThinPaint = new Paint();
        markThinPaint.setColor(context.getResources().getColor(R.color.primary_dark));
        markThinPaint.setStyle(Paint.Style.STROKE);
        markThinPaint.setStrokeJoin(Paint.Join.ROUND);
        markThinPaint.setStrokeCap(Paint.Cap.ROUND);
        markThinPaint.setStrokeWidth(markThinStrokeWidth);
        markThinPaint.setAntiAlias(true);

        secondsPaint = new Paint();
        secondsPaint.setColor(context.getResources().getColor(R.color.red));
        secondsPaint.setStyle(Paint.Style.STROKE);
        secondsPaint.setStrokeJoin(Paint.Join.ROUND);
        secondsPaint.setStrokeCap(Paint.Cap.ROUND);
        secondsPaint.setStrokeWidth(secondsStrokeWidth);
        secondsPaint.setAntiAlias(true);

        minutesPaint = new Paint();
        minutesPaint.setColor(context.getResources().getColor(R.color.black));
        minutesPaint.setStyle(Paint.Style.STROKE);
        minutesPaint.setStrokeJoin(Paint.Join.ROUND);
        minutesPaint.setStrokeCap(Paint.Cap.ROUND);
        minutesPaint.setStrokeWidth(minutesStrokeWidth);
        minutesPaint.setAntiAlias(true);

        hoursPaint = new Paint();
        hoursPaint.setColor(context.getResources().getColor(R.color.black));
        hoursPaint.setStyle(Paint.Style.STROKE);
        hoursPaint.setStrokeJoin(Paint.Join.ROUND);
        hoursPaint.setStrokeCap(Paint.Cap.ROUND);
        hoursPaint.setStrokeWidth(hoursStrokeWidth);
        hoursPaint.setAntiAlias(true);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = (int) convertDpToPixel(radiusClockDigits, getContext());
        int desiredHeight = (int) convertDpToPixel(radiusClockDigits, getContext());

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cx = getWidth() / 2f;
        cy = getHeight() / 2f;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timeHandlerThread = new TimeHandlerThread();
        timeHandlerThread.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timeHandlerThread != null) {
            timeHandlerThread.quit();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawClockDigits(canvas);
        drawHourHands(canvas);
        drawMarks(canvas);

        canvas.drawCircle(cx, cy, radiusInnerCircle, circlePaint);
    }

    private void drawMarks(Canvas canvas) {
        for (int i = 0; i < 360; i += 6) {
            float angle = (float) Math.toRadians(i);

            float startX = (float) (cx + radiusMarkBold * Math.sin(angle));
            float startY = (float) (cy - radiusMarkBold * Math.cos(angle));

            float stopX = (float) (cx + (radiusMarkBold - markLineSize) * Math.sin(angle));
            float stopY = (float) (cy - (radiusMarkBold - markLineSize) * Math.cos(angle));
            if (i == 0 || i % 30 == 0) {
                canvas.drawLine(startX, startY, stopX, stopY, markBoldPaint);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, markThinPaint);
            }
        }
    }

    private void drawHourHands(Canvas canvas) {
        float angleSecondsRadians = (float) Math.toRadians(secondsAngle);
        canvas.drawLine((float) (cx + secondsLineSize * Math.sin(angleSecondsRadians)),
                (float) (cy - secondsLineSize * Math.cos(angleSecondsRadians)), cx, cy, secondsPaint);

        float angleMinutesRadians = (float) Math.toRadians(minutesAngle);
        canvas.drawLine((float) (cx + minutesLineSize * Math.sin(angleMinutesRadians)),
                (float) (cy - minutesLineSize * Math.cos(angleMinutesRadians)), cx, cy, minutesPaint);

        float angleHoursRadians = (float) Math.toRadians(hoursAngle);
        canvas.drawLine((float) (cx + hoursLineSize * Math.sin(angleHoursRadians)),
                (float) (cy - hoursLineSize * Math.cos(angleHoursRadians)), cx, cy, hoursPaint);
    }

    private void drawClockDigits(Canvas canvas) {
        int count = 0;
        for (int i = 0; i < 360; i += 30) {
            float angle = (float) Math.toRadians(i);
            float textX = (float) (cx + radiusClockDigits * Math.sin(angle));
            float textY = (float) (cy - radiusClockDigits * Math.cos(angle)) + textPaint.descent();

            if (i == 0) {
                canvas.drawText("12", textX, textY, textPaint);
                continue;
            }
            count++;
            canvas.drawText(String.valueOf(count), textX, textY, textPaint);
        }
    }

    private class TimeHandlerThread extends HandlerThread {

        private static final int CALCULATE_TIME_ANGLES = 0;
        public static final int CALCULATE_TIME_RESULT = 1;

        private Handler handler;
        private int timeZone;

        public TimeHandlerThread() {
            super(TimeHandlerThread.class.getSimpleName(), Process.THREAD_PRIORITY_BACKGROUND);
            timeZone = TimeZone.getDefault().getOffset(System.currentTimeMillis());
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            handler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case CALCULATE_TIME_ANGLES:
                            calculateTimeAngles();
                            UIHandler.sendEmptyMessage(CALCULATE_TIME_RESULT);
                            break;
                    }
                }
            };
            calculateTime();
        }

        private void calculateTimeAngles() {
            long milliseconds = System.currentTimeMillis() + timeZone;
            int seconds = (int) (milliseconds / 1000) % 60;
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

            secondsAngle = seconds * 6;
            minutesAngle = minutes * 6;
            hoursAngle = hours * 30;
        }

        public void calculateTime() {
            handler.sendEmptyMessage(CALCULATE_TIME_ANGLES);
        }


    }

    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}