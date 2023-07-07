package kr.fan.problem1.board;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Board Entity Class
*/
public class Board {
    @JsonProperty("boardId")
    private final Integer id;
    @JsonProperty("boardName")
    private final String name;

    public Board(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
