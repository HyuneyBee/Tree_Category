package kr.fan.problem1.category;

import java.util.HashSet;
import java.util.Set;

/**
 * Domain 관계에서 자식 관계의 Id 관리하는 Class
 */
public class Children {
    private final Set<Integer> children = new HashSet<>();


    public void add(Integer childId){
        children.add(childId);
    }

    public void delete(Integer childId){
        children.remove(childId);
    }

    public Set<Integer> getChildrenIds(){
        return children;
    }
}
