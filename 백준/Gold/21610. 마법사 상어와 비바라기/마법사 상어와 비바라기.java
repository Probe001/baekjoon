/**
 * ## 문제 요약

1. N * N 격자. 각 격자에는 무한대로 물 저장 가능
2. 격자 왼쪽 위 (1, 1) 오른쪽 아래 (N, N)
3. 격자는 좌 우가 연결, 상 하가 연결돼있다
4. 비바라기를 시전하면 (N,1), (N,2), (N-1,1), (N-1,2)에 비구름 생성
5. 구름이 M번 이동
-> 방향 d와 거리 s. 방향은 8방
6. 이동 방식
	1. 모든 구름이 d_i 방향으로 s_i칸 이동
	2. 각 구름에서 비가 내려 구름 밑 바구니에 물이 1 증가
	3. 구름이 모두 사라짐
	4. 2에서 물이 증가한 칸들(r,c)에 물복사 버그 마법 사용
	- 대각선 방향으로 거리가 1인 칸의 물이 있는 바구니 수 만큼 (r, c)에 물 양 증가
	- 경계를 넘어가는 칸은 거리가 1인 칸이 아님
	5. 바구니 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고 물 양 2 줄어듬
	구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 함
7. M번의 이동이 끝난 후 바구니에 들어있는 물의 양의 합은?

## 입력 및 제약조건

1. 첫째 줄 : N, M
2 <= N <= 50, 1 <= M <= 100

2. N 개 줄 : 보드의 상태
0 <= A[r][c] <= 100

3. M 개 줄 : 이동의 정보 d,s 순서대로 한줄씩
1 <= d <= 8, 1 <= s <= 50

## 출력

1. 첫째 줄 : M번의 이동이 끝난 후 바구니의 물의 양의 총합

## 문제 풀이

1. 구름은 어차피 한번에 하나씩 생기므로 구름 클래스를 만들어 분포를 큐에 저장
2. 큐에서 하나씩 제거하며 이동정보를 더해서 보드에 +1
3. 비가 내린 칸에는 구름이 생기지 않으므로 vis 배열에 표시한다.
4. 전부 비가 내린 뒤 vis배열이 true인 값들을 대상으로 물복사 버그 시전
5. vis 배열이 false 인 값들을 대상으로 구름 생성
4와 5는 동시에 일어나면 안된다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, M;
	static int[][] A;
	// 풀이용
	static class Cloud{
		Queue<Point> que = new ArrayDeque<>();
		
		Cloud(int n) {
			que.offer(new Point(n-1, 0));
			que.offer(new Point(n-1, 1));
			que.offer(new Point(n-2, 0));
			que.offer(new Point(n-2, 1));
		}
		
		void rain(int d, int s, int[][] board, boolean[][] vis) {
			int[][] dxy = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
			while(!que.isEmpty()) {
				Point now = que.poll();
				int n = board.length;
				int nx = (now.x + n*s + dxy[d-1][0]*s)%n;
				int ny = (now.y + n*s + dxy[d-1][1]*s)%n;
				board[nx][ny]++;
				vis[nx][ny] = true;
			}
		}
		
		void addCloud(int x, int y, int[][] board) {
			que.offer(new Point(x, y));
			board[x][y] -= 2;
		}
	}
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}
	static boolean[][] vis;
	static int[][] dxy2 = {{-1,-1},{-1,1},{1,-1},{1,1}};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		A = new int[N][N];
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				A[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		// 풀이 시작
		// 1. 구름 생성
		Cloud cloud = new Cloud(N);
		vis = new boolean[N][N];
		
		for(int i=0; i<M; i++) {
			visInit();
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			// 2. 구름 이동 후 비내리기
			cloud.rain(d, s, A, vis);
			// 3. 비 내린 칸에 물 복사하기
			for(int x=0; x<N; x++) {
				for(int y=0; y<N; y++) {
					if(!vis[x][y]) continue;
					int tmp = 0;
					for(int j=0; j<4; j++) {
						int nx = x+dxy2[j][0], ny = y+dxy2[j][1];
						if(nx < 0 || N <= nx || ny < 0 || N <= ny) continue;
						if(A[nx][ny]>0) tmp++;
					}
					A[x][y] += tmp;
				}
			}
			// 4. 구름 생성
			for(int x=0; x<N; x++) {
				for(int y=0; y<N; y++) {
					if(vis[x][y]) continue;
					if(A[x][y]<2) continue;
					cloud.que.add(new Point(x, y));
					A[x][y] -= 2;
				}
			}
		}
		
		// 물 양 총합 구하기
		int tmp = 0;
		for(int x=0; x<N; x++) {
			for(int y=0; y<N; y++) {
				tmp += A[x][y];
			}
		}
		System.out.println(tmp);
		br.close();
	}
	
	static void visInit() {
		for(int x=0; x<vis.length; x++) {
			Arrays.fill(vis[x], false);
		}
	}
	
	static void print() {
		for(int x=0; x<A.length; x++) {
			System.out.println(Arrays.toString(A[x]));
		}
		System.out.println();
	}

}