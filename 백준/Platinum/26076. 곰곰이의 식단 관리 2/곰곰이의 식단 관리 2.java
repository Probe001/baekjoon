import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, M;
	static int[][] board;
	// 풀이용
	static class Wall {
		boolean u, d, l, r;

		@Override
		public String toString() {
			return "Wall [u=" + u + ", d=" + d + ", l=" + l + ", r=" + r + "]";
		}
	}
	static List<Wall> wall;
	static int[][] vis;
	static int[][] dxy = {{1,1},{1,0},{1,-1},{0,1},{0,-1},{-1,1},{-1,0},{-1,-1}};
	
	static boolean finalDevided;
	static int res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		
		for(int x= 0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		// 풀이
		wall = new ArrayList<>();
		wall.add(null);
		vis = new int[N][M];
		int wallNum = 1;
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(board[x][y] != 0 && vis[x][y] == 0) {		
					wall.add(new Wall());
					makeWall(x, y, wallNum++);
				}
			}
		}
//		print();
//		System.out.println("초기: "+wall);
		finalDevided = false;
		res = 0;
		for(int i=1;i<wall.size(); i++) {
			if(isDevided(wall.get(i))) finalDevided = true;
		}
		if(!finalDevided) {
			res++;
			if(blocking()) finalDevided = true;
		}
		if(!finalDevided) res++;
		System.out.println(res);
		
		br.close();
	}
	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(vis[x]));
		}
		System.out.println();
	}
	
	static boolean blocking() {
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(x==0 && y==0) continue;
				if(x==N-1 && y == M-1) continue;
				if(board[x][y] == 1) continue;
				
				Wall tmpWall = new Wall();
				if(x == 0) tmpWall.u = true;
				if(x == N-1) tmpWall.d = true;
				if(y == 0) tmpWall.l = true;
				if(y == M-1) tmpWall.r = true;
				
//				vis[x][y] = -1;
//				print();
//				vis[x][y] = 0;
				
				for(int i=0; i<8; i++) {
					int nx = x + dxy[i][0], ny = y + dxy[i][1];
					if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
					if(board[nx][ny] == 1) {						
						int n = vis[nx][ny];
//						System.out.println();
						if(wall.get(n).u) tmpWall.u = true;
						if(wall.get(n).d) tmpWall.d = true;
						if(wall.get(n).l) tmpWall.l = true;
						if(wall.get(n).r) tmpWall.r = true;
					}
				}
				
				if(isDevided(tmpWall)) {
//					System.out.println("success");
//					System.out.printf("%d %d %s\n", x, y, tmpWall);
//					print();
//					System.out.println();
					return true;
				}
			}
		}
		return false;
	}
	
	static boolean isDevided(Wall tmpWall) {
		if((tmpWall.u || tmpWall.r) && (tmpWall.l || tmpWall.d)) return true;
//		if(tmpWall.u && (tmpWall.d || tmpWall.l)) return true;
//		if(tmpWall.d && (tmpWall.u || tmpWall.r)) return true;
//		if(tmpWall.l && (tmpWall.u || tmpWall.r)) return true;
//		if(tmpWall.r && (tmpWall.l || tmpWall.d)) return true;
		return false;
	}
	
	static void makeWall(int x, int y, int num) {
		Queue<Point> que = new ArrayDeque<>();
		vis[x][y] = num;
		que.offer(new Point(x, y));
		
		isBorder(x, y, num);
		
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<8; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(board[nx][ny] != 0 && vis[nx][ny] == 0) {
					vis[nx][ny] = num;
					que.offer(new Point(nx, ny));
					
					isBorder(nx, ny, num);
				}
			}
		}
	}
	static void isBorder(int x, int y, int num) {
		if(x == 0) wall.get(num).u = true;
		if(x == N-1) wall.get(num).d = true;
		if(y == 0) wall.get(num).l = true;
		if(y == M-1) wall.get(num).r = true;
	}
	
	
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
}