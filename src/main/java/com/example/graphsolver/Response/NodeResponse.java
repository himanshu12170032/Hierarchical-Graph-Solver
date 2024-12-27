package com.example.graphsolver.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeResponse {
    private Long id;
    private String name;
    private List<NodeResponse> children;
}
