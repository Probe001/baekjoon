/**
 * ## 문제 요약

1. 직사각형 모양의 지도. 각 격자에는 높이
2. 세준이는 좌상단에서 출발. 우하단까지 도착
3. 항상 높이가 더 낮은 지점으로만 이동해 목표 지점까지 이동
4. 항상 내리막길로 이동하는 경로의 개수는?

## 입력 및 제약조건

1. 첫째 줄 : 세로크기 M, 가로크기 N
1 <= M, N <= 500
2. M 개 줄 : 지도의 상태
각 지점의 높이는 10000이하 자연수

시간제한: 2초
메모리제한: 128MB

## 출력

1. 첫째 줄 : 이동 가능한 경로의 수 H
H는 10억 이하의 음이 아닌 정수

## 문제 풀이 - DP

1. BFS로 항상 낮은 길로만 가게 한다.
2. 그런데 결과가 10억 이하의 음이 아닌 정수 → 최대 10초 걸린다는 소리
3. DP[x][y] : 해당 지점에 오는 최대 경로 수.
4. DP[x][y]에서 4방 탐색 후, 자신에게 올 수 있는 경우를 모두 더한 값.
5. 그러기 위해서 PriorityQueue에 넣고, 값이 큰 수부터 4방 탐색을 진행한다.

4 5
50 45 37 32 30
35 50 40 20 25
30 30 25 17 28
27 24 22 15 10

50 50 45 40 37 35 32 30 30 30 28 27 25 25 24 22 20 17 15 10

1	1	1	1	1
1	0	0	2	1
1	0	0	2	0
1	1	1	3	3
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, M;
	static int[][] board;
	// 풀이용
	static PriorityQueue<Point> pq;
	static int[][] dp;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		pq = new PriorityQueue<>((p1, p2) -> board[p2.x][p2.y] - board[p1.x][p1.y]);
		for(int x=0; x<N;x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
				if(x == 0 && y == 0) continue;
				pq.offer(new Point(x, y));
			}
		}
		// 풀이
		dp = new int[N][M];
		dp[0][0] = 1;
		while(!pq.isEmpty()) {
			Point now = pq.poll();
			int tmp = 0;
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(board[nx][ny] <= board[now.x][now.y]) continue;
				tmp += dp[nx][ny];
			}
			dp[now.x][now.y] = tmp;
		}
		System.out.println(dp[N-1][M-1]);
		
		br.close();
	}
	
	static class Point{
		int x,y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
		
	}
	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(dp[x]));
		}
		System.out.println();
	}

}