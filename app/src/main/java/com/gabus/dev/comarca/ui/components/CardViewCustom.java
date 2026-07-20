package com.gabus.dev.comarca.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gabus.dev.comarca.R;

import java.util.Locale;

public class CardViewCustom extends FrameLayout {

    private TextView tvManaCost;
    private TextView tvCardName;
    private TextView tvAttack;
    private TextView tvDefense;

    private int manaCost;
    private int attack;
    private int defense;
    private String cardName;
    private int factionId;

    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint manaBadgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint manaBadgeBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float cornerRadius;
    private float manaBadgeRadius;

    public CardViewCustom(Context context) {
        super(context);
        init(context, null);
    }

    public CardViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CardViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        setClipChildren(false);

        cornerRadius = context.getResources().getDimension(R.dimen.card_corner_radius) * 2;
        manaBadgeRadius = context.getResources().getDimension(R.dimen.card_mana_badge_size) / 2f;

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4f);
        borderPaint.setColor(Color.parseColor("#cd7f32"));

        manaBadgePaint.setStyle(Paint.Style.FILL);
        manaBadgePaint.setColor(Color.parseColor("#cd7f32"));

        manaBadgeBorderPaint.setStyle(Paint.Style.STROKE);
        manaBadgeBorderPaint.setStrokeWidth(2f);
        manaBadgeBorderPaint.setColor(Color.parseColor("#8e4e00"));

        LayoutInflater.from(context).inflate(R.layout.layout_card_view, this, true);

        tvManaCost = findViewById(R.id.tv_mana_cost);
        tvCardName = findViewById(R.id.tv_card_name);
        tvAttack = findViewById(R.id.tv_card_attack);
        tvDefense = findViewById(R.id.tv_card_defense);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CardViewCustom);
            manaCost = ta.getInt(R.styleable.CardViewCustom_cardManaCost, 0);
            attack = ta.getInt(R.styleable.CardViewCustom_cardAttack, 0);
            defense = ta.getInt(R.styleable.CardViewCustom_cardDefense, 0);
            cardName = ta.getString(R.styleable.CardViewCustom_cardName);
            factionId = ta.getInt(R.styleable.CardViewCustom_cardFactionId, 1);
            ta.recycle();
        }

        updateUI();
    }

    private void updateUI() {
        if (tvManaCost != null) {
            tvManaCost.setText(String.valueOf(manaCost));
        }
        if (tvCardName != null) {
            tvCardName.setText(cardName != null ? cardName : "");
        }
        if (tvAttack != null) {
            tvAttack.setText(String.valueOf(attack));
        }
        if (tvDefense != null) {
            tvDefense.setText(String.valueOf(defense));
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();

        // Card background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#393522"));
        canvas.drawRoundRect(0, 0, w, h, cornerRadius, cornerRadius, bgPaint);

        // Faction tint on top half
        int factionColor = getFactionColor(factionId);
        Paint factionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        factionPaint.setStyle(Paint.Style.FILL);
        factionPaint.setColor(factionColor);
        factionPaint.setAlpha(80);
        canvas.drawRoundRect(0, 0, w, h / 2f, cornerRadius, cornerRadius, factionPaint);

        // Bronze border
        canvas.drawRoundRect(2, 2, w - 2, h - 2, cornerRadius, cornerRadius, borderPaint);

        // Divider line between art and stats
        Paint dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setStyle(Paint.Style.STROKE);
        dividerPaint.setStrokeWidth(2f);
        dividerPaint.setColor(Color.parseColor("#cd7f32"));
        canvas.drawLine(0, h / 2f, w, h / 2f, dividerPaint);

        super.dispatchDraw(canvas);

        // Mana badge (top-left circle)
        float badgeCx = manaBadgeRadius + 4;
        float badgeCy = manaBadgeRadius + 4;
        canvas.drawCircle(badgeCx, badgeCy, manaBadgeRadius, manaBadgePaint);
        canvas.drawCircle(badgeCx, badgeCy, manaBadgeRadius, manaBadgeBorderPaint);
    }

    private int getFactionColor(int factionId) {
        switch (factionId) {
            case 1: return Color.parseColor("#3a5a82"); // Human
            case 2: return Color.parseColor("#8a6318"); // Dwarf
            case 3: return Color.parseColor("#3a7a24"); // Ogre
            case 4: return Color.parseColor("#6a4490"); // Elf
            default: return Color.parseColor("#393522");
        }
    }

    public void setManaCost(int cost) {
        this.manaCost = cost;
        updateUI();
        invalidate();
    }

    public void setAttack(int attack) {
        this.attack = attack;
        updateUI();
    }

    public void setDefense(int defense) {
        this.defense = defense;
        updateUI();
    }

    public void setCardName(String name) {
        this.cardName = name;
        updateUI();
    }

    public void setFactionId(int factionId) {
        this.factionId = factionId;
        invalidate();
    }

    public int getManaCost() { return manaCost; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public String getCardName() { return cardName; }
    public int getFactionId() { return factionId; }
}
