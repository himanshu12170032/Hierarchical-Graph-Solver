package com.example.graphsolver.Controller;

import com.example.graphsolver.Model.Node;
import com.example.graphsolver.Request.NodeRequest;
import com.example.graphsolver.Response.NodeResponse;
import com.example.graphsolver.Service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/nodes")
public class GraphController {
    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping
    public ResponseEntity<NodeResponse> addNode(@RequestBody NodeRequest request) {
        Node node = graphService.addNode(request.getNodeName(), request.getParentId());
        return new ResponseEntity<>(mapToDto(node), HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<NodeResponse> getNode(@PathVariable Long id) {
        Node node = graphService.getNode(id);
        return new ResponseEntity<>(mapToDto(node), HttpStatus.OK);
    }

    private NodeResponse mapToDto(Node node) {
        return mapToDto(node, new HashSet<>());
    }

    private NodeResponse mapToDto(Node node, Set<Long> visited) {
        if (node == null || visited.contains(node.getId())) {
            return null;
        }

        visited.add(node.getId());

        List<NodeResponse> children = new ArrayList<>();
        for (Node child : node.getChildren()) {
            NodeResponse childDto = mapToDto(child, visited);
            if (childDto != null) {
                children.add(childDto);
            }
        }
        return new NodeResponse(node.getId(), node.getName(), children);
    }



    @GetMapping("/{id}/depth")
    public ResponseEntity<Integer> calculateDepth(@PathVariable Long id) {
        int depth = graphService.calculateDepth(id);
        return new ResponseEntity<>(depth, HttpStatus.OK);
    }

    @GetMapping("/paths")
    public ResponseEntity<List<String>> getPathBetweenTwoNodes(@RequestParam Long startNodeId, @RequestParam Long endNodeId) {
        List<String> path = graphService.findPath(startNodeId, endNodeId);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }

    @GetMapping("/common-ancestor")
    public ResponseEntity<String> getCommonAncestor(@RequestParam Long nodeId1, @RequestParam Long nodeId2) {
        String ancestor = graphService.getCommonAncestor(nodeId1, nodeId2);
        return new ResponseEntity<>(ancestor, HttpStatus.OK);
    }
}
