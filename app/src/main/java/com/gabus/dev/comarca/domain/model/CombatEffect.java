package com.gabus.dev.comarca.domain.model;

import java.util.List;

public abstract class CombatEffect {

    public enum EffectType {
        DAMAGE_TO_ENEMY,
        DAMAGE_TO_PLAYER,
        HEAL_PLAYER,
        DRAW_CARDS,
        GAIN_MANA,
        APPLY_STATUS
    }

    private final EffectType type;

    protected CombatEffect(EffectType type) {
        this.type = type;
    }

    public EffectType getType() { return type; }

    public static class DamageToEnemy extends CombatEffect {
        private final int amount;

        public DamageToEnemy(int amount) {
            super(EffectType.DAMAGE_TO_ENEMY);
            this.amount = amount;
        }

        public int getAmount() { return amount; }
    }

    public static class DamageToPlayer extends CombatEffect {
        private final int amount;

        public DamageToPlayer(int amount) {
            super(EffectType.DAMAGE_TO_PLAYER);
            this.amount = amount;
        }

        public int getAmount() { return amount; }
    }

    public static class HealPlayer extends CombatEffect {
        private final int amount;

        public HealPlayer(int amount) {
            super(EffectType.HEAL_PLAYER);
            this.amount = amount;
        }

        public int getAmount() { return amount; }
    }

    public static class DrawCards extends CombatEffect {
        private final int count;

        public DrawCards(int count) {
            super(EffectType.DRAW_CARDS);
            this.count = count;
        }

        public int getCount() { return count; }
    }

    public static class GainMana extends CombatEffect {
        private final int amount;

        public GainMana(int amount) {
            super(EffectType.GAIN_MANA);
            this.amount = amount;
        }

        public int getAmount() { return amount; }
    }
}
