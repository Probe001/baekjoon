/**
 * ## 문제 요약

1. 9 * 9 크기의 스도쿠 보드
2. 하다 만 스도쿠 보드가 주어졌을 때 마저 끝내라

## 입력 및 제약조건

1. 9 개 줄 : 스도쿠 보드의 상태. 공백없이.
숫자가 없는 칸은 0으로 주어짐

제한 시간 : 528ms


## 출력

1. 9 개 줄 : 완성된 스도쿠 보드
답이 존재한다면 사전식으로 앞서는 것을 출력함.
81자리의 수가 가장 작은 경우를 출력.

## 문제 풀이 - 백트래킹

1. 좌상단부터 읽어들이면서 빈칸이면 List<Point> src에 넣는다.
2. 빈칸이 아니라면 스도쿠 조건을 확인하는 배열을 완성시킨다.
    - 가로 boolean[] row[10] : row[x][board[x][y]] = true;
    - 세로 boolean[] col[10] : col[y][board[x][y]] = true;
    - 네모 boolean[] squ[10] : squ[3*(x/3)+(y/3)][board[x][y]] = true;
3. src를 다 넣으면 하나씩 넣어본다.
4. 완성되는 순간이 가장 작은 경우.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static int[][] board;
	
	static boolean[][] row, col, squ;
	static List<Point> src;
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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		board = new int[9][9];
		row = new boolean[9][10];
		col = new boolean[9][10];
		squ = new boolean[9][10];
		src = new ArrayList<>();
		
		for(int x= 0 ; x<9; x++) {
			char[] st = br.readLine().toCharArray();
			for(int y=0; y<9; y++) {
				board[x][y] = st[y]-'0';
				if(board[x][y] == 0) {
					src.add(new Point(x,y));
				} else {
					row[x][board[x][y]] = true;
					col[y][board[x][y]] = true;
					squ[3*(x/3)+(y/3)][board[x][y]] = true;
				}
			}
		}
		backtracking(0);
		
		br.close();
	}

	private static boolean backtracking(int k) {
		if(k==src.size()) {
			print();
			return true;
		}
		Point now = src.get(k);
		for(int i=1; i<10; i++) {
			if(row[now.x][i]) continue;
			if(col[now.y][i]) continue;
			if(squ[3*(now.x/3)+(now.y/3)][i]) continue;
			
			row[now.x][i] = true;
			col[now.y][i] = true;
			squ[3*(now.x/3)+(now.y/3)][i] = true;
			
			board[now.x][now.y] = i;
			if(backtracking(k+1)) return true;
			board[now.x][now.y] = 0;
			
			row[now.x][i] = false;
			col[now.y][i] = false;
			squ[3*(now.x/3)+(now.y/3)][i] = false;
		}
		return false;
	}
	static void print() {
		StringBuilder sb = new StringBuilder();
		for(int x=0; x<9; x++) {
			for(int y=0; y<9; y++) {
				sb.append(board[x][y]);
			}
			sb.append('\n');
		}
		System.out.println(sb);
	}
}