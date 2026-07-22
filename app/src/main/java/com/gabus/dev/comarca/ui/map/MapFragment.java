package com.gabus.dev.comarca.ui.map;

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

import com.gabus.dev.comarca.R;
import com.gabus.dev.comarca.databinding.FragmentMapBinding;
import com.gabus.dev.comarca.domain.model.MapNode;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);

        observeViewModel();
        setupNodeClickListeners();
    }

    private void observeViewModel() {
        viewModel.nodes.observe(getViewLifecycleOwner(), this::updateMapNodes);
        viewModel.selectedNode.observe(getViewLifecycleOwner(), this::onNodeSelected);
    }

    private void setupNodeClickListeners() {
        binding.nodeStart.setOnClickListener(v -> viewModel.selectNode(getNodeByType(MapNode.NodeType.START)));
        binding.nodeCurrent.setOnClickListener(v -> viewModel.selectNode(getNodeByType(MapNode.NodeType.COMBAT)));
        binding.nodeLoot.setOnClickListener(v -> viewModel.selectNode(getNodeByType(MapNode.NodeType.TREASURE)));
        binding.nodeRest.setOnClickListener(v -> viewModel.selectNode(getNodeByType(MapNode.NodeType.REST)));
        binding.nodeBoss.setOnClickListener(v -> viewModel.selectNode(getNodeByType(MapNode.NodeType.BOSS)));
    }

    private MapNode getNodeByType(MapNode.NodeType type) {
        List<MapNode> nodes = viewModel.nodes.getValue();
        if (nodes == null) return null;

        for (MapNode node : nodes) {
            if (node.getType() == type) {
                return node;
            }
        }
        return null;
    }

    private void updateMapNodes(List<MapNode> nodes) {
        if (nodes == null) return;

        for (MapNode node : nodes) {
            View nodeView = getNodeView(node.getType());
            if (nodeView == null) continue;

            if (node.isCompleted()) {
                nodeView.setAlpha(0.5f);
                nodeView.setEnabled(false);
            } else if (node.isAvailable()) {
                nodeView.setAlpha(1.0f);
                nodeView.setEnabled(true);
                nodeView.setElevation(12f);
            } else {
                nodeView.setAlpha(0.3f);
                nodeView.setEnabled(false);
            }
        }
    }

    private View getNodeView(MapNode.NodeType type) {
        switch (type) {
            case START: return binding.nodeStart;
            case COMBAT: return binding.nodeCurrent;
            case TREASURE: return binding.nodeLoot;
            case REST: return binding.nodeRest;
            case BOSS: return binding.nodeBoss;
            default: return null;
        }
    }

    private void onNodeSelected(MapNode node) {
        if (node == null) return;

        switch (node.getType()) {
            case COMBAT:
                // Navigate to combat
                break;
            case TREASURE:
                // Show treasure
                break;
            case REST:
                // Show rest options
                break;
            case BOSS:
                // Navigate to boss combat
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
