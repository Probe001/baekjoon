import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[][] board;
	static boolean[] vis;
	static long res;
	static int[] src;
	
	static void dfs(int k, long tmp, int now, int start) {
		// 인덱스, 경로비용, 현재노드, 시작노드
		if(k==N) {
			if(board[now][start] == 0) return;
			res = Math.min(res, tmp + board[now][start]);
			return;
		}
		
		for(int i=0; i<N; i++) { // 다음 목적지 설정
			if(vis[i]) continue; // 방문한 곳은 지나쳐
			if(board[now][i] == 0) continue; // 현재 노드에서 다음노드로 가는 길 없을 때
			vis[i] = true; // 방문처리
			long t = tmp + board[now][i]; // 현재 노드에서 다음노드 가는 비용 추가
//			if(t > res) continue; // 이미 최소비용보다 많으면 안가도 됨
			dfs(k+1, t, i, start); // 다음 노드로 넘어감
			vis[i] = false;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		for(int x=0; x<N; x++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		vis = new boolean[N];
		res = Long.MAX_VALUE;
		for(int st = 0; st<N; st++) {
			vis[st] = true;
			dfs(1, 0, st, st);
			// 시작점은 정해졌음.
			vis[st] = false;
		}
		System.out.println(res);
		
		br.close();
	}

}