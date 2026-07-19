package com.gabus.dev.comarca.domain.usecase;

import com.gabus.dev.comarca.domain.model.CombatEffect;
import com.gabus.dev.comarca.domain.model.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class EnemyAI {

    private final Random random;

    @Inject
    public EnemyAI() {
        this.random = new Random();
    }

    public List<CombatEffect> executeTurn(Enemy enemy) {
        List<CombatEffect> effects = new ArrayList<>();

        switch (enemy.getPattern()) {
            case AGGRESSIVE:
                effects.add(new CombatEffect.DamageToPlayer(enemy.getAttackPower()));
                break;
            case DEFENSIVE:
                if (enemy.getCurrentHP() < enemy.getMaxHP() / 2) {
                    effects.add(new CombatEffect.DamageToPlayer(enemy.getAttackPower() / 2));
                } else {
                    effects.add(new CombatEffect.DamageToPlayer(enemy.getAttackPower()));
                }
                break;
            case BALANCED:
                int attackChance = random.nextInt(100);
                if (attackChance < 70) {
                    effects.add(new CombatEffect.DamageToPlayer(enemy.getAttackPower()));
                } else {
                    effects.add(new CombatEffect.DamageToPlayer(enemy.getAttackPower() / 2));
                }
                break;
            case BERSERKER:
                int bonusDamage = enemy.getAttackPower();
                if (enemy.getCurrentHP() < enemy.getMaxHP() / 3) {
                    bonusDamage *= 2;
                }
                effects.add(new CombatEffect.DamageToPlayer(bonusDamage));
                break;
        }

        return effects;
    }
}
