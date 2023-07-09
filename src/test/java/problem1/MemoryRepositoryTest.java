package problem1;

import kr.fan.problem1.board.Board;
import kr.fan.problem1.category.Category;
import kr.fan.problem1.MemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryRepositoryTest {
    private MemoryRepository memoryRepository;

    private static final Integer MAN_CATEGORY_ID = 1;
    private static final Integer BTS_CATEGORY_ID = 3;
    private static final Integer BLACK_PINK_CATEGORY_ID = 5;
    private static final String BLACK_PINK_CATEGORY_NAME = "블랙핑크";
    private static final Integer ANONYMOUS_NOTICE_ID = 6;

    @BeforeEach
    void setup(){
        memoryRepository = new MemoryRepository();
    }

    private void setupData(){
        Category man = new Category(1, "남자");
        Category exo = new Category(2, "엑소");
        Category bts = new Category(3, "방탕소년단");

        memoryRepository.saveCategory(man);
        memoryRepository.saveCategory(exo);
        memoryRepository.saveCategory(bts);

        memoryRepository.addChildToParent(man, exo);
        memoryRepository.addChildToParent(man, bts);

        Category woman = new Category(4, "여자");
        Category blackPink = new Category(5, "블랙핑크");

        memoryRepository.saveCategory(woman);
        memoryRepository.saveCategory(blackPink);

        memoryRepository.addChildToParent(woman, blackPink);

        Board exoNotice = new Board(1, "공지사항");
        Board chen = new Board(2, "첸");
        Board baekHyun = new Board(3, "백현");
        Board xiumin = new Board(4, "시우민");

        memoryRepository.saveBoard(exoNotice);
        memoryRepository.saveBoard(chen);
        memoryRepository.saveBoard(baekHyun);
        memoryRepository.saveBoard(xiumin);

        memoryRepository.addBoardToCategory(exo, exoNotice);
        memoryRepository.addBoardToCategory(exo, chen);
        memoryRepository.addBoardToCategory(exo, baekHyun);
        memoryRepository.addBoardToCategory(exo, xiumin);

        Board btsNotice = new Board(5, "공지사항");
        Board anonymousNotice = new Board(6, "익명게시판");
        Board v = new Board(7, "뷔");

        memoryRepository.saveBoard(btsNotice);
        memoryRepository.saveBoard(anonymousNotice);
        memoryRepository.saveBoard(v);

        memoryRepository.addBoardToCategory(bts, btsNotice);
        memoryRepository.addBoardToCategory(bts, anonymousNotice);
        memoryRepository.addBoardToCategory(bts, v);

        Board blackPinkNotice = new Board(8, "공지사항");
        Board rose = new Board(9, "로제");

        memoryRepository.saveBoard(blackPinkNotice);
        memoryRepository.saveBoard(rose);

        memoryRepository.addBoardToCategory(blackPink, blackPinkNotice);
        memoryRepository.addBoardToCategory(blackPink, anonymousNotice);
        memoryRepository.addBoardToCategory(blackPink, rose);
    }

    @Test
    @DisplayName("카테고리ID 검색하여 JSON 변환")
    void 카테고리ID_검색하여_카테고리_계층을_JSON_표현한다() throws IOException {
        setupData();
        String manCategory = memoryRepository.searchCategoryToId(MAN_CATEGORY_ID);
        String expectedJson = new String(Files.readAllBytes(Paths.get("src/test/resources/ManCategoryRelation.json")));

        assertEquals(expectedJson, manCategory);
    }

    @Test
    @DisplayName("카테고리 이름 검색하여 JSON 변환")
    void 카테고리_이름_검색하여_카테고리_계층을_JSON_표현한다() throws IOException {
        setupData();
        String manCategory = memoryRepository.searchCategoryToName(BLACK_PINK_CATEGORY_NAME);
        String expectedJson = new String(Files.readAllBytes(Paths.get("src/test/resources/BlackCategoryRelation.json")));

        assertEquals(expectedJson, manCategory);
    }

    @Test
    @DisplayName("동일한 게시판은 여러 카테고리에 포함")
    void 동일한_게시판은_여러_카테고리에_포함_가능하다(){
        setupData();

        Set<Integer> btsBoardIds = memoryRepository.getCategoryBoardIds(BTS_CATEGORY_ID);
        Set<Integer> blackPinkBoardIds = memoryRepository.getCategoryBoardIds(BLACK_PINK_CATEGORY_ID);

        assertTrue(btsBoardIds.contains(ANONYMOUS_NOTICE_ID)
                && blackPinkBoardIds.contains(ANONYMOUS_NOTICE_ID));
    }

    @Test
    @DisplayName("Category 저장")
    void 카테고리를_저장한다(){
        Category man = new Category(1, "남자");
        memoryRepository.saveCategory(man);

        Category category = memoryRepository.getCategory(man.getId());

        assertEquals(1, category.getId());
        assertEquals("남자", category.getName());
    }

    @Test
    @DisplayName("Category 삭제")
    void 카테고리를_삭제한다(){
        Category man = new Category(1, "남자");
        memoryRepository.saveCategory(man);

        memoryRepository.deleteCategory(man);

        assertThrows(RuntimeException.class, () -> memoryRepository.getCategory(man.getId()));
    }

    @Test
    @DisplayName("Board 저장")
    void 게시판을_저장한다(){
        Category man = new Category(1, "남자");
        memoryRepository.saveCategory(man);

        Board notice = new Board(1, "공지사항");
        memoryRepository.saveBoard(notice);

        Board category = memoryRepository.getBoard(notice.getId());

        assertEquals(1, category.getId());
        assertEquals("공지사항", category.getName());
    }

    @Test
    @DisplayName("Board 삭제")
    void 게시판을_삭제한다(){
        Category man = new Category(1, "남자");
        memoryRepository.saveCategory(man);

        Board notice = new Board(1, "공지사항");
        memoryRepository.saveBoard(notice);
        memoryRepository.addBoardToCategory(man, notice);

        memoryRepository.deleteBoard(notice, man);

        assertThrows(RuntimeException.class, () -> memoryRepository.getBoard(notice.getId()));
        assertEquals(0, memoryRepository.getCategoryBoardIds(man.getId()).size());
    }
}
