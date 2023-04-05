/**
 * ## 문제 요약

1. 부등호 기호 < 와 >가 k개 나열된 순서열 A
2. 부등호 앞 뒤에 서로 다른 한 자리 수 숫자를 넣어 모든 부등호 관계를 만족
3. 부등호 앞 뒤에 넣을 수 있는 숫자는 0 ~ 9. 선택된 숫자는 모두 달라야 함
4. 부등호 기호를 제거한 뒤 숫자를 모두 붙이면 하나의 수가 됨
5. 부등호 순서를 만족하는 숫자 중 최댓값과 최솟값을 찾아라

## 입력 및 제약조건

1. 첫째 줄 : 부등호 문자의 개수 k
2 <= k <= 9

2. 둘째 줄 : k개의 부등호 기호

## 출력

1. 부등호 관계를 만족하는 k+1 자리의 최대, 최소 정수 ( 첫자리가 0인 경우도 포함 )
첫째줄에 최대, 둘째 줄에 최소 정수 출력 (단, 앞자리가 0인 경우도 0을 출력한다.)

## 문제 풀이 - 백트래킹

1. 백트래킹을 이용해 전부 완성되는 값들을 문자열로 만들어서 넣는다.
2. 최대, 최소값을 출력한다.

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	// 입력용
	static int K;
	static char[] greater; // true인 경우 오른쪽 값이 더 크다
	// 풀이용
	static boolean[] vis;
	static int[] selected;
	static TreeSet<String> ts;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		K = Integer.parseInt(br.readLine());
		
		greater = new char[K];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0 ; i < K; i++) {
			greater[i]= st.nextToken().charAt(0);
		}
		// 풀이
		vis = new boolean[10];
		selected = new int[K+1];
		ts = new TreeSet<>();
		backtracking(0);
		System.out.println(ts.pollLast());
		System.out.println(ts.pollFirst());
		
		br.close();
	}
	static void backtracking(int k) {
		if(k == K+1) {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<selected.length; i++) {
				sb.append(selected[i]);
			}
			ts.add(sb.toString());
//			System.out.println(Arrays.toString(selected));
			return;
		}
		
		for(int i=0; i<10; i++) {
			if(vis[i]) continue;
			if(k > 0) {
				if(greater[k-1] == '<') {
					if(selected[k-1] > i) continue;
				} else {
					if(selected[k-1] < i) continue;
				}
			}
			vis[i] = true;
			selected[k] = i;
			backtracking(k+1);
			vis[i] = false;
		}
	}

}