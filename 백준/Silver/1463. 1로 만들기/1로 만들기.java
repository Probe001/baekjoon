import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Main {
	static int N;
	
	static int[] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		dp = new int[N+1];
		Arrays.fill(dp, -1);
		bfs(1);
		System.out.println(dp[N]);
		
		br.close();
	}
	static void bfs(int x) {
		Queue<Integer> que = new ArrayDeque<>();
		dp[x] = 0;
		que.offer(x);
		while(!que.isEmpty()) {
			int now = que.poll();
			int[] next = {now+1, now*2, now*3};
			for(int i=0; i<3; i++) {
				if(next[i] > N) continue;
				if(dp[next[i]] != -1) continue;
				dp[next[i]] = dp[now]+1;
				que.offer(next[i]);
				if(next[i] == N) return;
			}
		}
	}

}