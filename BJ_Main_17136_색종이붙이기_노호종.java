package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_Main_17136_색종이붙이기_노호종 {
	// 입력용
	static int[][] board;
	
	// 풀이용
	static ArrayList<Point> ones;
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	static int[] paper;
	static int minVal;

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		board = new int[10][10];
		ones = new ArrayList<>();
		
		for(int x=0; x<10; x++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int y=0; y<10; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
				if(board[x][y] == 1) {
					ones.add(new Point(x, y));
				}
			}
		}
		paper = new int[] {0,5,5,5,5,5};
		minVal = Integer.MAX_VALUE;
		dfs(0, ones.size(), 0);
		System.out.println(minVal == Integer.MAX_VALUE ? -1 : minVal);
		
		br.close();
	}
	
	static void dfs(int k, int left, int used) { // n번째 1의 위치, 남은 1의 개수, 종이 개수
		if(k == ones.size()) { // 다 돌았을 때
			if(left == 0) // 남은게 없다면
				minVal = Math.min(minVal, used);
			return;
		}
		
		Point now = ones.get(k);
		if(board[now.x][now.y] == 0) { // 빈칸이라면 다음 1을 본다.
			dfs(k+1, left, used);
			return;
		}
		
		int[][] tmp = new int[10][10];
		for(int x=0; x<10; x++) {
			for(int y=0; y<10; y++) {
				tmp[x][y] = board[x][y];
			}
		}
		
		A: for(int i=5; i>= 1; i--) {
			// 종이가 남아있다면
			if(paper[i] <= 0) continue;
			
			// 초기화
			for(int x=0; x<10; x++) {
				for(int y=0; y<10; y++) {
					board[x][y] = tmp[x][y];
				}
			}
			
			// 색종이로 덮기
			for(int x=0; x<i; x++) {
				for(int y=0; y<i; y++) {
					int nx = now.x+x, ny = now.y+y;
					if(nx >= 10 || ny >= 10) continue A;
					if(board[nx][ny] == 0) continue A;
					board[nx][ny] = 0;
				}
			}
			paper[i]--;
			dfs(k+1, left - i*i, used+1);
			paper[i]++;
		}
	}

}
