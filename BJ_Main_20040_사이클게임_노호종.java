package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_Main_20040_사이클게임_노호종 {
	static int n, m;
	
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		parents = new int[n];
		makeSet();
		int res = 0;
		for(int i=1; i<=m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if(!unionSet(a, b) && res == 0) {
				res = i;
			}
		}
		System.out.println(res);
		
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
