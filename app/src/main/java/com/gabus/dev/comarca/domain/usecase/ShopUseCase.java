package com.gabus.dev.comarca.domain.usecase;

import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ShopUseCase {

    private static final int BASE_UPGRADE_COST = 15;
    private static final int ATTACK_UPGRADE_AMOUNT = 2;
    private static final int DEFENSE_UPGRADE_AMOUNT = 2;
    private static final int MANA_REDUCTION_AMOUNT = 1;

    @Inject
    public ShopUseCase() {
    }

    public int getUpgradeAttackCost(int currentAttack) {
        return BASE_UPGRADE_COST + (currentAttack * 2);
    }

    public int getUpgradeDefenseCost(int currentDefense) {
        return BASE_UPGRADE_COST + (currentDefense * 2);
    }

    public int getReduceManaCost(int currentManaCost) {
        if (currentManaCost <= 1) return Integer.MAX_VALUE;
        return BASE_UPGRADE_COST + (currentManaCost * 3);
    }

    public boolean canAfford(int gold, int cost) {
        return gold >= cost;
    }

    public Card upgradeAttack(Card card) {
        int newAttack = card.getAttack() + ATTACK_UPGRADE_AMOUNT;
        return new Card(
                card.getId(),
                card.getName(),
                card.getFactionId(),
                card.getManaCost(),
                newAttack,
                card.getDefense(),
                card.getDescription(),
                card.getAbility(),
                card.getIconRes()
        );
    }

    public Card upgradeDefense(Card card) {
        int newDefense = card.getDefense() + DEFENSE_UPGRADE_AMOUNT;
        return new Card(
                card.getId(),
                card.getName(),
                card.getFactionId(),
                card.getManaCost(),
                card.getAttack(),
                newDefense,
                card.getDescription(),
                card.getAbility(),
                card.getIconRes()
        );
    }

    public Card reduceManaCost(Card card) {
        int newManaCost = Math.max(1, card.getManaCost() - MANA_REDUCTION_AMOUNT);
        return new Card(
                card.getId(),
                card.getName(),
                card.getFactionId(),
                newManaCost,
                card.getAttack(),
                card.getDefense(),
                card.getDescription(),
                card.getAbility(),
                card.getIconRes()
        );
    }
}
