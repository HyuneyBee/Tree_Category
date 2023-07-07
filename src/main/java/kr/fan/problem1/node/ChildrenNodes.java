package kr.fan.problem1.node;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashSet;
import java.util.Set;

/**
 * Category 관계에서 자식 Category 의 모음 Class
 */
public class ChildrenNodes {
    @JsonValue
    private final Set<Node> children = new HashSet<>();

    public Set<Node> getChildren() {
        return children;
    }

    public void add(Node childNode){
        children.add(childNode);
    }

    public void delete(Node childNode){
        children.remove(childNode);
    }

    public Set<Node> getChildrenNodes(){
        return children;
    }
}
