/**
 * ## 문제 요약

1. N종류의 물약을 모두 구매. 물약은 1번부터 N번까지 넘버링
2. 특정 물약을 구매하면 다른 물약을 할인해줌
3. i번째 물약 가격은 동전 C_i 개.
4. i번째 물약을 구매하면 P_i 종류의 다른 물약 가격이 내려감
5. 할인은 중첩됨
6. 물약의 가격은 내려가더라도 0 이하로 내려가진 않음
7. 물약을 구매하는 순서에 따라 가격이 달라지는데 제일 싸게 살 경우의 비용을 구하라.

## 입력 및 제약조건

1. 첫째 줄 : 물략 종류 N
2 <= N <= 10

2. 둘째 줄 : 물약의 가격 C_i
1 <= i <= N
1 <= C_i <= 1000

3. N 개 : 물약 할인 정보.
할인 정보는 다음과 같이 여러개의 줄로 주어진다.
1) 1번 줄 : P_i
0 <= P_i <= N-1
2) p_i개 줄 : 물약번호 a_j, 할인가격 d_j
i번 물약을 구매하면 물약 a_j가 d_j만큼 할인된다는 의미
1 <= d_j <= 1000

## 출력

1. 첫째 줄 : 물건을 가장 싸게 살 때의 동전 개수

## 문제 풀이 - 순열

1. 순열로 어떤 물약을 먼저 사용할 지를 정한다.
2. 물약의 개수는 10개 이하이므로 다른 물약의 discount 배열에 얼마나 깎을지 더한다.
3. 동전의 개수를 취합한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N;
	static int[] potions;		// 포션
	static int[][][] discounts;		// 할인 [포션종류][할인되는 물약들][물약 번호, 할인가격]
	// 풀이용
	static boolean[] vis; // 선택 여부
	static int[] totalDiscount; // 할인율 총합
	static int min;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		potions = new int[N+1]; // 1-based index
		for(int i=1; i<=N; i++) {
			potions[i] = Integer.parseInt(st.nextToken());
		}
		discounts = new int[N+1][][]; // 1-based index. 
		for(int i=1; i<=N; i++) {
			int pi = Integer.parseInt(br.readLine());
			discounts[i] = new int[pi][2];
			for(int j = 0; j<pi; j++) {
				st = new StringTokenizer(br.readLine());
				discounts[i][j][0] = Integer.parseInt(st.nextToken());
				discounts[i][j][1] = Integer.parseInt(st.nextToken());
			}
		}
		// 풀이
		vis = new boolean[N+1];
		totalDiscount = new int[N+1];
		min = Integer.MAX_VALUE;
		permutation(0, 0);
		System.out.println(min);
		
		
		br.close();
	}
	
	static void permutation (int k, int ans) {
		if(k == N) {
			min = Math.min(ans, min);
			return;
		}
		
		for(int i=1; i<=N; i++) { // potions 순회
			if(vis[i]) continue;
			discounting(i);
			vis[i] = true;
			permutation(k+1, ans+(potions[i]-totalDiscount[i]>0 ? potions[i]-totalDiscount[i] : 1));
			vis[i] = false;
			undiscounting(i);
		}
	}
	static void discounting(int ind) {
		for(int i=0; i<discounts[ind].length; i++) {
			int num = discounts[ind][i][0];
			int price = discounts[ind][i][1];
			totalDiscount[num] += price;
		}
	}
	static void undiscounting(int ind) {
		for(int i=0; i<discounts[ind].length; i++) {
			int num = discounts[ind][i][0];
			int price = discounts[ind][i][1];
			totalDiscount[num] -= price;
		}
	}
}