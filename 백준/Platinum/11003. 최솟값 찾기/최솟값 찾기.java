/**
## 문제 요약

1. N개의 수 A_1, A_2 ... A_N 과 L이 주어짐
2. D = A_{i-L+1} ~ A_i 중의 최솟값
3. D에 저장된 수를 출력하는 프로그램을 구하라
4. i <= 0인 A_i 는 무시하고 D를 구해야 한다.

## 입력 및 제약조건

1. 첫째 줄 : N과 L
1 <= L <= N <= 5,000,000

2. 둘째 줄 : N개의 수 A_i
-10^9 <= A_i <= 10^9

메모리 제한 : 512MB
시간 제한 : 2.4 초

## 출력

1. 첫째 줄 : D_i를 공백으로 구분해 순서대로 출력한다.

## 문제 풀이

1. 값이 작다면 Deque의 왼쪽에, 크다면 오른쪽에.
2. 오른쪽에 넣을 때 자신보다 que의 값이 크다면 그걸 빼고 넣는다.
왜냐? 어차피 i - L + 1 ~ i 까지는 내가 넣는 값이 제일 작거든
3. 왼쪽에 넣을 때는 해당 값의 인덱스가 범위 안에 존재하는지를 본다.
4. 가장 왼쪽 애를 출력한다.

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, L;
	static int[] A;
	// 풀이용
	static Deque<Node> dq;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		A = new int[N];
		st = new StringTokenizer(br.readLine());
		// 풀이
		dq = new ArrayDeque<>();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
			
			if(dq.isEmpty()) dq.offerFirst(new Node(A[i],i));
			else {
				if(A[i] <= dq.peekFirst().num) { // 넣을 값이 최솟값이면
					while(!dq.isEmpty() && dq.peekFirst().ind < i-L+1) {
						dq.pollFirst();
					}
					dq.offerFirst(new Node(A[i], i));
				} else { // 넣을 값이 현재 최소값보다 크다면
					while(!dq.isEmpty() && dq.peekLast().ind < i-L+1) dq.pollLast();
					while(!dq.isEmpty() && dq.peekLast().num > A[i]) dq.pollLast();
					dq.offerLast(new Node(A[i], i));
				}
//				System.out.println(dq);
				while(!dq.isEmpty() && dq.peekFirst().ind < i-L+1) dq.pollFirst();
			}
			
//			System.out.println(dq);
			
			sb.append(dq.peekFirst().num).append(' ');
			
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static class Node implements Comparable<Node>{
		int num;
		int ind;
		public Node(int num, int ind) {
			super();
			this.num = num;
			this.ind = ind;
		}
		@Override
		public int compareTo(Node o) {
			if(this.num == o.num) return  o.ind-this.ind;
			return this.num - o.num;
		}
		@Override
		public String toString() {
			return "[" + num + ", " + ind + "]";
		}
	}

}
