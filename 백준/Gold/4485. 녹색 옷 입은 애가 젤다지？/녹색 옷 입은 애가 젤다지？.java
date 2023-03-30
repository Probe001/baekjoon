/**
 * ## 문제 요약

1. 링크는 N*N 크기의 동굴 좌상단에 존재 (0,0)
2. 우하단까지 이동해야 함
3. 도둑루피의 크기만큼 소지금을 잃게 된다.
4. 잃는 금액을 최소로 하여 이동해야하고 상하좌우로 움직임
5. 잃는 최소 금액은 얼마인가?

## 입력 및 제약조건

여러 테스트케이스로 이뤄짐

1. 첫째 줄 : N
2 <= N <= 125
N이 0이면 전체 입력이 종료된다.

2. N 개 줄 : 도둑 루피의 크기가 공백으로 주어짐
0 <= 루피 크기 <= 9

## 출력

1. 한 줄에 정답 하나씩 출력

## 문제 풀이 1 - 다익스트라

1. 다익스트라로 풀기
2. N*N의 minDist 배열을 세우고 Integer.Max_Value로 채워넣는다.
3. 사방을 탐색한다.
    1. minDist[nx][ny], minDist[now.x][now.y]+board[nx][ny] 를 비교한다.
    2. 만약 새로운 값으로 갱신된다면 que에 넣는다.
4. minDist[N-1][N-1]을 출력한다. 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[][] board, minDist;
	
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder();
		int tc = 0;
		while(true) {
			N = Integer.parseInt(br.readLine());
			if(N == 0) break;
			
			board = new int[N][N];
			minDist = new int[N][N];
			for(int x = 0; x< N; x++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int y=0; y<N; y++) {
					board[x][y] = Integer.parseInt(st.nextToken());
					minDist[x][y] = Integer.MAX_VALUE;
				}
			}
			dijkstra();
			sb.append("Problem ").append(++tc).append(": ").append(minDist[N-1][N-1]).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void dijkstra() {
		Queue<Point> que = new ArrayDeque<>();
		minDist[0][0] = board[0][0];
		que.offer(new Point(0,0));
		while(!que.isEmpty()) {
			Point now = que.poll();
			
			
			
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || N <= ny) continue;
				if(minDist[nx][ny] <= minDist[now.x][now.y]+board[nx][ny]) continue;
				minDist[nx][ny] = minDist[now.x][now.y]+board[nx][ny];
				que.offer(new Point(nx, ny));
			}
		}
	}
	
	static class Point{
		int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

}
