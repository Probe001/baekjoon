package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_22352_항체인식_노호종 {
	static int N, M;
	static int[][] beforeBoard, afterBoard;
	static int[][] area1, area2;
	static int[][] dxy= {{0,1},{1,0},{0,-1},{-1,0}};
	static class Point{
		int x, y;

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
	static ArrayList<Integer> space;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		beforeBoard = new int[N][M];
		afterBoard = new int[N][M];
		area1 = new int[N][M];
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				beforeBoard[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				afterBoard[x][y] = Integer.parseInt(st.nextToken());
			}
		}
//		입력 완료
		boolean flag = true;
		int cnt1 = 1;
		space = new ArrayList<>();
		space.add(-1);
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(area1[x][y] == 0) bfs(x, y, beforeBoard, area1, cnt1++);
			}
		}
		// 확인
		int diffArea = 0;
		
		boolean[][] vis = new boolean[N][M];
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(beforeBoard[x][y] == afterBoard[x][y]) continue; // 번호가 다르면
				if(vis[x][y]) continue;
				diffArea += 1;
				int areaSize = bfs2(x, y, vis);
				if(space.get(area1[x][y]) != areaSize) flag = false;
			}
		}
		
		if( diffArea > 1) flag = false;
		System.out.println(flag ? "YES" : "NO");
		br.close();
	}
	static void bfs(int x, int y, int[][] board, int[][] area, int areaNum) {
		int originNum = board[x][y];
		Queue<Point> que = new ArrayDeque<>();
		area[x][y] = areaNum;
		que.offer(new Point(x,y));
		int cnt=1;
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(area[nx][ny] != 0) continue;
				if(board[nx][ny] != originNum) continue;
				area[nx][ny] = areaNum;
				que.offer(new Point(nx, ny));
				cnt++;
			}
		}
		space.add(cnt);
		
	}
	static int bfs2(int x, int y, boolean[][] vis) {
		int originNum = afterBoard[x][y];
		Queue<Point> que = new ArrayDeque<>();
		vis[x][y] = true;
		que.offer(new Point(x, y));
		int cnt = 1;
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(vis[nx][ny]) continue;
				if(beforeBoard[nx][ny] == afterBoard[nx][ny]) continue;
				if(afterBoard[nx][ny] != originNum) continue;
				// 바뀐 부분은 같지만 원래 보드와는 다른 부분
				cnt++;
				vis[nx][ny] = true;
				que.offer(new Point(nx, ny));
			}
		}
		return cnt;
	}
	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(area1[x])+" "+Arrays.toString(area2[x]) );
		}
	}
}
