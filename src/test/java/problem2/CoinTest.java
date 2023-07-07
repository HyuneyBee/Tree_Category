package problem2;

import kr.fan.proplem2.Coin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {
    private Coin coin;

    @BeforeEach
    void setup(){
        coin = new Coin();
    }

    @Test
    @DisplayName("sum = 4, coins[] = {1,2,3} 출력 4")
    void coinTest1(){
        assertEquals(4, coin.solution(4, new int[]{1,2,3}));
    }

    @Test
    @DisplayName("sum = 10, coins[] = {2,5,3,6} 출력 5")
    void coinTest2(){
        assertEquals(5, coin.solution(10, new int[]{2,5,3,6}));
    }
}
