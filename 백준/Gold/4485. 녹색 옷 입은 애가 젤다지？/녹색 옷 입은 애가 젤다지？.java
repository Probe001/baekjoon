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