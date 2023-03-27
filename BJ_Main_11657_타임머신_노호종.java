package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_11657_타임머신_노호종 {
	// 입력용
	static int N, M; // 도시의 개수, 버스 노선의 개수
	
	static class Edge{
		int st, en;
		long dist; // 시작, 끝, 거리

		public Edge(int st, int en, int dist) {
			super();
			this.st = st;
			this.en = en;
			this.dist = dist;
		}
		
	}
	static Edge[] edges;
	
	// 풀이용
	static final Long MAX = Long.MAX_VALUE;
	
	static long[] minDist; // 최단 경로

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		edges = new Edge[M];
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			edges[i] = new Edge(a, b, c);
		}
		// 입력 완료
		StringBuilder sb = new StringBuilder();
		if(bellmanford(1)) { // 성공했을 경우에
			for(int i=2; i<=N; i++) {
				sb.append(minDist[i] == MAX? -1 : minDist[i]).append('\n');
			}
		} else sb.append(-1);
		System.out.println(sb);
		
		br.close();
	}
	
	static boolean bellmanford(int start) {
		minDist = new long[N+1];
		Arrays.fill(minDist, MAX);
		minDist[start] = 0;
		// 최단 경로 갱신
		for(int i=0; i<N; i++) {
			for(Edge now: edges) {
				int st = now.st;
				int en = now.en;
				if(minDist[st] == MAX) continue;
				if(minDist[en] <= minDist[st]+now.dist) continue;
				minDist[en] = minDist[st]+now.dist;
				if(i==N-1) return false;
			}
		}
		return true;
	}

}
