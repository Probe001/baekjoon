/**
 * ## 문제 요약

1. N*N 크기 시험관. 특정한 위치에 바이러스 존재
2. 바이러스는 1번부터 K번까지의 바이러스 종류 중 하나
3. 모든 바이러스는 1초마다 상 하 좌 우로 증식해 나감
4. 매 초마다 번호가 낮은 종류의 바이러스부터 먼저 증식
5. 이미 바이러스가 존재하는 칸에는 증식 불가
6. S 초가 지났을 때 (X, Y)에 존재하는 바이러스의 종류를 출력하시오
7. S 초 지났을 때 해당 위치에 바이러스가 존재하지 않으면 0을 출력
8. 시험관 가장 왼쪽 위는 1, 1이다.

## 입력 및 제약 조건

1. 첫째 줄 : N, K
1 <= N <= 200, 1 <= K <= 1000

2. N 개 줄 : 시험관의 정보
바이러스 번호, 없으면 0
바이러스 번호 <= K

3. N+2번줄 : S, X, Y
0 <= S <= 10000, 1 <= X, Y <= N

## 출력

1. 첫째 줄 : S초 뒤에 X, Y에 존재하는 바이러스 종류 출력

## 문제 풀이

1. 바이러스 종류에 따라 큐 배열을 생성한다.
2. 모든 시험관을 전부 돌면서 큐 배열에 집어넣는다.
3. 한번 모든 배열을 4방탐색하면 1초가 지난 것
4. S초가 지나거나 X,Y에 값이 들어오면 출력한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, K;
	static int[][] board;
	static int S, X, Y;
	// 풀이용
	static Queue<Point>[] virus;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	
	static class Point{
		int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		
		virus = new ArrayDeque[K+1];
		for(int i=1; i<=K; i++) {
			virus[i] = new ArrayDeque<Point>();
		}
		
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
				if(board[x][y] != 0) {					
					virus[board[x][y]].add(new Point(x, y));
				}
			}
		}
		st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		X--; // 0-based index
		Y--; // 0-based index
		// 풀이
		A: for(int time=0; time < S; time++) { // S초만큼 진행
			// 모든 바이러스를 1부터 전염시킨다.
			for(int v=1; v<=K; v++) {
				bfs(v);
				if(board[X][Y] != 0) break A;
			}
		}
		System.out.println(board[X][Y]);
		
		br.close();
	}
	
	static void bfs(int v) {
		// virus[v] : v번 바이러스들이 위치한 현재 좌표 큐
		int size = virus[v].size();
		for(int s=0; s<size; s++) {
			Point now = virus[v].poll();
			for(int i=0; i<4; i++) {
				int nx = now.x+dxy[i][0], ny = now.y+dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || N <= ny) continue;
				if(board[nx][ny] != 0) continue;
				board[nx][ny] = v;
				virus[v].offer(new Point(nx, ny));
			}
		}
	}

}