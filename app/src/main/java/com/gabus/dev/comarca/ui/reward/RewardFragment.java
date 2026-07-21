package com.gabus.dev.comarca.ui.reward;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.databinding.FragmentRewardBinding;
import com.gabus.dev.comarca.ui.components.CardViewCustom;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RewardFragment extends Fragment {

    private FragmentRewardBinding binding;
    private RewardViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRewardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RewardViewModel.class);

        setupCardChoices();
        setupContinueButton();
        observeViewModel();

        viewModel.generateLoot(1, false);
    }

    private void setupCardChoices() {
        binding.cardChoicesContainer.removeAllViews();
    }

    private void setupContinueButton() {
        binding.btnContinue.setOnClickListener(v -> {
            if (viewModel.getSelectedCard() != null) {
                Navigation.findNavController(v).navigate(R.id.action_reward_to_shop);
            }
        });
    }

    private void observeViewModel() {
        viewModel.lootChoices.observe(getViewLifecycleOwner(), this::renderLootChoices);
        viewModel.goldEarned.observe(getViewLifecycleOwner(), gold -> {
            binding.tvGoldAmount.setText(String.valueOf(gold));
        });
        viewModel.cardSelectionComplete.observe(getViewLifecycleOwner(), complete -> {
            binding.btnContinue.setEnabled(complete);
        });
    }

    private void renderLootChoices(List<CardEntity> cards) {
        binding.cardChoicesContainer.removeAllViews();

        for (CardEntity card : cards) {
            CardViewCustom cardView = new CardViewCustom(requireContext());
            cardView.setCardName(card.name);
            cardView.setManaCost(card.manaCost);
            cardView.setAttack(card.attack);
            cardView.setDefense(card.defense);
            cardView.setFactionId((int) card.factionId);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.hand_card_width),
                    getResources().getDimensionPixelSize(R.dimen.hand_card_height)
            );
            params.setMargins(8, 0, 8, 0);
            cardView.setLayoutParams(params);

            cardView.setOnClickListener(v -> {
                viewModel.selectCard(card);
                highlightSelectedCard(cardView);
            });

            binding.cardChoicesContainer.addView(cardView);
        }
    }

    private void highlightSelectedCard(CardViewCustom selectedCardView) {
        for (int i = 0; i < binding.cardChoicesContainer.getChildCount(); i++) {
            View child = binding.cardChoicesContainer.getChildAt(i);
            if (child instanceof CardViewCustom) {
                child.setAlpha(child == selectedCardView ? 1.0f : 0.5f);
                child.setScaleX(child == selectedCardView ? 1.1f : 1.0f);
                child.setScaleY(child == selectedCardView ? 1.1f : 1.0f);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
