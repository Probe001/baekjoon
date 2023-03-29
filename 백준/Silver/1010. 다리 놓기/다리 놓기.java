import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static long[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		
		// 파스칼의 삼각형 구현
		dp = new long[31][31];
		dp[0][0] = 1;
		for(int x=1;x<31;x++) {
			for(int y=0; y<=x; y++) {
				if(y==0) {
					dp[x][y] = 1;
					continue;
				}
				dp[x][y] = dp[x-1][y]+dp[x-1][y-1];
			}
//			System.out.println(Arrays.toString(dp[x]));
		}
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=0; tc<T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			sb.append(dp[M][N]).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}

}