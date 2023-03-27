package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_2252_줄세우기_노호종 {
	static int N, M;
	
	static class Node{
		int num;
		Node next;
		
		public Node(int num, Node next) {
			super();
			this.num = num;
			this.next = next;
		}
	}
	static Node[] graph;
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new Node[N+1];
		parents = new int[N+1];
		for(int i = 0;i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph[a] = new Node(b, graph[a]);
			parents[b]++;
		}
//		for(int i= 1; i<=M; i++) {
//			for(Node n = graph[i]; n != null ; n = n.next) {
//				System.out.print(n.num+" ");
//			}
//			System.out.println();
//		}
		ArrayList<Integer> res = TopologySort();
		for(int now:res) {
			System.out.print(now+" ");
		}
		System.out.println();
		
		br.close();
	}
	
	static ArrayList<Integer> TopologySort() {
		Queue<Integer> que = new ArrayDeque<>();
		ArrayList<Integer> result = new ArrayList<>();
		// 진입 간선이 0인 놈들 que에 넣기
		for(int i=1; i<=N; i++) {
			if(parents[i] == 0) que.offer(i);
		}
		// 정렬 완성하기
		while(!que.isEmpty()) {
			int now = que.poll();
			result.add(now);
			// 이제 하위 노드들 돌며 간선 제거하기
			for(Node next = graph[now]; next != null; next = next.next) {
				if(--parents[next.num] == 0) que.offer(next.num);
			}
		}
		return result;
	}
	

}
