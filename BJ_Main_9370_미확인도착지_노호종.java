package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_Main_9370_미확인도착지_노호종 {
	static int n, m, t, s, g, h;
	
	static class Vertex implements Comparable<Vertex>{
		int num;
		int dist;    // 해당 정점까지의 거리
		Vertex next; // 인접리스트 용
		int prevVerNum; // 다익스트라에서 쓰일 것
		int isPassed; // 해당 간선을 지나는 경로인지?
		
		public Vertex(int num, int dist, int prevVerNum, int isPassed) { // 다익스트라용
			this.num = num;
			this.dist = dist;
			this.prevVerNum = prevVerNum;
			this.isPassed = isPassed;
		}
		
		public Vertex(int num, int dist, Vertex next) { // 인접리스트용
			super();
			this.num = num;
			this.dist = dist;
			this.next = next;
		}

		@Override
		public int compareTo(Vertex o) {
			if(this.dist == o.dist) return o.isPassed - this.isPassed; // 가급적 목표 간선을 지나도록
			return Integer.compare(this.dist, o.dist);
		}
	}
	
	static Vertex[] graph;
	static boolean[] isPassed;
	static int[] minDist;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {			
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			s = Integer.parseInt(st.nextToken());
			g = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			graph = new Vertex[n+1];
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				graph[a] = new Vertex(b, d, graph[a]);
				graph[b] = new Vertex(a, d, graph[b]);
			}
			isPassed = new boolean[n+1];
			dijkstra(s);
			PriorityQueue<Integer> pq = new PriorityQueue<>();
			for(int i=0; i<t; i++) {
				int target = Integer.parseInt(br.readLine());
				if(isPassed[target]) pq.offer(target);
			}
			while(!pq.isEmpty()) {				
				sb.append(pq.poll()).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	static void dijkstra(int start) {
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		boolean[] vis = new boolean[n+1];
		minDist = new int[n+1];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		minDist[start] = 0;
		pq.offer(new Vertex(start, 0, 0, 0));
		while(!pq.isEmpty()) {
			Vertex now = pq.poll();
			
			if(vis[now.num]) continue;
			// 지금이 최소거리라는 의미
			minDist[now.num] = now.dist;
			if(now.isPassed == 1) isPassed[now.num] = true; // 목표 간선을 지나는  경로라면 true.
			vis[now.num] = true;
			
			for(Vertex next = graph[now.num]; next != null; next = next.next) {
				if(minDist[next.num] < minDist[now.num]+next.dist) continue;
				int isPassed = now.isPassed;
				if((g == now.num && h == next.num) || (h == now.num && g == next.num)) isPassed = 1;
				pq.offer(new Vertex(next.num, minDist[now.num]+next.dist, now.num, isPassed));
			}
		}
	}

}
