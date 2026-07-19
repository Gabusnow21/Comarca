package com.gabus.dev.comarca.domain.usecase;

import com.gabus.dev.comarca.domain.model.CombatAction;
import com.gabus.dev.comarca.domain.model.CombatEffect;
import com.gabus.dev.comarca.domain.model.CombatState;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.model.Enemy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CombatUseCase {

    @Inject
    public CombatUseCase() {
    }

    public List<CombatEffect> processAction(CombatState state, CombatAction action) {
        List<CombatEffect> effects = new ArrayList<>();

        if (state.isGameOver()) {
            return effects;
        }

        switch (action.getType()) {
            case PLAY_CARD:
                effects.addAll(handlePlayCard(state, (CombatAction.PlayCard) action));
                break;
            case END_TURN:
                effects.addAll(handleEndTurn(state));
                break;
            case USE_ABILITY:
                effects.addAll(handleUseAbility(state, (CombatAction.UseAbility) action));
                break;
        }

        state.checkGameOver();
        return effects;
    }

    private List<CombatEffect> handlePlayCard(CombatState state, CombatAction.PlayCard action) {
        List<CombatEffect> effects = new ArrayList<>();
        Card card = action.getCard();

        if (state.getCurrentPhase() != CombatState.Phase.PLAYER_TURN) {
            return effects;
        }

        if (!state.getPlayer().spendMana(card.getManaCost())) {
            state.setLastActionMessage("Maná insuficiente");
            return effects;
        }

        effects.add(new CombatEffect.DamageToEnemy(card.getAttack()));

        if (card.getDefense() > 0) {
            effects.add(new CombatEffect.HealPlayer(card.getDefense()));
        }

        state.setCardPlayed(true);
        state.setLastActionMessage("Jugaste " + card.getName());
        return effects;
    }

    private List<CombatEffect> handleEndTurn(CombatState state) {
        List<CombatEffect> effects = new ArrayList<>();

        if (state.getCurrentPhase() != CombatState.Phase.PLAYER_TURN) {
            return effects;
        }

        state.setPhase(CombatState.Phase.ENEMY_TURN);
        state.setLastActionMessage("Turno del enemigo");
        return effects;
    }

    private List<CombatEffect> handleUseAbility(CombatState state, CombatAction.UseAbility action) {
        List<CombatEffect> effects = new ArrayList<>();
        state.setLastActionMessage("Habilidad usada: " + action.getAbilityName());
        return effects;
    }
}
