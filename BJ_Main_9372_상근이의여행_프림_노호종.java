package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_Main_9372_상근이의여행_프림_노호종 {
	static int n, m;
	
	// 크루스칼 알고리즘용
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			int res = 0;
			parents = new int[n];
			makeSet();
			for(int i=0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				if(unionSet(a-1, b-1)) res++;
			}
			sb.append(res).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void makeSet() {
		for(int i=0; i<n; i++) {
			parents[i] = i;
		}
	}
	
	static int findSet(int a) {
		if(a == parents[a]) return a;
		
		return parents[a] = findSet(parents[a]);
	}
	
	static boolean unionSet(int a, int b) {
		int rootA = findSet(a);
		int rootB = findSet(b);
		if(rootA == rootB) return false;
		
		parents[rootB] = rootA;
		return true;
	}

}
