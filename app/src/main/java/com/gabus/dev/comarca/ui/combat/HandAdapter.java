package com.gabus.dev.comarca.ui.combat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.ui.components.CardViewCustom;

import java.util.ArrayList;
import java.util.List;

public class HandAdapter extends RecyclerView.Adapter<HandAdapter.HandViewHolder> {

    public interface OnCardClickListener {
        void onCardClick(Card card, int position);
    }

    private final List<Card> cards = new ArrayList<>();
    private OnCardClickListener listener;
    private int selectedPosition = -1;

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Card> newCards) {
        cards.clear();
        if (newCards != null) {
            cards.addAll(newCards);
        }
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(int position) {
        int old = selectedPosition;
        selectedPosition = position;
        if (old >= 0) notifyItemChanged(old);
        if (position >= 0) notifyItemChanged(position);
    }

    @NonNull
    @Override
    public HandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hand_card, parent, false);
        return new HandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HandViewHolder holder, int position) {
        Card card = cards.get(position);
        CardViewCustom cardView = holder.itemView.findViewById(R.id.card_custom);

        cardView.setCardName(card.getName());
        cardView.setManaCost(card.getManaCost());
        cardView.setAttack(card.getAttack());
        cardView.setDefense(card.getDefense());
        cardView.setFactionId((int) card.getFactionId());

        // Rotation based on position for fan effect
        int total = cards.size();
        if (total > 1) {
            float rotation = ((position - (total - 1) / 2f) / (total - 1)) * 24f;
            float translationY = Math.abs(rotation) * 0.8f;
            holder.itemView.setRotation(rotation);
            holder.itemView.setTranslationY(translationY);
        } else {
            holder.itemView.setRotation(0f);
            holder.itemView.setTranslationY(0f);
        }

        // Selection state
        if (position == selectedPosition) {
            holder.itemView.setTranslationY(holder.itemView.getTranslationY() - 32f);
            holder.itemView.setScaleX(1.1f);
            holder.itemView.setScaleY(1.1f);
            holder.itemView.setElevation(20f);
        } else {
            holder.itemView.setScaleX(1f);
            holder.itemView.setScaleY(1f);
            holder.itemView.setElevation(4f + position);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCardClick(card, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class HandViewHolder extends RecyclerView.ViewHolder {
        HandViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
