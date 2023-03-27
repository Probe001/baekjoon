package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_2230_수고르기_노호종 {
	static int N, M;
	static int[] A;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		A = new int[N];
		for(int i=0; i<N; i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(A);
		int res = twoPointer();
		System.out.println(res);
		
		br.close();
	}
	
	static int twoPointer() {
		int left = 0, right = 0;
		int minDiff = Integer.MAX_VALUE;
		while(left < N && right < N) {
			int L = A[left];
			int R = A[right];
			int diff = R-L;
			if(diff < M) {
				right++;
			} else {
				minDiff = Math.min(minDiff, diff);
				left++;
			}
//			System.out.println(left+" "+right+" "+L+" "+R);
			if(right < left) break;
		}
		return minDiff;
	}

}
