import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int K;
	static int W, H;
	static int[][] board;
	
	static class Monkey{
		int x, y;
		int jump;
		
		public Monkey(int x, int y, int jump) {
			super();
			this.x = x;
			this.y = y;
			this.jump = jump;
		}
	}
	static int[][][] dxy = {{{0,1},{1,0},{0,-1},{-1,0}}, {{2,1},{2,-1},{1,2},{1,-2},{-1,2},{-1,-2},{-2,1},{-2,-1}}};
	static int[][][] vis; // [동작수, 말움직임횟수]
	static List<Integer> res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		board = new int[H][W];
		for(int x=0; x<H; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<W; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		vis = new int[H][W][2];
		res = new ArrayList<>();
		bfs();
//		print();
		if(W==1 && H == 1) res.add(0);
		if(res.size() == 0) System.out.println(-1);
		else System.out.println(res.get(0));
		
		br.close();
	}
	
	static void bfs() {
		Queue<Monkey> que = new ArrayDeque<>();
		vis[0][0][0] = 1;
		vis[0][0][1] = 0;
		que.offer(new Monkey(0,0,0));
		while(!que.isEmpty()) {
			Monkey now = que.poll();
			for(int d=0; d<2; d++) { // 점프여부
				if(d==1 && now.jump >= K) continue;
				for(int i=0; i<dxy[d].length; i++) {
					int nx = now.x + dxy[d][i][0], ny = now.y + dxy[d][i][1];
//					System.out.println(nx+" "+ny);
					if(nx<0 || H <= nx || ny < 0 || W <= ny) continue;
					if(board[nx][ny] == 1) continue; // 벽
					// 많이 돌아가더라도 원숭이 점프 수가 적으면 들어갈 수 있다.
					int savedAct = vis[nx][ny][0];
					int savedJump = vis[nx][ny][1];
					if(savedAct != 0) { // 한번도 간적 없으면 그냥 갈수있음
						// 한번이라도 갔다면 원숭이 점프 수를 비교
						if(now.jump+d >= savedJump) continue;
					}
					
					vis[nx][ny][0] = vis[now.x][now.y][0]+1;
					vis[nx][ny][1] = now.jump+d;
					que.offer(new Monkey(nx, ny, now.jump+d));
					if(nx == H-1 && ny == W-1) {
						res.add(vis[nx][ny][0]-1);
					}
				}
			}
		}
	}
	static void print() {
		for(int x=0; x<H; x++) {
			System.out.println(Arrays.toString(board[x])+" "+Arrays.toString(vis[x]));
		}
		System.out.println();
	}

}