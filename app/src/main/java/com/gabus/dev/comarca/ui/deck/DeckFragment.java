package com.gabus.dev.comarca.ui.deck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.databinding.FragmentDeckBinding;
import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;
    private DeckViewModel viewModel;
    private DeckAdapter deckAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDeckBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DeckViewModel.class);

        setupRecyclerView();
        setupEditButton();
        loadDemoDeck();
    }

    private void setupRecyclerView() {
        deckAdapter = new DeckAdapter();
        binding.rvCardList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCardList.setAdapter(deckAdapter);
    }

    private void setupEditButton() {
        binding.btnEditDeck.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_deck_to_deckBuilder);
        });
    }

    private void loadDemoDeck() {
        List<Card> demoDeck = new ArrayList<>();
        demoDeck.add(new Card(1, "Vanguardia de Roble", 1, 2, 4, 3, "Human Infantry", null, 0));
        demoDeck.add(new Card(2, "Herrero de Bronce", 2, 2, 3, 5, "Dwarven Defender", null, 0));
        demoDeck.add(new Card(3, "Cazador del Bosque", 1, 1, 5, 1, "Human Ranged", null, 0));
        demoDeck.add(new Card(4, "Bardo Cervecero", 2, 1, 1, 2, "Dwarven Support", null, 0));
        demoDeck.add(new Card(5, "Escudero Real", 1, 1, 2, 4, "Human Tank", null, 0));
        demoDeck.add(new Card(6, "Minero Enano", 2, 2, 3, 3, "Dwarven Miner", null, 0));

        deckAdapter.submitList(demoDeck);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
