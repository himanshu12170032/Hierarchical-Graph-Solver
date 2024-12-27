package com.example.graphsolver.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeRequest {
    private String nodeName;
    private Long parentId;
}
