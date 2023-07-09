package kr.fan.problem1.category;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Domain 관계에서 자식 관계의 Id 관리하는 Class
 */
public class Children {
    private final SortedSet<Integer> children = new TreeSet<>();


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
