/**
 * ## 문제 요약

1. 1번부터 N번까지 번호가 매겨진 도시
2. 도시들 사이에는 길이 있거나 없을 수 있다.
3. 한 외판원이 한 도시에서 출발해 N개의 도시를 모두 거쳐 원래 도시로 돌아오는 계획을 세움
4. 한번 갔던 도시로는 다시 갈 수 없다.
5. 가장 적은 비용을 들이는 여행 계획을 세우고자 한다.
6. 도시간 이동 비용은 행렬로 주어짐
7. 비용은 대칭적이지 않음
8. 모든 도시간의 비용은 양의 정수
9. 갈 수 없다면 비용이 0으로 주어짐.

## 입력 및 제약조건

1. 첫째 줄 : 도시의 수 N
2 <= N <= 10

2. N 개 줄 : 비용 행렬
0 <= 각 행렬 성분 <= 1000000

3. 항상 순회 가능한 경우만 입력으로 주어짐

4. 메모리 제한 : 256MB
5. 시간 제한 : 2초

## 출력

1. 첫째 줄 : 외판원 순회에 필요한 최소 비용

## 문제 풀이 1 - 순열

1. 외판원 순회 1의 경우 n이 16이므로 16!이라 안되지만 이 문제는 n=10이므로 가능
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[][] board;
	static boolean[] vis;
	static long res;
	static int[] src;
	
	static void dfs(int k, long tmp, int now, int start) {
		// 인덱스, 경로비용, 현재노드, 시작노드
		if(k==N) {
			if(board[now][start] == 0) return;
			res = Math.min(res, tmp + board[now][start]);
			return;
		}
		
		for(int i=0; i<N; i++) { // 다음 목적지 설정
			if(vis[i]) continue; // 방문한 곳은 지나쳐
			if(board[now][i] == 0) continue; // 현재 노드에서 다음노드로 가는 길 없을 때
			long t = tmp + board[now][i]; // 현재 노드에서 다음노드 가는 비용 추가
			if(t > res) continue; // 이미 최소비용보다 많으면 안가도 됨
			vis[i] = true; // 방문처리
			dfs(k+1, t, i, start); // 다음 노드로 넘어감
			vis[i] = false;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		for(int x=0; x<N; x++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		vis = new boolean[N];
		res = Long.MAX_VALUE;
		for(int st = 0; st<N; st++) {
			vis[st] = true;
			dfs(1, 0, st, st);
			// 시작점은 정해졌음.
			vis[st] = false;
		}
		System.out.println(res);
		
		br.close();
	}

}