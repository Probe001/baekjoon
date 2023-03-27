package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_1005_ACMCraft_노호종 {
	static int N, K; 	// 건물 개수, 건설순서 규칙 수
	static int[] D; 	// 건물 당 건설 시간
	static int W; 		// 건설해야 할 건물 번호
	
	static class Node{
		int num;
		Node next;
		
		public Node(int num, Node next) {
			super();
			this.num = num;
			this.next = next;
		}

		@Override
		public String toString() {
			return "Node [num=" + num + ", next=" + next + "]";
		}
	}
	static Node[] src; // 건물 연결현황
	static int[] times; // 해당 건물에 오기까지 걸리는 시간 최대치
	static int[] parents; // 부모 여부

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			D = new int[N+1];
			st = new StringTokenizer(br.readLine());
			for(int i=1; i<=N; i++) D[i] = Integer.parseInt(st.nextToken());
			src = new Node[N+1];
			times = new int[N+1];
			parents = new int[N+1];
			for(int i=0; i<K; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				src[x] = new Node(y, src[x]);
				parents[y]++;
			}
			W = Integer.parseInt(br.readLine());
			topology();
//			System.out.println(Arrays.toString(times));
			sb.append(times[W]+D[W]).append('\n');
		}
		System.out.println(sb);
		br.close();
	}
	
	static void topology() {
		Queue<Integer> que = new ArrayDeque<>();
		for(int i=1; i<=N; i++) {
			if(parents[i] != 0) continue;
			que.offer(i);
		}
		while(!que.isEmpty()) {
			int i = que.poll();
			for(Node next=src[i]; next != null; next= next.next) {
				if(times[next.num] < times[i]+D[i]) {						
					times[next.num] = times[i]+D[i];
				}
				if(--parents[next.num] == 0) que.offer(next.num);
			}
		}
	}
}
