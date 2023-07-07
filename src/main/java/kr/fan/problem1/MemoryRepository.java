package kr.fan.problem1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.fan.problem1.board.Board;
import kr.fan.problem1.category.Category;
import kr.fan.problem1.category.CategoryRelation;
import kr.fan.problem1.category.CategoryToBoardRelation;
import kr.fan.problem1.node.CategoryLeafNode;
import kr.fan.problem1.node.CategoryNode;
import kr.fan.problem1.node.Node;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository {
    private final Map<Integer, Category> categoryMap = new HashMap<>();
    private final Map<Integer, Board> BoardMap = new HashMap<>();
    private final Map<Integer, CategoryRelation> categoryRelationMap = new HashMap<>();
    private final Map<Integer, CategoryToBoardRelation> categoryToBoardRelationMap = new HashMap<>();
    private final static ObjectMapper objectMapper = new ObjectMapper();


    public void saveCategory(Category category){
        if(categoryMap.containsKey(category.getId())) throw new RuntimeException("이미 존재하는 key 입니다. 다른 key 입력해주세요");

        categoryMap.put(category.getId(), category);

        categoryRelationMap.put(category.getId(), new CategoryRelation(category.getId()));
        categoryToBoardRelationMap.put(category.getId(), new CategoryToBoardRelation(category.getId()));
    }

    public void saveBoard(Board board){
        if(BoardMap.containsKey(board.getId())) throw new RuntimeException("이미 존재하는 key 입니다. 다른 key 입력해주세요");
        BoardMap.put(board.getId(), board);
    }

    public void addChildToParent(Category parent, Category child){
        if(categoryMap.containsKey(parent.getId()) && categoryMap.containsKey(child.getId())){
            CategoryRelation parentRelation = categoryRelationMap.get(parent.getId());
            parentRelation.addChildId(child.getId());
            return;
        }
        throw new RuntimeException("parent 또는 child 존재하지 않습니다.");
    }

    public void addBoardToCategory(Category category, Board board){
        if(categoryMap.containsKey(category.getId()) && BoardMap.containsKey(board.getId())){
            CategoryToBoardRelation categoryRelation = categoryToBoardRelationMap.get(category.getId());
            categoryRelation.addChildId(board.getId());
            return;
        }
        throw new RuntimeException("category 또는 board 가 존재하지 않습니다.");
    }

    public String searchCategoryToId(Integer categoryId) throws JsonProcessingException {
        Category category = categoryMap.get(categoryId);
        if(isLeafCategory(category)){
            Node leafNode = createLeafNode(category);
            return objectMapper.writeValueAsString(leafNode);
        }
        CategoryNode categoryNode = new CategoryNode(category);
        Node node = makeCategoryData(categoryId, categoryNode);
        return objectMapper.writeValueAsString(node);
    }

    public String searchCategoryToName(String name){
        return "";
    }


    private Node makeCategoryData(Integer categoryId, Node node){
        Category category = categoryMap.get(categoryId);
        CategoryRelation categoryRelation = categoryRelationMap.get(category.getId());

        for(Integer id: categoryRelation.getChildrenIds()){
            Category childCategory = categoryMap.get(id);

            if(isLeafCategory(childCategory)){
                Node leafNode = createLeafNode(childCategory);
                node.addNode(leafNode);
                continue;
            }

            CategoryNode categoryNode = new CategoryNode(childCategory);
            node.addNode(categoryNode);
            makeCategoryData(id, categoryNode);
        }
        return node;
    }

    private boolean isLeafCategory(Category category){
        return !categoryToBoardRelationMap.get(category.getId()).getChildrenIds().isEmpty();
    }

    private Node createLeafNode(Category category){
        Node categoryLeafNode = new CategoryLeafNode(category);
        CategoryToBoardRelation categoryToBoardRelation = categoryToBoardRelationMap.get(category.getId());
        for(Integer id : categoryToBoardRelation.getChildrenIds()){
            Board board = BoardMap.get(id);
            categoryLeafNode.addBoard(board);
        }
        return categoryLeafNode;
    }


}
