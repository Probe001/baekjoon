package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_1926_그림_노호종 {
	/* 문제 해석
	 * 그림의 개수, 가장 넓은것의 넓이 출력
	 * 그림의 넓이: 그림에 포함된 1의 개수
	 * 
	 * 모든 그림을 한번씩 보면서 DFS 또는 BFS를 돌리면
	 * 각 영역당 크기가 나오고, 해당 셀이 어떤 그림에 있는지 파악이 됨.
	 * 
	 * 풀이 방식
	 * x = 0, y = 0 부터 전체 영역을 하나씩 보면서
	 * 만약에 기존 배열(board)이 1이고 방문 배열(vis)이 false라면 아직 방문하지 않은 것.
	 * 그림의 개수 ++
	 * DFS 돌면 해당 그림을 전부 다 돌게 되므로, DFS를 호출할때마다 크기가 커짐
	 */
	
	static int N, M;
	static int[][] board;
	static boolean[][] vis;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	
	static int num, maxSize; // 그림의 개수, 그림의 최대 넓이
	
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 입력 완료
		vis = new boolean[N][M];
		num = 0;
		maxSize = 0;
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(board[x][y] == 1 && !vis[x][y]) {
					vis[x][y] = true;
					dfs(new Point(x,y), 1);
					num++;
				}
			}
		}
		System.out.println(num);
		System.out.println(maxSize);
		
		br.close();
	}
	
	static void dfs(Point p, int size) throws InterruptedException {
		maxSize = Math.max(size, maxSize);
		print();
		
		for(int i=0; i<4; i++) {
			int nx = p.x + dxy[i][0];
			int ny = p.y + dxy[i][1];
			if(nx<0 || N <= nx || ny < 0 || M <= ny) continue;
			if(board[nx][ny] == 0) continue;
			if(vis[nx][ny]) continue;
			vis[nx][ny] = true;
			dfs(new Point(nx, ny), size+1);
		}
		
	}
	
	static void print() throws InterruptedException {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(vis[x]));
		}
		System.out.println();
		Thread.sleep(1000);
	}

}
