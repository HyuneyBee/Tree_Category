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
import java.util.Optional;
import java.util.Set;

/**
 * Category, Board 의 데이터 접근, 제어와 출력을 담당하는 Class
 */
public class MemoryRepository {
    private final Map<Integer, Category> categoryMap = new HashMap<>();
    private final Map<Integer, Board> boardMap = new HashMap<>();
    private final Map<Integer, CategoryRelation> categoryRelationMap = new HashMap<>();
    private final Map<Integer, CategoryToBoardRelation> categoryToBoardRelationMap = new HashMap<>();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Category 저장
     * @param category - Category
     */
    public void saveCategory(Category category){
        if(categoryMap.containsKey(category.getId())) throw new RuntimeException("이미 존재하는 category 입니다. 다른 category 입력해주세요");

        categoryMap.put(category.getId(), category);

        categoryRelationMap.put(category.getId(), new CategoryRelation(category.getId()));
        categoryToBoardRelationMap.put(category.getId(), new CategoryToBoardRelation(category.getId()));
    }

    /**
     * Category 반환
     * @param id - Category Id
     * @return Category
     */
    public Category getCategory(Integer id){
        if(!categoryMap.containsKey(id)) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");
        Category category = categoryMap.get(id);
        return new Category(category.getId(), category.getName());
    }

    /**
     * Category 삭제
     * @param category - Category
     */
    public void deleteCategory(Category category){
        if(!categoryMap.containsKey(category.getId())) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");

        categoryMap.remove(category.getId());

        categoryRelationMap.remove(category.getId());
        categoryToBoardRelationMap.remove(category.getId());
    }

    /**
     * Board 저장
     * @param board - board
     */
    public void saveBoard(Board board, Category category){
        if(boardMap.containsKey(board.getId())) throw new RuntimeException("이미 존재하는 book 입니다. 다른 book 입력해주세요");
        if(!categoryMap.containsKey(category.getId())) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");
        boardMap.put(board.getId(), board);
        CategoryRelation categoryRelation = categoryRelationMap.get(category.getId());
        categoryRelation.addChildId(board.getId());
    }

    /**
     * Board 반환
     * @param id - Board Id
     * @return Board
     */
    public Board getBoard(Integer id){
        if(!boardMap.containsKey(id)) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");
        Board board = boardMap.get(id);
        return new Board(id, board.getName());
    }

    /**
     * Board 삭제하고 연관된 CategoryToBoardRelation Board 제거 한다.
     * @param board - board
     * @param category - board 를 가지고 있는 category
     */
    public void deleteBoard(Board board, Category category){
        if(!boardMap.containsKey(board.getId())) throw new RuntimeException("존재하는 않는 book 입니다. 다른 book 입력해주세요");

        boardMap.remove(board.getId());

        CategoryToBoardRelation categoryToBoardRelation = categoryToBoardRelationMap.get(category.getId());
        categoryToBoardRelation.deleteChildId(board.getId());
    }

    /**
     * Category 의 자식들의 id 를 반환한다.
     * @param id - Category Id
     * @return 자식 Category Id 목록
     */
    public Set<Integer> getCategoryChildrenIds(Integer id){
        if(!categoryMap.containsKey(id)) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");

        CategoryRelation categoryRelation = categoryRelationMap.get(id);
        return categoryRelation.getChildrenIds();
    }

    /**
     * Category 의 게시판들의 id 를 반환한다.
     * @param id - Category Id
     * @return 게시판 Id 목록
     */
    public Set<Integer> getCategoryBoardIds(Integer id){
        if(!categoryMap.containsKey(id)) throw new RuntimeException("존재하는 않는 category 입니다. 다른 category 입력해주세요");

        CategoryToBoardRelation categoryToBoardRelation = categoryToBoardRelationMap.get(id);
        return categoryToBoardRelation.getChildrenIds();
    }

    /**
     * Category 부모, 자식 연관관계를 설정한다
     * @param parent - Category parent
     * @param child - Category child
     */
    public void addChildToParent(Category parent, Category child){
        if(categoryMap.containsKey(parent.getId()) && categoryMap.containsKey(child.getId())){
            CategoryRelation parentRelation = categoryRelationMap.get(parent.getId());
            parentRelation.addChildId(child.getId());
            return;
        }
        throw new RuntimeException("parent 또는 child 존재하지 않습니다.");
    }

    /**
     * Category 에 연관되는 board 설정한다.
     * @param category - Category parent
     * @param board - Category child
     */
    public void addBoardToCategory(Category category, Board board){
        if(categoryMap.containsKey(category.getId()) && boardMap.containsKey(board.getId())){
            CategoryToBoardRelation categoryRelation = categoryToBoardRelationMap.get(category.getId());
            categoryRelation.addChildId(board.getId());
            return;
        }
        throw new RuntimeException("category 또는 board 가 존재하지 않습니다.");
    }

    /**
     * Category id 검색하여 해당 Category 자신과 하위 요소들을 반환한다.
     * @param id - categoryId
     * @return category 계층 관계 json
     */
    public String searchCategoryToId(Integer id) throws JsonProcessingException {
        return searchCategory(categoryMap.get(id));
    }

    /**
     * Category id 검색하여 해당 Category 자신과 하위 요소들을 반환한다.
     * @param name - categoryName
     * @return category 계층 관계 json
     */
    public String searchCategoryToName(String name){
        Category category = findCategoryToName(name).orElseThrow(
                () -> new RuntimeException("해당 name 은 category 존재하지 않습니다."));
        return searchCategory(category);
    }

    /**
     * Category id 검색하여 해당 Category 자신과 하위 요소들을 반환한다.
     * @param name - categoryName
     * @return category 계층 관계 json
     */
    private String searchCategory(Category category){
        try{
            if(isLeafCategory(category)){
                Node leafNode = createLeafNode(category);
                return objectMapper.writeValueAsString(leafNode);
            }
            CategoryNode categoryNode = new CategoryNode(category);
            Node node = makeCategoryRelationData(category.getId(), categoryNode);
            return objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Category  검색하여 해당 Category 자신과 하위 요소들을 반환한다.
     * @param name - categoryName
     * @return category 계층 관계 json
     */
    private Optional<Category> findCategoryToName(String name){
        return this.categoryMap.values().stream()
                .filter((category -> category.getName().equals(name))).findFirst();
    }

    /**
     * Category id 검색하여 해당 Category 자신과 하위 요소들을 반환한다.
     * @param id - categoryId
     * @param node - node
     * @return category 계층 관계 node
     */
    private Node makeCategoryRelationData(Integer categoryId, Node node){
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
            makeCategoryRelationData(id, categoryNode);
        }
        return node;
    }

    /**
     * Category 가 Leaf 의 여부
     * @param category - Category
     * @return LeafCategory 판단 여부
     */
    private boolean isLeafCategory(Category category){
        return !categoryToBoardRelationMap.get(category.getId()).getChildrenIds().isEmpty();
    }

    /**
     * Category 가 leaf 의 경우 Board 추가한다.
     * @param category - Category
     * @return LeafCategory
     */
    private Node createLeafNode(Category category){
        Node categoryLeafNode = new CategoryLeafNode(category);
        CategoryToBoardRelation categoryToBoardRelation = categoryToBoardRelationMap.get(category.getId());
        for(Integer id : categoryToBoardRelation.getChildrenIds()){
            Board board = boardMap.get(id);
            categoryLeafNode.addBoard(board);
        }
        return categoryLeafNode;
    }
}
