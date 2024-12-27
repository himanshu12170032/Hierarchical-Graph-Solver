package com.example.graphsolver.Service;

import com.example.graphsolver.Model.Node;
import com.example.graphsolver.Model.NodeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GraphService {
    private final NodeMap nodeMap;

    @Autowired
    GraphService(NodeMap nodeMap) {
        this.nodeMap = nodeMap;
    }

    public Node addNode(String name, Long parentId) {
        return nodeMap.addNode(name, parentId);
    }

    public Node getNode(Long id) {
        return nodeMap.getNode(id);
    }

    public int calculateDepth(Long id) {
        Node node = nodeMap.getNode(id);
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.getParent();
        }
        return depth - 1;
    }

    public List<String> findPath(Long startId, Long endId) {
        Node startNode = nodeMap.getNode(startId);
        Node endNode = nodeMap.getNode(endId);
        
        if (startNode == null || endNode == null) {
            throw new IllegalArgumentException("Invalid node IDs provided.");
        }

        Node commonAncestor = findLCA(startNode, endNode);

        if (commonAncestor == null) {
            throw new IllegalArgumentException("No common ancestor found between the nodes.");
        }

        List<String> path = new ArrayList<>();
        buildPath(startNode, commonAncestor, path, true);
        buildPath(endNode, commonAncestor, path, false);

        return path;
    }



    private void buildPath(Node node, Node stopNode, List<String> path, boolean addToPath) {
        while (node != null && node != stopNode) {
            if (addToPath) {
                path.add(node.getName());
            } else {
                path.add(0, node.getName());
            }
            node = node.getParent();
        }

        if (stopNode != null && addToPath) {
            path.add(stopNode.getName());
        }
    }


    public String getCommonAncestor(Long id1, Long id2) {
        Node lca = findLCA(nodeMap.getNode(id1), nodeMap.getNode(id2));
        return lca != null ? lca.getName() : null;
    }

    private Node findLCA(Node node1, Node node2) {
        Set<Node> ancestors = new HashSet<>();
        while (node1 != null) {
            ancestors.add(node1);
            node1 = node1.getParent();
        }
        while (node2 != null) {
            if (ancestors.contains(node2)) return node2;
            node2 = node2.getParent();
        }
        return null;
    }

}
