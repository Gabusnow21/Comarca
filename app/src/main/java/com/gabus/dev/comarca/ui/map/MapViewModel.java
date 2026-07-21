package com.gabus.dev.comarca.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabus.dev.comarca.domain.model.MapNode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    private final MutableLiveData<List<MapNode>> _nodes = new MutableLiveData<>();
    public final LiveData<List<MapNode>> nodes = _nodes;

    private final MutableLiveData<MapNode> _selectedNode = new MutableLiveData<>();
    public final LiveData<MapNode> selectedNode = _selectedNode;

    private final MutableLiveData<Integer> _currentLevel = new MutableLiveData<>(1);
    public final LiveData<Integer> currentLevel = _currentLevel;

    @Inject
    public MapViewModel() {
        initializeMap();
    }

    private void initializeMap() {
        List<MapNode> mapNodes = new ArrayList<>();

        mapNodes.add(new MapNode(0, MapNode.NodeType.START, "Inicio", true, false, 40, 60));
        mapNodes.add(new MapNode(1, MapNode.NodeType.COMBAT, "Combate 1", false, true, 80, 40));
        mapNodes.add(new MapNode(2, MapNode.NodeType.TREASURE, "Tesoro", false, false, 120, 60));
        mapNodes.add(new MapNode(3, MapNode.NodeType.COMBAT, "Combate 2", false, false, 160, 40));
        mapNodes.add(new MapNode(4, MapNode.NodeType.REST, "Descanso", false, false, 200, 60));
        mapNodes.add(new MapNode(5, MapNode.NodeType.COMBAT, "Combate 3", false, false, 240, 40));
        mapNodes.add(new MapNode(6, MapNode.NodeType.BOSS, "Jefe Final", false, false, 280, 60));

        _nodes.setValue(mapNodes);
    }

    public void selectNode(MapNode node) {
        if (node.isAvailable() && !node.isCompleted()) {
            _selectedNode.setValue(node);
        }
    }

    public void completeNode(int nodeId) {
        List<MapNode> currentNodes = _nodes.getValue();
        if (currentNodes == null) return;

        for (MapNode node : currentNodes) {
            if (node.getId() == nodeId) {
                node.setCompleted(true);
                node.setAvailable(false);
                break;
            }
        }

        for (MapNode node : currentNodes) {
            if (!node.isCompleted() && isNextNode(node, currentNodes)) {
                node.setAvailable(true);
            }
        }

        _nodes.setValue(currentNodes);
    }

    private boolean isNextNode(MapNode node, List<MapNode> allNodes) {
        int nodeIndex = allNodes.indexOf(node);
        if (nodeIndex <= 0) return false;

        MapNode previousNode = allNodes.get(nodeIndex - 1);
        return previousNode.isCompleted();
    }

    public void advanceLevel() {
        Integer currentLevelValue = _currentLevel.getValue();
        if (currentLevelValue != null) {
            _currentLevel.setValue(currentLevelValue + 1);
        }
    }
}
