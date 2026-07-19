package com.gabus.dev.comarca.ui.combat;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.databinding.FragmentCombatBinding;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.model.CombatState;
import com.gabus.dev.comarca.domain.model.Enemy;
import com.gabus.dev.comarca.domain.model.Player;
import com.gabus.dev.comarca.ui.components.CardViewCustom;
import com.gabus.dev.comarca.ui.components.HealthBarView;
import com.gabus.dev.comarca.ui.components.ManaBarView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CombatFragment extends Fragment {

    private FragmentCombatBinding binding;
    private CombatViewModel viewModel;
    private HandAdapter handAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCombatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CombatViewModel.class);

        setupHandRecyclerView();
        setupEndTurnButton();
        observeCombatState();
        startDemoCombat();
    }

    private void setupHandRecyclerView() {
        handAdapter = new HandAdapter();
        binding.rvHand.setLayoutManager(new LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvHand.setAdapter(handAdapter);

        handAdapter.setOnCardClickListener((card, position) -> {
            handAdapter.setSelectedPosition(position);
            viewModel.playCard(card);
        });
    }

    private void setupEndTurnButton() {
        binding.btnEndTurn.setOnClickListener(v -> {
            // Animate button press
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 0f, 6f);
            animator.setDuration(100);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.start();

            v.postDelayed(() -> {
                ObjectAnimator returnAnim = ObjectAnimator.ofFloat(v, "translationY", 6f, 0f);
                returnAnim.setDuration(100);
                returnAnim.start();
            }, 100);

            viewModel.endTurn();
        });
    }

    private void observeCombatState() {
        // Observe via lifecycle
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                if (viewModel.combatState.getValue() != null) {
                    requireActivity().runOnUiThread(() -> updateUI(viewModel.combatState.getValue()));
                }
            }
        }).start();
    }

    private void updateUI(CombatState state) {
        if (state == null || state.isGameOver()) {
            if (state != null) {
                showGameOver(state);
            }
            return;
        }

        // Update enemy
        Enemy enemy = state.getEnemy();
        binding.tvEnemyName.setText(enemy.getName());
        binding.hpBarEnemy.setMax(enemy.getMaxHP());
        binding.hpBarEnemy.setProgress(enemy.getCurrentHP());

        // Update enemy intent (show next attack damage)
        binding.tvEnemyIntentValue.setText(String.valueOf(enemy.getAttackPower()));

        // Update player HP
        Player player = state.getPlayer();

        // Update mana
        binding.tvManaCount.setText(String.format("%d/%d", player.getCurrentMana(), player.getMaxMana()));
        updateManaGems(player.getCurrentMana(), player.getMaxMana());

        // Update hand
        handAdapter.submitList(player.getHand());

        // Update turn counter
        binding.tvTurnCount.setText(getString(R.string.combat_turn_count, state.getTurnCount()));

        // Update action message
        if (state.getLastActionMessage() != null && !state.getLastActionMessage().isEmpty()) {
            binding.tvActionMessage.setText(state.getLastActionMessage());
            binding.tvActionMessage.setVisibility(View.VISIBLE);
            binding.tvActionMessage.postDelayed(() -> {
                if (binding != null) {
                    binding.tvActionMessage.setVisibility(View.GONE);
                }
            }, 2000);
        }

        // Enable/disable end turn based on phase
        binding.btnEndTurn.setEnabled(state.getCurrentPhase() == CombatState.Phase.PLAYER_TURN);
    }

    private void updateManaGems(int current, int max) {
        LinearLayout container = binding.manaGemsContainer;
        container.removeAllViews();

        for (int i = 0; i < max; i++) {
            View gem = new View(requireContext());
            int size = getResources().getDimensionPixelSize(R.dimen.mana_gem_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(4, 0, 4, 0);
            gem.setLayoutParams(params);

            if (i < current) {
                gem.setBackgroundResource(R.drawable.bg_mana_gem);
            } else {
                gem.setBackgroundResource(R.drawable.bg_mana_gem_inactive);
            }

            // Rotate 45 degrees for diamond shape
            gem.setRotation(45f);

            container.addView(gem);
        }
    }

    private void showGameOver(CombatState state) {
        if (state.getCurrentPhase() == CombatState.Phase.VICTORY) {
            binding.tvActionMessage.setText(R.string.combat_victory);
        } else {
            binding.tvActionMessage.setText(R.string.combat_defeat);
        }
        binding.tvActionMessage.setVisibility(View.VISIBLE);
        binding.btnEndTurn.setEnabled(false);
    }

    private void startDemoCombat() {
        // Create demo player with cards
        Player player = new Player(80, 3);
        List<Card> deck = new ArrayList<>();
        deck.add(new Card(1, "Strike", 1, 1, 3, 0, "Golpe básico", null, 0));
        deck.add(new Card(2, "Defend", 2, 1, 0, 5, "Defensa sólida", null, 0));
        deck.add(new Card(3, "Quick Hit", 1, 1, 2, 0, "Golpe rápido", null, 0));
        deck.add(new Card(4, "Power Strike", 3, 2, 6, 0, "Golpe poderoso", null, 0));
        deck.add(new Card(5, "Shield Wall", 2, 2, 1, 6, "Muro de escudos", null, 0));
        deck.add(new Card(6, "Heal", 2, 1, 0, 3, "Curación menor", null, 0));
        player.setDeck(deck);

        // Draw initial hand (3 cards)
        for (int i = 0; i < 3 && i < deck.size(); i++) {
            player.getHand().add(deck.get(i));
        }

        // Create demo enemy
        Enemy enemy = new Enemy("Cave Ogre", 50, 10, 2, Enemy.EnemyPattern.AGGRESSIVE);

        viewModel.startCombat(player, enemy);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
