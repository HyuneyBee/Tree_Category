package kr.fan.problem1.node;

import kr.fan.problem1.category.Category;

/**
 * Category 관계를 표현하기 위한 Class
 */
public class CategoryNode implements Node{
    private final Integer id;
    private final String name;
    private final ChildrenNodes categories;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChildrenNodes getCategories() {
        return categories;
    }

    public CategoryNode(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.categories = new ChildrenNodes();
    }

    @Override
    public void addNode(Node node) {
        categories.add(node);
    }
}
