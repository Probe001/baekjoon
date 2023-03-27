package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_5656_벽돌깨기_노호종 {
	/* 문제 요약
	 * 구슬은 N번 쏨. W*H 배열
	 * 0은 빈공간, 그 외는 벽돌
	 * 벽돌에 적힌 숫자 -1 만큼 상하좌우로 벽돌을 부숨
	 * 빈공간이 생기면 벽돌은 밑으로 내려감
	 * 
	 * N개의 벽돌을 떨어트려 최대한 많은 벽돌을 제거해야 한다.
	 * 남은 벽돌의 개수는?
	 * 
	 * 백트래킹으로 일단 벽돌 위치 고르고 떨어뜨리기. => 원본 배열이 필요함.
	 */
	static int N, W, H, res;
	static int[][] board, dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	
	static class Point{
		int x, y, l;

		public Point(int x, int y, int l) {
			super();
			this.x = x;
			this.y = y;
			this.l = l;
		}
	}

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream(new File("input.txt")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			board = new int[H][];
			for(int h=0; h<H; h++) {
				board[h] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			}
			res = Integer.MAX_VALUE;
			backtracking(0);
			sb.append('#').append(tc).append(' ').append(res != Integer.MAX_VALUE ? res : 0).append('\n');
		}
		System.out.println(sb);
		br.close();
	}
	
	static void backtracking(int k) {
		if(k == N) {
			int t = 0;
			for(int x=0; x<H; x++) {
				for(int y=0; y<W; y++) {
					if(board[x][y] != 0) t++;
				}
			}
			if(t < res) {
				res = t;
//				print();
			}
			return;
		}
		
		int[][] tmp = new int[H][W]; // 원본 배열 저장
		for(int x=0; x<H; x++) {
			for(int y=0; y<W; y++) {
				tmp[x][y] = board[x][y];
			}
		}
		
		for(int y=0; y<W; y++) {
			if(isZero(y)) continue;
			breaking(y);
			backtracking(k+1);
			for(int i=0; i<H; i++) {
				for(int j=0; j<W; j++) {
					board[i][j] = tmp[i][j];
				}
			}
		}
	}
	
	static boolean isZero(int y) { // 전부 0인 경우
		for(int x=0; x<H; x++) {
			if(board[x][y] != 0) return false;
		}
		return true;
	}
	
	static void breaking(int y) {
		// 처음 위치 찾기
		int x = 0;
		while(board[x][y] == 0) {
			x++;
		}
		
		boolean[][] vis = new boolean[H][W];
		Queue<Point> que = new LinkedList<>();
		que.offer(new Point(x, y, board[x][y]));
		vis[x][y] = true;
		while(!que.isEmpty()) {
			Point now = que.poll();
			board[now.x][now.y] = 0;
			for(int i=0; i<4; i++) {
				for(int t=1; t<now.l; t++) { // 길이만큼 탐색					
					int nx = now.x+dxy[i][0]*t, ny = now.y+dxy[i][1]*t;
					if(nx < 0 || H <= nx || ny < 0 || W <= ny) continue;
					if(vis[nx][ny]) continue;
					vis[nx][ny] = true;
					que.offer(new Point(nx, ny, board[nx][ny]));
					board[nx][ny] = 0;
				}
			}
		}
		down();
	}
	
	static void down() {
		for(int y=0; y<W; y++) {
			int ind = H-1;
			for(int x=H-1; x>-1; x--) {
				if(board[x][y] != 0) {
					int t = board[x][y];
					board[x][y] = 0;
					board[ind][y] = t;
					ind--;
				}
			}
		}
	}
	
	static void print() {
		for(int x=0; x<H; x++) {
			System.out.println(Arrays.toString(board[x]));
		}
		System.out.println();
	}

}
