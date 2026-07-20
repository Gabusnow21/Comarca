package com.gabus.dev.comarca.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.gabus.dev.comarca.R;

import java.util.Locale;

public class HealthBarView extends View {

    private int maxProgress = 100;
    private int currentProgress = 75;

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint overlayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float cornerRadius;

    public HealthBarView(Context context) {
        super(context);
        init(context, null);
    }

    public HealthBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HealthBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        cornerRadius = context.getResources().getDimension(R.dimen.enemy_health_bar_height) / 2f;

        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#110e02"));

        fillPaint.setStyle(Paint.Style.FILL);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f);
        borderPaint.setColor(Color.parseColor("#cd7f32"));

        textPaint.setTypeface(Typeface.create(context.getResources().getFont(R.font.space_grotesk), Typeface.BOLD));
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.text_label_sm));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setShadowLayer(2f, 1f, 1f, Color.BLACK);

        overlayPaint.setStyle(Paint.Style.FILL);
        overlayPaint.setColor(Color.WHITE);
        overlayPaint.setAlpha(50);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HealthBarView);
            maxProgress = ta.getInt(R.styleable.HealthBarView_barMax, 100);
            currentProgress = ta.getInt(R.styleable.HealthBarView_barProgress, 75);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        // Background (barrel)
        canvas.drawRoundRect(0, 0, w, h, cornerRadius, cornerRadius, bgPaint);

        // Fill
        float fraction = maxProgress > 0 ? (float) currentProgress / maxProgress : 0;
        float fillWidth = (w - 8) * fraction;

        if (fillWidth > 0) {
            // Gradient from dark green to bright green
            fillPaint.setShader(new LinearGradient(
                    0, 0, fillWidth, 0,
                    Color.parseColor("#1a5a10"),
                    Color.parseColor("#40c040"),
                    Shader.TileMode.CLAMP));
            canvas.drawRoundRect(4, 4, 4 + fillWidth, h - 4, cornerRadius - 2, cornerRadius - 2, fillPaint);

            // Overlay shine
            canvas.drawRoundRect(4, 4, 4 + fillWidth, h / 2f, cornerRadius - 2, cornerRadius - 2, overlayPaint);
        }

        // Border
        canvas.drawRoundRect(2, 2, w - 2, h - 2, cornerRadius, cornerRadius, borderPaint);

        // Text
        String text = String.format(Locale.getDefault(), "%d / %d", currentProgress, maxProgress);
        float textY = (h / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);
        canvas.drawText(text, w / 2f, textY, textPaint);
    }

    public void setProgress(int progress) {
        this.currentProgress = Math.max(0, Math.min(maxProgress, progress));
        invalidate();
    }

    public void setMax(int max) {
        this.maxProgress = max;
        invalidate();
    }

    public int getProgress() { return currentProgress; }
    public int getMax() { return maxProgress; }
}
