package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_14888_연산자끼워넣기_노호종 {
	/* 문제 요약
	 * 연산자 우선순위는 고려X 왼쪽에서 오른쪽으로 진행
	 * 
	 * 연산자 카드로 숫자 사이에 연산자를 계산.
	 * -> 순열? 시간초과남.
	 * 그러면 어떻게 해야할까? 바로 중복을 제거해야됨
	 * 이거 보면 연산자가 중복되고있음
	 * 같은 자리에 같은 연산자가 들어가면 안되므로 vis 배열을 하나 더 둬서 중복제거를 해봄
	 * 
	 * 결과가 최대가 되는 수식과 최소가 되는 수식을 찾고 두 값의 차이를 출력.
	 * 
	 * 제약 사항
	 * 3 <= N <= 12
	 * 연산자 카드 개수: N-1
	 * 게임판 숫자 : 1 ~ 9
	 * 소수점 이하는 버린다.
	 * 연산중의 값은 오버플로우가 일어나지 않음을 보장.
	 * 
	 * 시간복잡도: 11! -> 3300만 언저리 
	 * 그렇지만 backtracking으로 내려서 괜찮아짐.
	 */
	static int N, M;
	static int[] src; // 수식에 사용될 숫자
	static ArrayList<Integer> oper; // 연산자
	// 1 : +, 2 : -, 3 : *, 4 : /
	static int maxNum, minNum;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream(new File("input.txt")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		N = Integer.parseInt(br.readLine());
		src = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		oper = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=1; i<=4; i++) {
			int num = Integer.parseInt(st.nextToken());
			for(int j=0; j<num; j++) {
				oper.add(i);
			}
		}
		
		maxNum = Integer.MIN_VALUE;
		minNum = Integer.MAX_VALUE;
		
		permutation(0, 1<<(N-1), src[0]);

		System.out.println(maxNum);
		System.out.println(minNum);
		
		br.close();
	}
	
	static void permutation(int k, int flag, int val) {
		if(k==N-1) {
			minNum = Math.min(minNum, val);
			maxNum = Math.max(maxNum, val);
			return;
		}
		boolean[] vis = new boolean[5];
		
		for(int i=0; i<N-1; i++) {
			if((flag & (1<<i)) != 0) continue;
			if(vis[oper.get(i)]) continue;
			vis[oper.get(i)] = true;
			permutation(k+1, (flag | (1<<i)), cal(val, src[k+1], oper.get(i)));
		}
	}
	
	static int cal(int num1, int num2, int oper) {
		switch (oper) {
		case 1:
			return num1 + num2;
		case 2:
			return num1 - num2;
		case 3:
			return num1 * num2;
		case 4:
			return num1 / num2;
		default:
			return -1;
		}
	}

}
