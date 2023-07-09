package kr.fan.problem1.board;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Board Entity Class
*/
public class Board implements Comparable<Board>{
    private final Integer id;
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

    @Override
    public int compareTo(Board o) {
        return Integer.compare(this.id, o.getId());
    }
}
