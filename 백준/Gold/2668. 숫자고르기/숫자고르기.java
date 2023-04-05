/**
 * ## 문제 요약

1. 세로 두줄, 가로 N개 표
2. 첫째 줄에는 1부터 N까지 차례대로
3. 둘째 줄의 각 칸에는 1 이상 N 이하 정수
4. 첫째 줄에서 숫자를 뽑아 생기는 집합과, 둘째 줄의 정수의 집합이 일치해야 한다.
5. 이러한 조건을 만족시키는 정수들을 뽑되 최대한 많이 뽑는 방법은?
6. 예를 들어, N = 7일 때 다음처럼 주어진다면
	1 2 3 4 5 6 7
	3 1 1 5 5 4 6
1, 3, 5를 뽑는게 가장 많이 뽑을 수 있다.

## 입력 및 제약조건

1. 첫째 줄 : N
1 <= N <= 100

2. N 개 줄 : 표의 둘째 줄에 들어가는 정수의 수열
1 <= 수열의 수 <= N

## 출력

1. 첫째 줄 : 뽑힌 정수들의 개수
2. 뽑힌 정수들을 한 줄에 하나씩 작은 순서대로 출력

## 문제 풀이

1. 부분집합? => 2^N인데 N == 100이므로 시간초과

2. 존재여부 int 배열 parents 생성

3. 두번째 줄의 값 board[i]에 따라 parents[i]++
ind	1 2 3 4 5 6 7
board	3 1 1 5 5 4 6
parents	2 0 1 1 2 1 0

4. parents를 순회하면서 값이 parents[i]의 값이 0인 경우
parents[board[i]]--
ind	1 2 3 4 5 6 7
board	3 1 1 5 5 4 6
parents	1 0 1 1 2 0 0

5. 만약 parents[board[i]]-- 가 0이 된 경우 해당 값도 parents를 줄여준다.
ind	1 2 3 4 5 6 7
board	3 1 1 5 5 4 6
parents	1 0 1 0 1 0 0

5. 1번부터 순회하면서 parents[i]가 양수인 것들만 뽑는다.


 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Main {
	// 입력용
	static int N;
	static int[] board;
	// 풀이용
	static boolean[] vis;
	static TreeSet<Integer> lists;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		N = Integer.parseInt(br.readLine());
		board = new int[N+1];
		for(int i=1; i<=N; i++) {
			board[i] = Integer.parseInt(br.readLine());
		}
		// 풀이
		vis = new boolean[N+1];
		lists = new TreeSet<>();
		for(int i=1; i<=N; i++) {
			dfs(i);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(lists.size()).append('\n');
		while(!lists.isEmpty()) {
			sb.append(lists.pollFirst()).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void dfs(int k) {
		if(vis[k]) {
			lists.add(k);
		}else {
			vis[k] = true;
			dfs(board[k]);
			vis[k] = false;
		}
	}

}