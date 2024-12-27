package com.example.graphsolver.Model;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class NodeMap {
    private Map<Long, Node> nodeMap = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public synchronized Node addNode(String name, Long parentId) {
        Node node = new Node(idCounter.getAndIncrement(), name, null, new ArrayList<>());
        nodeMap.put(node.getId(), node);

        if (parentId != null && nodeMap.containsKey(parentId)) {
            Node parent = nodeMap.get(parentId);
            parent.addChild(node);
            node.setParent(parent);
        }
        return node;
    }
    public Node getNode(Long id) {
        return nodeMap.get(id);
    }

    public boolean containsNode(String id) {
        return nodeMap.containsKey(id);
    }

    public synchronized void removeNode(Long id) {
        Node node = nodeMap.get(id);
        if (node != null) {
            Node parent = node.getParent();
            if (parent != null) {
                parent.getChildren().remove(node);
            }
            for (Node child : node.getChildren()) {
                child.setParent(null);
            }
            nodeMap.remove(id);
        }
    }

    public Collection<Node> getAllNodes() {
        return nodeMap.values();
    }
}
