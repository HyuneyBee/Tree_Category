package kr.fan.problem1.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.fan.problem1.category.Category;

/**
 * Category 관계를 표현하기 위한 Class
 */
public class CategoryNode implements Node{
    @JsonProperty("categoryId")
    private final Integer id;
    @JsonProperty("categoryName")
    private final String name;
    private final ChildrenNodes childrenNodes;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChildrenNodes getChildrenNodes() {
        return childrenNodes;
    }

    public CategoryNode(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.childrenNodes = new ChildrenNodes();
    }

    @Override
    public void addNode(Node node) {
        childrenNodes.add(node);
    }
}
