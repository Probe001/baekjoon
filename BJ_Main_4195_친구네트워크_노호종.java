package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BJ_Main_4195_친구네트워크_노호종 {
	static int F;
	static final int friends = 200000;
	
	static int[] parents; // 부모 노드
	static HashMap<String, Integer> person;
	static int[] unionSize;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc <= T; tc++) {
			F = Integer.parseInt(br.readLine());
			person = new HashMap<>();
			parents = new int[friends];
			unionSize = new int[friends];
			makeSet();
			int nextInd = 0;
			for(int i=0; i<F; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				String a = st.nextToken();
				String b = st.nextToken();
				if(person.get(a) == null) person.put(a, nextInd++);
				if(person.get(b) == null) person.put(b, nextInd++);
				int indA = person.get(a);
				int indB = person.get(b);
				unionSet(indA, indB);
//				System.out.println(person);
//				System.out.println(Arrays.toString(parents));
//				System.out.println(Arrays.toString(unionSize));
				sb.append(unionSize[findSet(indA)]).append('\n');
			}
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void makeSet() {
		for(int i=0; i<friends; i++) {
			parents[i] = i;
			unionSize[i] = 1;
		}
	}
	
	static int findSet(int a) {
		if(a == parents[a]) return a;
		
		return parents[a] = findSet(parents[a]);
	}
	
	static boolean unionSet(int a, int b) {
		int rootA = findSet(a);
		int rootB = findSet(b);
		if (rootA == rootB) return false;
		
		unionSize[rootA] += unionSize[rootB];
		unionSize[rootB] = 0;
		parents[rootB] = rootA;
		return true;
	}

}
