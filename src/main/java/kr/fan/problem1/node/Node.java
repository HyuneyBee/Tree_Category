package kr.fan.problem1.node;

import kr.fan.problem1.board.Board;

/**
 * Category 계층관계 와 Board 관계 표현을 위한 interface
 */
public interface Node {

    /**
     * CategoryNode 또는 CategoryLeafNode 추가한다
     * @param node - categoryNode 또는 categoryLeafNode
     */
    default void addNode(Node node){
        throw new RuntimeException("Leaf Node 는 Node 를 추가할 수 없습니다.");
    }

    /**
     * 게시판을 CategoryLeafNode 에 추가한다.
     * @param board - 게시판
     */
    default void addBoard(Board board){
        throw new RuntimeException("일반 Node 에는 게시판을 추가할 수 없습니다.");
    }
}
