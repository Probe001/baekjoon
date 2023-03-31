import java.util.Scanner;

public class Main {
	static int N;
	static int maxLen = Integer.MIN_VALUE;
	static int[] dp = new int[1001];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		int[] board = new int[N];
		
		for(int ind = 0; ind<N;ind++) {
			board[ind] = sc.nextInt();
		}
		
		for(int ind = 0; ind < N;ind++) {
			int maxNum = 0;
			for(int ind2 = 0; ind2 < ind; ind2++) {
				if(board[ind2] < board[ind]) {
					maxNum = maxNum < dp[board[ind2]] ? dp[board[ind2]] : maxNum;
					
				}
			}
			dp[board[ind]] = maxNum+1;
			maxLen = maxLen < dp[board[ind]] ? dp[board[ind]] : maxLen;
		}
		System.out.println(maxLen);
		
		sc.close();
	}
	
}