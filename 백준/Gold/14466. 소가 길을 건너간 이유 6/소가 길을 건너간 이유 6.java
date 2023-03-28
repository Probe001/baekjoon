/**
 * ## 문제 요약

1. N * N 목초지
2 <= N <= 100
2. K 마리 소
1 <= K <= 100 , K <= N^2
3. 인접한 목초지 사이는 자유롭게 이동할 수 있다.
4. 일부 인접한 목초지는 길을 건너야 이동이 가능하다.
5. 이렇게 길을 건너가야만 만날 수 있는 소의 쌍은 몇쌍일까?
6. 즉, 다른 소에게 갈 때 무조건 길을 건너야만 만날 수 있는 소들의 쌍을 구해야 한다.

## 입력 및 제약조건

1. 첫째 줄 : N, K, R
2 <= N <= 100
2. R 개 줄에 길 : r, c, r', c'
1 <= 각 수 <= N
3. K 개 줄 : 소의 위치 X, Y

## 출력

1. 첫째 줄 : 만날 수 없는 소의 쌍

## 문제 풀이

1. 소가 다른 소에게 갈 때 무조건 길을 건너야만 하는 소의 쌍을 구하라.
2. 간선으로 주어진다. → 간선으로 풀까?
3. 각 칸을 class로 구현

```java
class Point {
	int x, y; // 좌표
  boolean u, d, l, r; // 상 하 좌 우 길 여부

	addLoad(int x, int y){  // 상 하 좌 우
		int dx = x - this.x;  // -1  1  0  0
		int dy = y - this.y;  //  0  0 -1  1
		// 상하좌우 여부에 따라 길 여부 체크
	}
}
```

1. 각 칸부터 BFS를 돌려 구역을 구분한다. (만약 길이 존재한다면 그쪽으로는 가지 않게)
2. 그러면 구분된 구역이 존재.
3. 구역 번호에 해당하는 소의 숫자를 판단한다.
4. 조합을 이용해 총 쌍의 수를 구한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, K, R;
	static Point[][] board;
	
	static class Point{
		int x, y;				// 좌표
		boolean[] d; 	// 상하좌우 길 여부
		int areaNum;			// 영역 번호
		boolean cow;			// 소 유무
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
			d = new boolean[4];
			areaNum = -1; // 0-based index용
			cow = false;
		}
		
		void addLoad(int tx, int ty) {	// 상, 하, 좌, 우
			int dx = tx - this.x;		// -1, 1, 0, 0
			int dy = ty - this.y;		//  0, 0,-1, 1
			if(dx == -1) { // 상
				d[0] = true;
			} else if (dx == 1) { // 하
				d[1] = true;
			} else if (dy == -1) { // 좌
				d[2] = true;
			} else if (dy == 1) { // 우
				d[3] = true;
			}
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + ", d=" + Arrays.toString(d) + ", areaNum=" + areaNum + ", cow=" + cow
					+ "]";
		}
	}
	
	// 목초지 리스트. 원소: (영역번호, 소의 수)
	static List<Integer> group;
	static int res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		board = new Point[N][N];
		for(int x=0; x<N; x++) {
			for(int y=0; y<N; y++) {
				board[x][y] = new Point(x, y);
			}
		}
		// 입력
		for(int i=0; i<R; i++) {  // 길
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int rd = Integer.parseInt(st.nextToken());
			int cd = Integer.parseInt(st.nextToken());
			board[r-1][c-1].addLoad(rd-1, cd-1);
			board[rd-1][cd-1].addLoad(r-1, c-1);
		}
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			board[x-1][y-1].cow = true;
		}
		// 문제 풀이
		// 영역 나누기
		group = new ArrayList<>();
		int arNum = 0;
		for(int x=0; x<N; x++) {
			for(int y=0; y<N; y++) {
				if(board[x][y].areaNum == -1) {
					group.add(0);
					bfs(x, y, arNum++);
				}
			}
		}
//		System.out.println(group);
		// 조합으로 답 구하기
		sel = new int[2];
		combination(0, 0);
		System.out.println(res);
		
		br.close();
	}
	static int[] sel;
	static void combination(int k, int ind) {
		if(k == 2) {
//			System.out.println(Arrays.toString(sel));
			int tmp = sel[0] * sel[1];
			res += tmp;
			return;
		}
		for(int i=ind; i<group.size(); i++) {
			sel[k] = group.get(i);
			combination(k+1, i+1);
		}
	}
	
	static int[][] dxy = {{-1, 0},{1,0},{0,-1},{0,1}};
	static void bfs(int x, int y, int arNum) {
		Queue<Point> que = new ArrayDeque<>();
		board[x][y].areaNum = arNum;
		if(board[x][y].cow) group.set(arNum, group.get(arNum)+1);
		que.offer(new Point(x, y));
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				if(board[now.x][now.y].d[i]) continue; // 길이 있는경우 통과
				int nx = now.x+dxy[i][0], ny = now.y+dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || N <= ny) continue;
				if(board[nx][ny].areaNum != -1) continue;
				board[nx][ny].areaNum = arNum;
				if(board[nx][ny].cow) group.set(arNum, group.get(arNum)+1);
				que.offer(new Point(nx, ny));
			}
		}
	}
	static void print() {
		for(int x=0; x<N; x++) {
			for(int y=0; y<N; y++) {
				System.out.print(board[x][y].areaNum+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

}