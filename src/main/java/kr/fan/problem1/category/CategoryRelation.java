package kr.fan.problem1.category;

import java.util.Set;

/**
 * Category 간의 관계를 관리하는 Class
 */
public class CategoryRelation {
    private final Integer parentId;
    private final Children childrenIds;

    public CategoryRelation(Integer parentId) {
        this.parentId = parentId;
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
