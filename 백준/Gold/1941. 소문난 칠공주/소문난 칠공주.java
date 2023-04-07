/**
 * ## 문제요약

1. 25명의 여학생. 5*5 격자 형태로 자리 배치
2. 모든 여학생은 이다솜파 아니면 임도연파
3. 7명의 여학생은 소문난 칠공주
4. 7명의 자리는 세로나 가로로 인접해있어야 한다.
5. 7명의 여학생 중 4명 이상은 반드시 '이다솜파' 이어야 함
6. 여학생 반의 자리배치도가 주어질 때
'소문난 칠공주'가 될 수 있는 모든 경우의 수를 구하라.

## 입력 및 제약조건

1. 5 개 줄 : 5*5 행렬이 공백없이 'S'(이다솜파), 'Y'(임도연파)로 주어짐

## 출력

1. 첫째 줄 : '소문난 칠공주'를 결성할 수 있는 경우의 수 출력

## 문제 풀이

1. 25 C 7 : 25!/(18!*7!) = 480700
2. 시작점에서 BFS를 돌렸을 때 연결돼있지 않으면 pass
3. 모두 완성 가능하면 count++
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Main {
	// 입력용
	static char[][] board;
	// 풀이용
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	static boolean[][] vis;
	static int res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		board = new char[5][];
		for(int i=0; i<5; i++) {
			board[i] = br.readLine().toCharArray();
		}
		vis = new boolean[5][5];
		res = 0;
		combination(0, 0, 0);
		System.out.println(res);
		
		br.close();
	}
	
	static void combination(int k, int ind, int imdo) {
		if(k == 7) {
			if(bfs(ind/5, ind%5)) {
//				System.out.println(ind);
				res++;
//				print();
			}
			return;
		}
		
		for(int i=ind; i<25; i++) {
			int nx = i/5, ny = i%5;
			if(vis[nx][ny]) continue;
			if(board[nx][ny] == 'Y') {
				if(imdo+1 >= 4) continue;
				vis[nx][ny] = true;
				combination(k+1, i, imdo+1);
				vis[nx][ny] = false;
			} else {
				vis[nx][ny] = true;
				combination(k+1, i, imdo);
				vis[nx][ny] = false;
			}
		}
		
	}
	static boolean bfs(int x, int y) {
		Queue<Point> que = new ArrayDeque<>();
		boolean[][] localvis = new boolean[5][5];
		localvis[x][y] = true;
		que.offer(new Point(x, y));
		
		int cnt = 1;
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x+dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || 5 <= nx || ny < 0 || 5 <= ny) continue;
				if(localvis[nx][ny]) continue;
				if(!vis[nx][ny]) continue;
				localvis[nx][ny] = true;
				que.offer(new Point(nx, ny));
				cnt++;
			}
		}
		
		if(cnt == 7) {
//			System.out.println(x+" "+y);
//			for(int i=0; i<5; i++) {
//				System.out.println(Arrays.toString(localvis[i]));
//			}
//			System.out.println();
			
			return true;
		}
		else return false;
	}
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	static void print() {
		for(int i=0; i<5; i++) {
			for(int j=0; j<5; j++) {
				System.out.print(vis[i][j] ? "1 " : "0 ");
			}
			System.out.println();
		}
		System.out.println();
	}
}