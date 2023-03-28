import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[][] board;
	static int[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		board = new int[N+1][3];
		for(int i=1; i<=N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			board[i][0] = Integer.parseInt(st.nextToken());
			board[i][1] = Integer.parseInt(st.nextToken());
			board[i][2] = Integer.parseInt(st.nextToken());
		}
		dp = new int[N+1][3];
		for(int i=1; i<=N; i++) {
			dp[i][0] = Math.min(dp[i-1][1], dp[i-1][2])+board[i][0];
			dp[i][1] = Math.min(dp[i-1][0], dp[i-1][2])+board[i][1];
			dp[i][2] = Math.min(dp[i-1][0], dp[i-1][1])+board[i][2];
		}
		int res = Integer.MAX_VALUE;
		for(int i=0; i<3; i++) {
			res = Math.min(res, dp[N][i]);
		}
		System.out.println(res);
		
		br.close();
	}

}