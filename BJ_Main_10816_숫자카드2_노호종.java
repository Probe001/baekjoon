package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_10816_숫자카드2_노호종 {
	static int N, M;
	static int[] board;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		board = new int[N];
		for(int i=0; i<N; i++) {
			board[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(board);
		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<M; i++) {
			int target = Integer.parseInt(st.nextToken());
			int right = findRight(target);
			int left = findLeft(target);
			sb.append(right-left).append(' ');
		}
		System.out.println(sb);
		
		br.close();
	}
	static int findRight(int target) {
		int st = 0, en = N;
		while(st < en) {
			int mid = st + (en-st)/2;
			if(board[mid] > target) en = mid;
			else if(board[mid] <= target) st = mid+1;
		}
		return st;
	}
	static int findLeft(int target) {
		int st = 0, en = N;
		while(st < en) {
			int mid = st + (en-st)/2;
			if(board[mid] >= target) en = mid;
			else if(board[mid] < target) st = mid+1;
		}
		return st;
	}
}
