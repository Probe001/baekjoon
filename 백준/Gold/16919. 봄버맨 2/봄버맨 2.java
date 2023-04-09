/**
## 문제 요약

1. R * C 격자판
2. 폭탄은 3초 뒤에 폭발, 인접한 네 칸도 파괴됨
3. 인접한 칸에 폭탄이 있는 경우 폭발없이 그냥 사라짐 => 연쇄반응은 없다.
4. 봄버맨은 모든 칸을 자유롭게 이동 가능 ( 폭탄이 있어도 )
5. 봄버맨의 행동
1) 가장 처음에 일부 칸에 폭탄을 설치한다. ( 모든 폭탄은 동시에 설치됨 )
2) 다음 1초동안은 가만히 있는다.
3) 다음 1초 동안 설치안된 모든 칸에 폭탄을 설치한다.
4) 다음 1초에 처음 설치한 폭탄이 모두 터진다.
5) 3과 4를 반복한다.
6. N초가 흐른 후에 격자판 상태를 구하라

## 입력 및 제약조건

1. 첫째 줄 : R, C, N
1 <= R, C <= 200, 1 <= N <= 10^9

2. R 개 줄 : 격자판의 초기 상태
'.' 빈 칸, 'O' 폭탄

## 출력

N 초가 지난 후의 격자판의 상태를 출력하라

## 문제 풀이

1. 초기상태, 2초일 때, 3초일 때, 4초일 때의 배열을 만든다.
2. 4초를 기준으로 사이클이 돌아가나 초기상태에서 다음과 같은 예제일 경우 초기와는 다르게 돌아간다.
3 3 5
OO.
OOO
OOO

3. 1초 초기상태
4. 2초 전부
5. 3초 전부 - 초기
6. 4초 전부
5. 5초 전부 - 3초

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int R, C, N;
	static char[][][] board;
	// 풀이용
	static ArrayList<Point> bombs;
	static int[][] dxy = {{0,0},{0,1},{1,0},{0,-1},{-1,0}};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		board = new char[4][R][C]; // N%4일 때의 값. 0: 전부, 1: 전부 - 2 , 2: 전부 - 초기, 3: 초기 상태
		bombs = new ArrayList<>();
		for(int x=0; x<R; x++) {
			board[3][x] = br.readLine().toCharArray();
			for(int y=0; y<C; y++) {
				if(board[3][x][y] == 'O') bombs.add(new Point(x, y));
			}
		}
		// 풀이
		for(int x=0; x<R; x++) {
			Arrays.fill(board[0][x], 'O');
			Arrays.fill(board[1][x], 'O');
			Arrays.fill(board[2][x], 'O');
		}
		for(Point now : bombs) {
			for(int i=0; i<5; i++) {
				int nx = now.x+dxy[i][0], ny = now.y+dxy[i][1];
				if(nx < 0 || R <= nx || ny < 0 || C <= ny) continue;
				board[2][nx][ny] = '.';
			}
		}
		for(int x=0; x<R; x++) {
			for(int y=0; y<C; y++) {
				if(board[2][x][y] == 'O') {
					for(int i=0; i<5; i++) {
						int nx = x + dxy[i][0], ny = y + dxy[i][1];
						if(nx < 0 || R <= nx || ny < 0 || C <= ny) continue;
						board[1][nx][ny] = '.';
					}
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int x=0; x<R; x++) {
			for(int y=0; y<C; y++) {
				int target = -1;
				if(N == 1) target = 3;
				else if(N%2 == 0) target = 0;
				else if(N%4 == 3) target = 2;
				else if(N%4 == 1) target = 1;
				sb.append(board[target][x][y]);
			}
			sb.append('\n');
		}
		System.out.println(sb);
		br.close();
	}
	
	static class Point{
		int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

}