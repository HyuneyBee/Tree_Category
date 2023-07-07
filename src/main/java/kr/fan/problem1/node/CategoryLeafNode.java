package kr.fan.problem1.node;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.fan.problem1.board.Board;
import kr.fan.problem1.category.Category;

import java.util.HashSet;
import java.util.Set;

/**
 * Category 와 Board 의 관계를 표현하기 위한 Class
 */
public class CategoryLeafNode implements Node{
    @JsonProperty("categoryId")
    private final Integer id;
    @JsonProperty("categoryName")
    private final String name;
    private final Set<Board> boards = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Board> getBoards() {
        return boards;
    }


    public CategoryLeafNode(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }
}
