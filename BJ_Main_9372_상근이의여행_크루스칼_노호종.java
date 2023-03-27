package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_Main_9372_상근이의여행_크루스칼_노호종 {
	static int n, m;
	
	// 프림 알고리즘 용
	static ArrayList<Integer>[] board;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			board = new ArrayList[n+1];
			for(int i=1; i<=n; i++) board[i] = new ArrayList<>(); // 인접리스트
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				board[a].add(b);
				board[b].add(a);
			}
			// 프림 알고리즘
			MST mst = new MST(n);
			mst.makeMST();
			sb.append(mst.getEdges()).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	static class MST{
		boolean[] isInMST;
		int edges; // 총 간선 수
		
		MST(int length){
			isInMST = new boolean[length+1];
			edges= -1;
		}
		
		void makeMST() {
			PriorityQueue<Integer> pq = new PriorityQueue<>();
			pq.offer(1);
			while(!pq.isEmpty()) {
				int now = pq.poll();
				// 현재 뽑은 값이 MST에 있는 값이라면 통과
				if(isInMST[now]) continue;
				
				edges++;
				isInMST[now] = true;
				
				for(Integer elements : board[now]) {
					if(isInMST[elements]) continue;
					pq.offer(elements);
				}
			}
		}
		
		int getEdges() {
			return edges;
		}
		
	}
}
