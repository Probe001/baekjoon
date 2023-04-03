/**
 * ## 문제 요약

1. 상근이네 집에서 맥주 한박스 들고 출발. 맥주 한박스에는 20개.
2. 50미터를 가려면 맥주를 한 병 마셔야 함( 마시고 출발해야 한다. )
3. 편의점에서는 빈 맥주를 다시 리필할 수 있음.(박스의 맥주는 20개를 넘을 수 없다)
4. 상근이가 락 페스티벌에 도착할 수 있는지를 구하라

## 입력 및 제약조건

1. 첫번째 줄 : 테스트 케이스 개수 t
t <= 50

2. tc 1번 줄 : 맥주를 파는 편의점의 개수 n
0 <= n <= 100

3. n+2 개 줄 : 상근이 집, 편의점(n개), 락 페스티벌의 좌표
두 정수 x, y로 구성되며 -32768 <= x, y <= 32767

4. 두 좌표 사이의 거리는 맨해튼 거리.

시간제한 : 1초
메모리제한: 128MB

## 출력

1. 첫번째 줄 : 도착 가능하면 happy, 불가능하면 sad 출력

## 문제 풀이

1. 가중치가 1000인 그래프 문제
2. 어차피 각 정점에서는 무조건 1000을 갈 수 있다.
3. 최단경로를 가는 문제가 아니므로 그냥 갈수만 있으면 다시 안가도 됨
4. BFS.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int n;
	static Point[] board;
	
	// 풀이용
	static boolean[] vis;
	static boolean isReached;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=t; tc++) {
			n = Integer.parseInt(br.readLine());
			board = new Point[n+2];
			vis = new boolean[n+2];

			for(int i = 0; i<n+2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				board[i] = new Point(x, y);
			}
			isReached= false;
			if(board[0].equals(board[n+1])) isReached = true;
			if(!isReached) bfs();
			
			sb.append(isReached ? "happy\n" : "sad\n");
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void bfs() {
		Queue<Point> que = new ArrayDeque<>();
		vis[0] = true;
		que.offer(board[0]);
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=1; i<n+2; i++) {
				if(vis[i]) continue;
				if(now.isCanReach(board[i])) {
					if(i == n+1) {
						isReached = true;
						return;
					}
					vis[i] = true;
					que.offer(board[i]);
				}				
			}
		}
	}
	
	
	static class Point{
		int x, y;
		
		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Point) {
				Point pnt = (Point) obj;
				if(this.x == pnt.x && this.y == pnt.y) {
					return true;
				}
			}
			return false;
		}
		
		public boolean isCanReach(Point pnt) {
			int distX = Math.abs(pnt.x-this.x);
			int distY = Math.abs(pnt.y-this.y);
			if(distX+distY<= 1000) return true;
			else return false;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

}