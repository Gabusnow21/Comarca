package com.gabus.dev.comarca.domain.model;

public abstract class CombatAction {

    public enum ActionType {
        PLAY_CARD,
        END_TURN,
        USE_ABILITY
    }

    private final ActionType type;
    private final Card card;

    protected CombatAction(ActionType type, Card card) {
        this.type = type;
        this.card = card;
    }

    public ActionType getType() { return type; }
    public Card getCard() { return card; }

    public static class PlayCard extends CombatAction {
        public PlayCard(Card card) {
            super(ActionType.PLAY_CARD, card);
        }
    }

    public static class EndTurn extends CombatAction {
        public EndTurn() {
            super(ActionType.END_TURN, null);
        }
    }

    public static class UseAbility extends CombatAction {
        private final String abilityName;

        public UseAbility(Card card, String abilityName) {
            super(ActionType.USE_ABILITY, card);
            this.abilityName = abilityName;
        }

        public String getAbilityName() { return abilityName; }
    }
}
