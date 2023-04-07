/**
 * ## 문제 요약

1. 인접한 모든 자리의 차이가 1인 수를 계단수 라고 한다.
2. N이 주어질 때 길이가 N이면서 0~9까지 모든 수가 등장하는 계단 수는 총 몇개인가?

## 입력 및 제약조건

1. 첫째 줄 : N
1 <= N <= 100

## 출력

1. 첫째 줄 : 정답을 1,000,000,000으로 나눈 나머지 출력

시간제한: 2초
메모리제한: 128MB

## 문제 풀이 - DP
  
1. 계단 수를 만드는 개념은 쉬운 계단 수와 동일
2. 다만 해당 계단 수의 개수에서 0~9가 모두 존재하는 경우를 세야 함
3. 즉, DP 테이블을 3차원으로 구성하고, 각 격자에는 1023개의 공간을 배치한다.
4. 점화식은 다음과 같다.
    1. dp[x][y][i | 1<<y] += dp[x-1][y-1][i]
    2. dp[x][y][i | 1<<y] += dp[x-1][y+1][i]
5. 즉 dp[x][y][i] 에서 dp[x+1][y-1][i | 1<< y-1]과 dp[x+1][y+1][ i | 1<<y+1]에 dp[x][y][i]만큼 더해준다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	// 입력용
	static int N;
	// 풀이용
	static int[][][] dp;
	static final int MAX = 1000000000;
	static int[] res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		N = Integer.parseInt(br.readLine());
		// 풀이
		dp = new int[101][10][1024];
		for(int i=0; i<10; i++) {
			dp[1][i][1<<i]++;
		}
		// dp채우기
		res = new int[101];
		
		for(int x=1; x<=100; x++) {
			int cnt = 0;
			for(int y=0; y<10; y++) {
				if(x < 100) {
					for(int i=0; i<1024; i++) {
						if(y!=0) {
							dp[x+1][y-1][i | 1<<(y-1)] = (dp[x+1][y-1][i | 1<<(y-1)] + dp[x][y][i])%MAX;
						}
						if(y!=9){
							dp[x+1][y+1][i | 1<<(y+1)] = (dp[x+1][y+1][i | 1<<(y+1)] + dp[x][y][i])%MAX;
						}
					}
				}
				if(y != 0) {
					cnt = (cnt + dp[x][y][1023])%MAX;
				}
			}
			res[x] = cnt;
		}
		// dp에서 값 가져오기
		System.out.println(res[N]%MAX);
		
		br.close();
	}
}