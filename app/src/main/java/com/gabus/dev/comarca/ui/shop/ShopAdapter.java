package com.gabus.dev.comarca.ui.shop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.usecase.ShopUseCase;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopCardViewHolder> {

    private List<Card> cards = new ArrayList<>();
    private int gold = 0;
    private final ShopUseCase shopUseCase = new ShopUseCase();
    private OnUpgradeClickListener listener;

    public interface OnUpgradeClickListener {
        void onUpgradeAttack(Card card, int cost);
        void onUpgradeDefense(Card card, int cost);
        void onReduceMana(Card card, int cost);
    }

    public void setOnUpgradeClickListener(OnUpgradeClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Card> cards, int gold) {
        this.cards = new ArrayList<>(cards);
        this.gold = gold;
        notifyDataSetChanged();
    }

    public void updateGold(int gold) {
        this.gold = gold;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_card, parent, false);
        return new ShopCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.bind(card, gold, shopUseCase, listener);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class ShopCardViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvAttack;
        private final TextView tvDefense;
        private final TextView tvMana;
        private final LinearLayout btnUpgradeAttack;
        private final LinearLayout btnUpgradeDefense;
        private final LinearLayout btnReduceMana;
        private final TextView tvAttackCost;
        private final TextView tvDefenseCost;
        private final TextView tvManaCost;

        ShopCardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_shop_card_name);
            tvAttack = itemView.findViewById(R.id.tv_shop_card_attack);
            tvDefense = itemView.findViewById(R.id.tv_shop_card_defense);
            tvMana = itemView.findViewById(R.id.tv_shop_card_mana);
            btnUpgradeAttack = itemView.findViewById(R.id.btn_upgrade_attack);
            btnUpgradeDefense = itemView.findViewById(R.id.btn_upgrade_defense);
            btnReduceMana = itemView.findViewById(R.id.btn_reduce_mana);
            tvAttackCost = itemView.findViewById(R.id.tv_attack_cost);
            tvDefenseCost = itemView.findViewById(R.id.tv_defense_cost);
            tvManaCost = itemView.findViewById(R.id.tv_mana_cost);
        }

        void bind(Card card, int gold, ShopUseCase shopUseCase, OnUpgradeClickListener listener) {
            tvName.setText(card.getName());
            tvAttack.setText(String.format("ATK: %d", card.getAttack()));
            tvDefense.setText(String.format("DEF: %d", card.getDefense()));
            tvMana.setText(String.format("MANA: %d", card.getManaCost()));

            int attackCost = shopUseCase.getUpgradeAttackCost(card.getAttack());
            int defenseCost = shopUseCase.getUpgradeDefenseCost(card.getDefense());
            int manaCost = shopUseCase.getReduceManaCost(card.getManaCost());

            tvAttackCost.setText(String.format("Coste: %d", attackCost));
            tvDefenseCost.setText(String.format("Coste: %d", defenseCost));
            tvManaCost.setText(manaCost == Integer.MAX_VALUE ? "Máx" : String.format("Coste: %d", manaCost));

            boolean canAffordAttack = shopUseCase.canAfford(gold, attackCost);
            boolean canAffordDefense = shopUseCase.canAfford(gold, defenseCost);
            boolean canAffordMana = manaCost != Integer.MAX_VALUE && shopUseCase.canAfford(gold, manaCost);

            btnUpgradeAttack.setAlpha(canAffordAttack ? 1.0f : 0.5f);
            btnUpgradeDefense.setAlpha(canAffordDefense ? 1.0f : 0.5f);
            btnReduceMana.setAlpha(canAffordMana ? 1.0f : 0.5f);

            btnUpgradeAttack.setOnClickListener(v -> {
                if (canAffordAttack && listener != null) {
                    listener.onUpgradeAttack(card, attackCost);
                }
            });

            btnUpgradeDefense.setOnClickListener(v -> {
                if (canAffordDefense && listener != null) {
                    listener.onUpgradeDefense(card, defenseCost);
                }
            });

            btnReduceMana.setOnClickListener(v -> {
                if (canAffordMana && listener != null) {
                    listener.onReduceMana(card, manaCost);
                }
            });
        }
    }
}
