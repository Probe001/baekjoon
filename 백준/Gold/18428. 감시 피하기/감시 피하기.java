/**
 * ## 문제 요약

1. N * N 크기 복도
2. 특정 위치에는 선생님, 학생, 장애물 존재
3. 학생들이 감시에 들키지 않아야 됨
4. 선생님은 자신의 위치에서 상, 하, 좌, 우 4가지 방향으로 감시 진행
5. 복도에 장애물이 있으면 그 뒤는 볼 수 없다.
6. 장애물이 없다면 끝까지 볼 수 있다.
7. 선생님 T, 학생 S, 장애물 O
8. 학생들은 정확히 3개의 장애물을 설치해야 한다.
9. 장애물을 설치했을 때 모든 학생들이 감시를 피할 수 있는지를 판단

## 입력 및 제약조건

1. 첫째 줄 : 자연수 N
3 <= N <= 6

2. N 개 줄 : 복도의 정보
학생: S, 선생님: T, 없음: X

3. 전체 선생님의 수는 5 이하 자연수
4. 전체 학생 수는 30 이하 자연수
5. 빈칸의 개수는 3개 이상으로 주어짐

## 출력

1 첫째 줄 : 모두 피할 수 있는지 여부 출력
가능: YES, 불가능: NO

## 문제 풀이

1. DFS로 모든 위치에 3개의 장애물을 놓아본다.
2. 다 놓았을 때 선생님의 감시에 학생들이 들어오는지 파악.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static char[][] board;
	static boolean[][] vis;
	static List<Teacher> teachers;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	static boolean res;
	
	static void dfs(int k) {
		if(k==3) {
//			print();
			boolean flag = finding();
			if(flag) res = true;
			return;
		}
		
		for(int x=0; x<N; x++) {
			for(int y=0; y<N; y++) {
				if(vis[x][y]) continue;
				if(board[x][y] != 'X') continue;
				board[x][y] = 'O';
				vis[x][y] = true;
				dfs(k+1);
				board[x][y] = 'X';
				vis[x][y] = false;
			}
		}
	}
	
	static boolean finding() {
		boolean isSuccess = true;
		A: for(int t=0; t<teachers.size(); t++) {
			for(int i=0; i<4; i++) {
				int nx = teachers.get(t).x, ny = teachers.get(t).y;
				while(true) {
					nx += dxy[i][0];
					ny += dxy[i][1];
					if(nx < 0 || N <= nx || ny < 0 || N <= ny) break;
					if(board[nx][ny] == 'O') break;
					if(board[nx][ny] == 'S') {
						isSuccess = false;
						break A;
					}
				}
			}
		}
		return isSuccess;
	}
	
	static class Teacher{
		int x, y;
		Teacher(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new char[N][N];
		teachers = new ArrayList<>();
		vis = new boolean[N][N];
		for(int x=0; x<N; x++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				board[x][y] = st.nextToken().charAt(0);
				if(board[x][y] == 'T') {
					teachers.add(new Teacher(x, y));
				}
				if(board[x][y] != 'X') vis[x][y] = true;
			}
		}
		
		dfs(0);
		
		System.out.println(res ? "YES" : "NO");
		br.close();
	}

	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(board[x]));
		}
		System.out.println();
	}
}