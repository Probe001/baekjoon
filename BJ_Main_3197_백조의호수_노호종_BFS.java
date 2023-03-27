package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_3197_백조의호수_노호종_BFS {
	// 입력용
	static int R, C;
	static char[][] board;
	// 풀이용
	static boolean[][] vis;
	static Queue<Point> waterQue, swanQue;
	static Point swan1, swan2;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	static boolean isMeet;

	public static void main(String[] args) throws IOException, InterruptedException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		board = new char[R][];
		
		// 풀이용
		swanQue = new ArrayDeque<>();
		waterQue = new ArrayDeque<>();
		vis = new boolean[R][C];
		
		
		// 데이터 입력
		for(int x=0; x<R; x++) {
			board[x] = br.readLine().toCharArray();
			for(int y=0; y<C; y++) {
				if(board[x][y] =='L') {
					if(swan1 == null) {
						swan1 = new Point(x, y);
						vis[swan1.x][swan1.y] = true;
						swanQue.offer(swan1);
					}
					else swan2 = new Point(x, y);
				}
				if(board[x][y] != 'X') {
					waterQue.offer(new Point(x, y));
				}
			}
		}
		
		int time = 0;
		isMeet = false;
		while(!isMeet) {
			bfs(); // 백조 움직임
//			print();
			if(isMeet) break;
			meltIce();
//			print();
			time++;
		}
		System.out.println(time);
	}
	static boolean bfs() throws InterruptedException {
		Queue<Point> nextQue = new ArrayDeque<>();
		while(!swanQue.isEmpty()) {
			Point now = swanQue.poll();
			
			// 백조를 만난 경우 종료
			if(now.x == swan2.x && now.y == swan2.y) {
				isMeet = true;
				swanQue.clear();
				break;
			}
			
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(isOutRange(nx, ny)) continue;
				if(vis[nx][ny]) continue;
				vis[nx][ny] = true;
				if(board[nx][ny] == 'X') {
					nextQue.offer(new Point(nx, ny));
					continue;
				}
				swanQue.offer(new Point(nx, ny));
			}
		}
		swanQue = nextQue;
		return false;
	}
	
	static void meltIce() {
		int size = waterQue.size();
		for(int s=0; s<size; s++) {
			Point now = waterQue.poll();
			
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(isOutRange(nx, ny)) continue;
				if(board[nx][ny] == 'X') {
					board[nx][ny] = '.';
					waterQue.offer(new Point(nx, ny));
				}
			}
		}
	}

	
	static boolean isOutRange(int x, int y) {
		if ( x < 0 || R <= x || y < 0 || C <= y) return true;
		return false;
	}
	
	static class Point{
		int x, y;
		int area;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		public Point(int x, int y, int area) {
			super();
			this.x = x;
			this.y = y;
			this.area = area;
		}
		
		
	}

	static void print() throws InterruptedException {
		for(int x=0; x<R; x++) {
			System.out.println(Arrays.toString(board[x])+" "+Arrays.toString(vis[x]));
		}
		System.out.println();
//		Thread.sleep(500);
	}
}
