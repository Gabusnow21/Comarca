package com.gabus.dev.comarca.ui.deck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.databinding.FragmentDeckBuilderBinding;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.ui.components.FactionBadgeView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeckBuilderFragment extends Fragment {

    private FragmentDeckBuilderBinding binding;
    private DeckBuilderViewModel viewModel;
    private DeckBuilderAdapter currentDeckAdapter;
    private DeckBuilderAdapter availableCardsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeckBuilderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DeckBuilderViewModel.class);

        setupRecyclerViews();
        setupFactionBalance();
        observeViewModel();
        loadDemoData();
    }

    private void setupRecyclerViews() {
        currentDeckAdapter = new DeckBuilderAdapter();
        binding.rvCurrentDeck.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCurrentDeck.setAdapter(currentDeckAdapter);

        availableCardsAdapter = new DeckBuilderAdapter();
        binding.rvAvailableCards.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvAvailableCards.setAdapter(availableCardsAdapter);

        currentDeckAdapter.setOnCardActionListener(new DeckBuilderAdapter.OnCardActionListener() {
            @Override
            public void onAddCard(Card card) {
            }

            @Override
            public void onRemoveCard(Card card) {
                viewModel.removeCardFromDeck(card);
            }
        });

        availableCardsAdapter.setOnCardActionListener(new DeckBuilderAdapter.OnCardActionListener() {
            @Override
            public void onAddCard(Card card) {
                viewModel.addCardToDeck(card);
            }

            @Override
            public void onRemoveCard(Card card) {
            }
        });
    }

    private void setupFactionBalance() {
        binding.factionBalanceContainer.removeAllViews();
    }

    private void observeViewModel() {
        viewModel.currentDeck.observe(getViewLifecycleOwner(), deck -> {
            currentDeckAdapter.submitList(deck, true);
            updateDeckCount(deck.size());
            updateFactionBalance(deck);
        });

        viewModel.availableCards.observe(getViewLifecycleOwner(), cards -> {
            availableCardsAdapter.submitList(cards, false);
        });
    }

    private void updateDeckCount(int count) {
        binding.tvDeckCount.setText(String.format("%d/8 cartas", count));
    }

    private void updateFactionBalance(List<Card> deck) {
        binding.factionBalanceContainer.removeAllViews();

        int[] factionCounts = new int[5];
        for (Card card : deck) {
            int factionId = (int) card.getFactionId();
            if (factionId >= 1 && factionId <= 4) {
                factionCounts[factionId]++;
            }
        }

        String[] factionNames = {"", "Humanos", "Enanos", "Ogros", "Elfos"};
        int[] factionColors = {0, R.color.faction_human, R.color.faction_dwarf, R.color.faction_ogre, R.color.faction_elf};

        for (int i = 1; i <= 4; i++) {
            if (factionCounts[i] > 0) {
                FactionBadgeView badge = new FactionBadgeView(requireContext());
                badge.setFaction(factionCounts[i], factionNames[i], getResources().getColor(factionColors[i], null));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(8, 0, 8, 0);
                badge.setLayoutParams(params);

                binding.factionBalanceContainer.addView(badge);
            }
        }
    }

    private void loadDemoData() {
        ArrayList<Card> currentDeck = new ArrayList<>();
        currentDeck.add(new Card(1, "Strike", 1, 1, 3, 0, "Basic attack", null, 0));
        currentDeck.add(new Card(2, "Defend", 2, 1, 0, 5, "Basic defense", null, 0));
        currentDeck.add(new Card(3, "Quick Hit", 1, 1, 2, 0, "Fast attack", null, 0));

        ArrayList<Card> availableCards = new ArrayList<>();
        availableCards.add(new Card(4, "Power Strike", 3, 2, 6, 0, "Powerful attack", null, 0));
        availableCards.add(new Card(5, "Shield Wall", 2, 2, 1, 6, "Shield wall", null, 0));
        availableCards.add(new Card(6, "Heal", 2, 1, 0, 3, "Minor heal", null, 0));
        availableCards.add(new Card(7, "Fireball", 3, 3, 8, 0, "Fire attack", null, 0));
        availableCards.add(new Card(8, "Ice Shield", 2, 2, 0, 7, "Ice defense", null, 0));

        viewModel.setDeck(currentDeck);
        viewModel.setAvailableCards(availableCards);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
