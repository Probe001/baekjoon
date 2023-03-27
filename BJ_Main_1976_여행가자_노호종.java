package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_Main_1976_여행가자_노호종 {
	static int N, M;
	
	static int[] parents;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		parents = new int[N];
		makeSet();
		
		StringTokenizer st;
		
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<N; y++) {
				int now = Integer.parseInt(st.nextToken());
				if(now == 1) {
					unionSet(x, y);
				}
			}
		}
		
		boolean isOk = true;
		st = new StringTokenizer(br.readLine());
		int before = Integer.parseInt(st.nextToken());
		int rootA = findSet(before-1);
		while(st.hasMoreTokens()) {
			int now = Integer.parseInt(st.nextToken());
			int rootB = findSet(now-1);
			if(rootA != rootB) {
				isOk = false;
				break;
			}
		}
		System.out.println(isOk ? "YES" : "NO");
		
		br.close();
	}
	
	static void makeSet() {
		for(int i=0; i<N; i++) {
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
