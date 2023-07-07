package kr.fan.proplem2;

public class Coin {
    private int sum;
    private int[] coins;
    private int result = 0;
    public int solution(int sum, int[] coins){
        this.sum = sum;
        this.coins = coins;

        dfs(0, 0);
        return result;
    }

    private void dfs(int coinSum, int index){
        if(coinSum == this.sum){
            this.result += 1;
            return;
        }
        if(coinSum > this.sum) return;


        for(int i=index; i<coins.length; i++) {
            dfs(coinSum + coins[i], i);
        }
    }
}
