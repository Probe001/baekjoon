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