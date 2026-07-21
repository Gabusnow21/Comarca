package com.gabus.dev.comarca.ui.deck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilderAdapter extends RecyclerView.Adapter<DeckBuilderAdapter.DeckCardViewHolder> {

    private List<Card> cards = new ArrayList<>();
    private boolean isCurrentDeck = false;
    private OnCardActionListener listener;

    public interface OnCardActionListener {
        void onAddCard(Card card);
        void onRemoveCard(Card card);
    }

    public void setOnCardActionListener(OnCardActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Card> cards, boolean isCurrentDeck) {
        this.cards = new ArrayList<>(cards);
        this.isCurrentDeck = isCurrentDeck;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeckCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deck_builder_card, parent, false);
        return new DeckCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckCardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.bind(card, isCurrentDeck, listener);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class DeckCardViewHolder extends RecyclerView.ViewHolder {
        private final View factionBorder;
        private final TextView tvCardName;
        private final TextView tvCardSubtitle;
        private final TextView tvCardAttack;
        private final TextView tvCardDefense;
        private final LinearLayout btnAction;
        private final TextView tvActionText;

        DeckCardViewHolder(@NonNull View itemView) {
            super(itemView);
            factionBorder = itemView.findViewById(R.id.faction_border);
            tvCardName = itemView.findViewById(R.id.tv_card_name);
            tvCardSubtitle = itemView.findViewById(R.id.tv_card_subtitle);
            tvCardAttack = itemView.findViewById(R.id.tv_card_attack);
            tvCardDefense = itemView.findViewById(R.id.tv_card_defense);
            btnAction = itemView.findViewById(R.id.btn_card_action);
            tvActionText = itemView.findViewById(R.id.tv_action_text);
        }

        void bind(Card card, boolean isCurrentDeck, OnCardActionListener listener) {
            tvCardName.setText(card.getName());
            tvCardSubtitle.setText(card.getDescription());
            tvCardAttack.setText(String.format("ATK: %d", card.getAttack()));
            tvCardDefense.setText(String.format("DEF: %d", card.getDefense()));

            int factionColor = getFactionColor((int) card.getFactionId());
            factionBorder.setBackgroundColor(factionColor);

            if (isCurrentDeck) {
                tvActionText.setText(R.string.deck_builder_remove);
                btnAction.setOnClickListener(v -> {
                    if (listener != null) listener.onRemoveCard(card);
                });
            } else {
                tvActionText.setText(R.string.deck_builder_add);
                btnAction.setOnClickListener(v -> {
                    if (listener != null) listener.onAddCard(card);
                });
            }
        }

        private int getFactionColor(int factionId) {
            switch (factionId) {
                case 1: return itemView.getContext().getResources().getColor(R.color.faction_human, null);
                case 2: return itemView.getContext().getResources().getColor(R.color.faction_dwarf, null);
                case 3: return itemView.getContext().getResources().getColor(R.color.faction_ogre, null);
                case 4: return itemView.getContext().getResources().getColor(R.color.faction_elf, null);
                default: return itemView.getContext().getResources().getColor(R.color.outline, null);
            }
        }
    }
}
