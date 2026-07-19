package com.gabus.dev.comarca.ui.combat;

import androidx.lifecycle.ViewModel;

import com.gabus.dev.comarca.domain.model.CombatAction;
import com.gabus.dev.comarca.domain.model.CombatEffect;
import com.gabus.dev.comarca.domain.model.CombatState;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.model.Enemy;
import com.gabus.dev.comarca.domain.model.Player;
import com.gabus.dev.comarca.domain.usecase.CombatUseCase;
import com.gabus.dev.comarca.domain.usecase.EnemyAI;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

@HiltViewModel
public class CombatViewModel extends ViewModel {

    private final CombatUseCase combatUseCase;
    private final EnemyAI enemyAI;

    private final MutableStateFlow<CombatState> _combatState;
    public final StateFlow<CombatState> combatState;

    @Inject
    public CombatViewModel(CombatUseCase combatUseCase, EnemyAI enemyAI) {
        this.combatUseCase = combatUseCase;
        this.enemyAI = enemyAI;
        this._combatState = StateFlowKt.MutableStateFlow(null);
        this.combatState = _combatState;
    }

    public void startCombat(Player player, Enemy enemy) {
        CombatState state = new CombatState(player, enemy);
        _combatState.setValue(state);
    }

    public void playCard(Card card) {
        CombatState state = _combatState.getValue();
        if (state == null || state.isGameOver()) return;

        List<CombatEffect> effects = combatUseCase.processAction(state, new CombatAction.PlayCard(card));
        applyEffects(state, effects);
        _combatState.setValue(state);
    }

    public void endTurn() {
        CombatState state = _combatState.getValue();
        if (state == null || state.isGameOver()) return;

        combatUseCase.processAction(state, new CombatAction.EndTurn());
        _combatState.setValue(state);

        executeEnemyTurn();
    }

    private void executeEnemyTurn() {
        CombatState state = _combatState.getValue();
        if (state == null || state.isGameOver()) return;

        List<CombatEffect> effects = enemyAI.executeTurn(state.getEnemy());
        applyEffects(state, effects);

        state.setPhase(CombatState.Phase.PLAYER_TURN);
        state.setTurnCount(state.getTurnCount() + 1);
        state.setCardPlayed(false);
        state.getPlayer().restoreMana();

        _combatState.setValue(state);
    }

    private void applyEffects(CombatState state, List<CombatEffect> effects) {
        for (CombatEffect effect : effects) {
            switch (effect.getType()) {
                case DAMAGE_TO_ENEMY:
                    state.getEnemy().takeDamage(((CombatEffect.DamageToEnemy) effect).getAmount());
                    break;
                case DAMAGE_TO_PLAYER:
                    state.getPlayer().takeDamage(((CombatEffect.DamageToPlayer) effect).getAmount());
                    break;
                case HEAL_PLAYER:
                    state.getPlayer().heal(((CombatEffect.HealPlayer) effect).getAmount());
                    break;
                case DRAW_CARDS:
                    break;
                case GAIN_MANA:
                    break;
            }
        }
    }
}
