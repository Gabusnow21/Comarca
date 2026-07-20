package com.gabus.dev.comarca.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.gabus.dev.comarca.R;

public class FactionBadgeView extends View {

    private int factionCount = 0;
    private String factionName = "";
    private int factionColor = Color.WHITE;

    private final Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float dotSize;

    public FactionBadgeView(Context context) {
        super(context);
        init(context, null);
    }

    public FactionBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FactionBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        dotSize = context.getResources().getDimension(R.dimen.deck_faction_dot_size);

        dotPaint.setStyle(Paint.Style.FILL);

        textPaint.setTypeface(Typeface.create(context.getResources().getFont(R.font.space_grotesk), Typeface.NORMAL));
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.text_label_lg));
        textPaint.setColor(Color.parseColor("#4a3520"));

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FactionBadgeView);
            factionCount = ta.getInt(R.styleable.FactionBadgeView_factionCount, 0);
            factionName = ta.getString(R.styleable.FactionBadgeView_factionName);
            if (factionName == null) factionName = "";
            factionColor = ta.getColor(R.styleable.FactionBadgeView_factionColor, Color.WHITE);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cy = getHeight() / 2f;

        // Dot
        float dotCx = dotSize / 2f + 4;
        dotPaint.setColor(factionColor);
        canvas.drawCircle(dotCx, cy, dotSize / 2f, dotPaint);

        // Text
        String text = String.format("%d %s", factionCount, factionName);
        canvas.drawText(text, dotCx + dotSize / 2f + 8, cy + textPaint.getTextSize() / 3f, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        String text = String.format("%d %s", factionCount, factionName);
        float textWidth = textPaint.measureText(text);
        int width = (int) (dotSize + 8 + textWidth + 16);
        int height = (int) (Math.max(dotSize, textPaint.getTextSize()) + 16);
        setMeasuredDimension(width, height);
    }

    public void setFaction(int count, String name, int color) {
        this.factionCount = count;
        this.factionName = name != null ? name : "";
        this.factionColor = color;
        requestLayout();
        invalidate();
    }
}
