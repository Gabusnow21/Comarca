package com.gabus.dev.comarca.ui.deck;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.DeckViewHolder> {

    private final List<Card> cards = new ArrayList<>();

    public void submitList(List<Card> newCards) {
        cards.clear();
        if (newCards != null) {
            cards.addAll(newCards);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_deck, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        Card card = cards.get(position);

        holder.tvCardName.setText(card.getName());
        holder.tvCardSubtitle.setText(card.getDescription() != null ? card.getDescription() : "");
        holder.tvCardAttack.setText(String.format(Locale.getDefault(), "⚔ %d", card.getAttack()));
        holder.tvCardDefense.setText(String.format(Locale.getDefault(), "🛡 %d", card.getDefense()));

        // Faction color border
        int factionColor = getFactionColor(card.getFactionId());
        holder.factionBorder.setBackgroundColor(factionColor);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    private int getFactionColor(long factionId) {
        switch ((int) factionId) {
            case 1: return Color.parseColor("#7ba4d4"); // Human
            case 2: return Color.parseColor("#e8a630"); // Dwarf
            case 3: return Color.parseColor("#6bbd4a"); // Ogre
            case 4: return Color.parseColor("#b07cd8"); // Elf
            default: return Color.parseColor("#a18d7f");
        }
    }

    static class DeckViewHolder extends RecyclerView.ViewHolder {
        final View factionBorder;
        final TextView tvCardName;
        final TextView tvCardSubtitle;
        final TextView tvCardAttack;
        final TextView tvCardDefense;

        DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            factionBorder = itemView.findViewById(R.id.faction_border);
            tvCardName = itemView.findViewById(R.id.tv_card_name);
            tvCardSubtitle = itemView.findViewById(R.id.tv_card_subtitle);
            tvCardAttack = itemView.findViewById(R.id.tv_card_attack);
            tvCardDefense = itemView.findViewById(R.id.tv_card_defense);
        }
    }
}
