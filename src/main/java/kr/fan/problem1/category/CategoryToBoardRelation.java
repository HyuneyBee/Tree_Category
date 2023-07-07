package kr.fan.problem1.category;

import java.util.Set;

/**
 * Category 와 Board 관계를 관리하는 Class
 */
public class CategoryToBoardRelation {
    private final Integer categoryId;
    private final Children childrenIds;

    public CategoryToBoardRelation(Integer categoryId) {
        this.categoryId = categoryId;
        this.childrenIds = new Children();
    }

    public void addChildId(Integer childId){
        childrenIds.add(childId);
    }

    public void deleteChildId(Integer childId){
        childrenIds.delete(childId);
    }

    public Set<Integer> getChildrenIds(){
        return childrenIds.getChildrenIds();
    }
}
