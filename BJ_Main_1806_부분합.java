package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_1806_부분합 {
	static int N, S;
	static int[] A;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		
		int res = twoPointer();
		System.out.println(res == Integer.MAX_VALUE ? 0 : res);
		
		br.close();
	}
	
	static int twoPointer() {
		int st = 0, en = 0;
		int length = 1;
		int sum = A[0];
		int minLength = Integer.MAX_VALUE;
		
		while(st < N && en < N) {
			if(sum < S) {
				if(en+1 >= N) break;
				sum += A[++en];
				length++;
			} else {
				minLength = Math.min(minLength, length);
				sum -= A[st++];
				length--;
			}
//			System.out.println(st+" "+en+" "+A[st]+" "+A[en]+" "+sum );
			if(en < st) break;
		}
		return minLength;
	}

}
