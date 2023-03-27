package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_Main_1766_문제집_노호종 {
	static int N, M; // 문제의 수, 정보의 개수
	
	static class Node{
		int num;
		Node next;
		
		public Node(int num, Node next) {
			super();
			this.num = num;
			this.next = next;
		}
	}
	
	static Node[] graph;  // 그래프
	static int[] incomes; // 진입 간선의 개수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new Node[N+1];
		incomes = new int[N+1];
		for(int i=0; i<M;i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph[a] = new Node(b, graph[a]); // 연결리스트 구현
			incomes[b]++; // 진입 간선
		}
		ArrayList<Integer> res = topologySort();
		StringBuilder sb = new StringBuilder();
		for(int now : res) {
			sb.append(now).append(' ');
		}
		System.out.println(sb);
		
		br.close();
	}

	static ArrayList<Integer> topologySort(){
		ArrayList<Integer> result = new ArrayList<>(); // 결과 리스트
		PriorityQueue<Integer> pq = new PriorityQueue<>(); // 다음 후보군
		for(int i=1; i<=N; i++) {
			if(incomes[i] == 0) pq.offer(i); // 진입 간선이 없는 애들
		}
		while(!pq.isEmpty()) {
			int now = pq.poll();
			
			result.add(now);
			
			for(Node nxt = graph[now] ; nxt != null ; nxt = nxt.next) {
				if(--incomes[nxt.num] == 0) pq.offer(nxt.num);
			}
		}
		return result;
	}
}
