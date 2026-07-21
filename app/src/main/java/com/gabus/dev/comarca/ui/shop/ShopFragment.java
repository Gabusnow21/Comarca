package com.gabus.dev.comarca.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.databinding.FragmentShopBinding;
import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;
    private ShopViewModel viewModel;
    private ShopAdapter shopAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        setupRecyclerView();
        setupContinueButton();
        observeViewModel();

        loadDemoDeck();
    }

    private void setupRecyclerView() {
        shopAdapter = new ShopAdapter();
        binding.rvShopCards.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvShopCards.setAdapter(shopAdapter);

        shopAdapter.setOnUpgradeClickListener(new ShopAdapter.OnUpgradeClickListener() {
            @Override
            public void onUpgradeAttack(Card card, int cost) {
                viewModel.upgradeAttack(card);
            }

            @Override
            public void onUpgradeDefense(Card card, int cost) {
                viewModel.upgradeDefense(card);
            }

            @Override
            public void onReduceMana(Card card, int cost) {
                viewModel.reduceMana(card);
            }
        });
    }

    private void setupContinueButton() {
        binding.btnShopContinue.setOnClickListener(v -> {
            viewModel.finishShopping();
            Navigation.findNavController(v).navigate(R.id.action_shop_to_combat);
        });
    }

    private void observeViewModel() {
        viewModel.deckCards.observe(getViewLifecycleOwner(), cards -> {
            shopAdapter.submitList(cards, viewModel.gold.getValue() != null ? viewModel.gold.getValue() : 0);
        });

        viewModel.gold.observe(getViewLifecycleOwner(), gold -> {
            binding.tvShopGold.setText(getString(R.string.shop_gold, gold));
            shopAdapter.updateGold(gold);
        });
    }

    private void loadDemoDeck() {
        ArrayList<Card> demoDeck = new ArrayList<>();
        demoDeck.add(new Card(1, "Strike", 1, 1, 3, 0, "Basic attack", null, 0));
        demoDeck.add(new Card(2, "Defend", 2, 1, 0, 5, "Basic defense", null, 0));
        demoDeck.add(new Card(3, "Quick Hit", 1, 1, 2, 0, "Fast attack", null, 0));
        demoDeck.add(new Card(4, "Power Strike", 3, 2, 6, 0, "Powerful attack", null, 0));

        viewModel.setDeck(demoDeck, 50);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
