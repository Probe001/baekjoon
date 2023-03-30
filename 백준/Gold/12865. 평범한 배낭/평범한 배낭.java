import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * ## 문제 요약

1. N개의 물건. 각 물건은 무게 w와 가치 v
2. 최대 K만큼 무게만 배낭에 넣을 수 있음
3. 배낭에 넣을 수 있는 가치의 최댓값은?

## 입력 및 제약조건

1. 첫째 줄 : N, K
1 <= N <= 100, 1 <= K <= 100000

2. N 개 줄 : W, V
1 <= W <= 100000, 0 <= V <= 1000

## 출력

1. 배낭에 넣을 수 있는 가치합의 최댓값

## 문제 풀이

1. DP 이용
2. DP[k][w] : 배낭 무게 w일 때 k번째 아이템까지의 최대 가치
3. DP[0] : 0으로 초기화
4. k : 1 based index
5. dp[k][w] = max( dp[k-1][w-v[k]]+v[k],dp[k-1][w] );
 */

public class Main {
	static int N, K;
	static int[][] dp;
	static int[][] items;
		
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		dp = new int[N+1][K+1];
		items = new int[N+1][];
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			int w = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			items[i] = new int[] {w, v};
		}
		// 동적 테이블 형성
		for(int x=1; x<=N; x++) {
			for(int y=1; y<=K; y++) {
				int w = items[x][0];
				int v = items[x][1];
				int val1 = 0, val2 = 0;
				if(y-w >= 0) {
					val1 = dp[x-1][y-w]+v; // 현재 물건을 넣을때
				}
				val2 = dp[x-1][y];
				dp[x][y] = Math.max(val1, val2);
			}
//			System.out.println(Arrays.toString(dp[x]));
		}
		System.out.println(dp[N][K]);
		
		br.close();
	}

}