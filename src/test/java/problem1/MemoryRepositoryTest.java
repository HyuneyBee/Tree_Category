package problem1;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.fan.problem1.board.Board;
import kr.fan.problem1.category.Category;
import kr.fan.problem1.MemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemoryRepositoryTest {
    private MemoryRepository memoryRepository;

    @BeforeEach
    void setup(){
        memoryRepository = new MemoryRepository();
    }

    @Test
    @DisplayName("종합 기능 테스트")
    void test() throws JsonProcessingException {
        Category man = new Category(1, "남자");
        Category exo = new Category(2, "엑소");
        Category bts = new Category(3, "방탕소년단");

        Board noticeBoard = new Board(1, "공지사항");
        Board chenBoard = new Board(2, "첸");
        Board anoticeBoard = new Board(3, "익명게시판");

        memoryRepository.saveCategory(man);
        memoryRepository.saveCategory(exo);
        memoryRepository.saveCategory(bts);

        memoryRepository.saveBoard(noticeBoard);
        memoryRepository.saveBoard(chenBoard);
        memoryRepository.saveBoard(anoticeBoard);
        System.out.println(1);

        memoryRepository.addChildToParent(man, exo);
        memoryRepository.addChildToParent(man, bts);

        memoryRepository.addBoardToCategory(exo, noticeBoard);
        memoryRepository.addBoardToCategory(exo, chenBoard);
        memoryRepository.addBoardToCategory(exo, anoticeBoard);

        memoryRepository.addBoardToCategory(bts, anoticeBoard);



        String s = memoryRepository.searchCategoryToId(1);
        System.out.println(1);
    }

}
