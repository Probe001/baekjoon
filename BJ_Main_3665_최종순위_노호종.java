package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_3665_최종순위_노호종 {
	static int n, m;
	static int[] lastYear;
	
	static ArrayList<Integer>[] graph;
	static int[] parents;
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {
			n = Integer.parseInt(br.readLine());
			
			lastYear = new int[n];
			graph = new ArrayList[n+1];
			parents = new int[n+1];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<n; i++) {
				lastYear[i] = Integer.parseInt(st.nextToken());
				graph[i+1] = new ArrayList<>();
			}
			// 모든 간선 그려주기
			for(int x=0; x<n; x++) {
				for(int y=x+1; y<n; y++) {
					graph[lastYear[x]].add(lastYear[y]);
					parents[lastYear[y]]++;
				}
//				System.out.println(graph[lastYear[x]]);
			}
//			System.out.println(Arrays.toString(parents));
//			print();
			
			m = Integer.parseInt(br.readLine());
			// 순위가 바뀌었으므로 그래프 방향 재정립
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if(graph[b].contains(Integer.valueOf(a))) {
					graph[b].remove(Integer.valueOf(a));
					parents[b]++;
					graph[a].add(b);
					parents[a]--;
				} else {
					graph[a].remove(Integer.valueOf(b));
					parents[a]++;
					graph[b].add(a);
					parents[b]--;
				}
			}
//			print();
			
			// 위상정렬
			ArrayList<Integer> res = topologySort();
			if(res.size() != n) {
				sb.append("IMPOSSIBLE\n");
			} else {
				for(int i : res) {
					sb.append(i).append(' ');
				}
				sb.append('\n');
			}
		}
		System.out.println(sb);
		
		br.close();
	}
	static ArrayList<Integer> topologySort(){
		ArrayList<Integer> result = new ArrayList<>();
		Queue<Integer> que = new ArrayDeque<>();
		// 진입 간선이 0인 것 큐에 추가
		for(int i=1;i<=n;i++) {
			if(parents[i] == 0) que.offer(i);
		}
		while(!que.isEmpty()) {
			int now = que.poll();
		
			result.add(now);
			
			for(int next : graph[now]) {
				if(--parents[next] == 0) que.offer(next);
			}
		}
		return result;
	}
	
	static void print() {
		for(int x=0; x<n; x++) {
			System.out.println(graph[lastYear[x]]);
		}
		System.out.println(Arrays.toString(parents));
	}

}
