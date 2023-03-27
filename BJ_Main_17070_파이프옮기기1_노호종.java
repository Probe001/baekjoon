package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BJ_Main_17070_파이프옮기기1_노호종 {
	// 입력용
	static int N;
	static int[][] board;
	// 풀이용
	static class Pipe{
		int x, y; // 왼쪽 위치
		int d; // 방향 (0: 가로, 1: 대각선, 2: 세로)
		
		Pipe(int x, int y, int d){
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	static int[][][] dxy = {
			{{0,1}, {1,1}},			// 가로, 대각
			{{0,1}, {1,1}, {1,0}},	// 가로, 대각, 세로
			{{1,1}, {1,0}}			// 대각, 세로
	};
	static int res;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][];
		for(int x=0;x<N; x++) {
			board[x] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		// 풀이
		res = 0;
		bfs();
		System.out.println(res);
		
		br.close();
	}
	
	static void bfs() {
		Queue<Pipe> que = new ArrayDeque<>();
		que.offer(new Pipe(0,1,0)); // 초기 위치 (0,1) 방향 가로(0)
		while(!que.isEmpty()) {
			Pipe now = que.poll();
			for(int i=0; i<dxy[now.d].length; i++) {
				int nx = now.x+dxy[now.d][i][0], ny = now.y + dxy[now.d][i][1];
				if(N<= nx || N <= ny) continue;
				if(board[nx][ny] == 1) continue;
				// 다음 방향 계산
				int nextD = now.d == 2 ? i+1 : i;
				// 대각선인 경우엔 바로 오른쪽이랑 바로 아래도 비어있어야 됨
				if(nextD == 1) {
					if(board[now.x+1][now.y] != 0 || board[now.x][now.y+1] != 0) continue;
				}
				if(nx == N-1 && ny == N-1) {
					res++;
					continue;
				}
				que.offer(new Pipe(nx, ny, nextD));
			}
		}
	}

}
