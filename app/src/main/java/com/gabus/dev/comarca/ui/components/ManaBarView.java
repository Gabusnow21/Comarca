package com.gabus.dev.comarca.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gabus.dev.comarca.R;

public class ManaBarView extends View {

    private int maxMana = 3;
    private int currentMana = 3;

    private final Paint gemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gemInactivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float gemSize;
    private float gemSpacing;

    public ManaBarView(Context context) {
        super(context);
        init(context, null);
    }

    public ManaBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ManaBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        gemSize = context.getResources().getDimension(R.dimen.mana_gem_size);
        gemSpacing = context.getResources().getDimension(R.dimen.spacing_sm);

        gemPaint.setStyle(Paint.Style.FILL);
        gemPaint.setColor(Color.parseColor("#4a9bd9"));

        gemInactivePaint.setStyle(Paint.Style.FILL);
        gemInactivePaint.setColor(Color.parseColor("#2a3a4a"));

        glowPaint.setStyle(Paint.Style.FILL);
        glowPaint.setColor(Color.parseColor("#4a9bd9"));
        glowPaint.setAlpha(120);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2f);
        borderPaint.setColor(Color.parseColor("#9dd4ff"));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ManaBarView);
            maxMana = ta.getInt(R.styleable.ManaBarView_manaMax, 3);
            currentMana = ta.getInt(R.styleable.ManaBarView_manaCurrent, 3);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float centerX = getWidth() / 2f;
        float totalWidth = maxMana * gemSize + (maxMana - 1) * gemSpacing;
        float startX = centerX - totalWidth / 2f;
        float cy = getHeight() / 2f;

        for (int i = 0; i < maxMana; i++) {
            float cx = startX + i * (gemSize + gemSpacing) + gemSize / 2f;

            if (i < currentMana) {
                // Active gem with glow
                canvas.drawRect(cx - gemSize / 2f - 4, cy - gemSize / 2f - 4,
                        cx + gemSize / 2f + 4, cy + gemSize / 2f + 4, glowPaint);

                // Diamond shape (rotated square)
                canvas.save();
                canvas.rotate(45, cx, cy);
                canvas.drawRect(cx - gemSize / 2f, cy - gemSize / 2f,
                        cx + gemSize / 2f, cy + gemSize / 2f, gemPaint);
                canvas.restore();

                // Border
                canvas.save();
                canvas.rotate(45, cx, cy);
                canvas.drawRect(cx - gemSize / 2f, cy - gemSize / 2f,
                        cx + gemSize / 2f, cy + gemSize / 2f, borderPaint);
                canvas.restore();
            } else {
                // Inactive gem
                canvas.save();
                canvas.rotate(45, cx, cy);
                canvas.drawRect(cx - gemSize / 2f, cy - gemSize / 2f,
                        cx + gemSize / 2f, cy + gemSize / 2f, gemInactivePaint);
                canvas.restore();
            }
        }
    }

    public void setMana(int current, int max) {
        this.currentMana = Math.max(0, Math.min(max, current));
        this.maxMana = max;
        requestLayout();
        invalidate();
    }

    public void setCurrentMana(int current) {
        this.currentMana = Math.max(0, Math.min(maxMana, current));
        invalidate();
    }

    public int getCurrentMana() { return currentMana; }
    public int getMaxMana() { return maxMana; }
}
