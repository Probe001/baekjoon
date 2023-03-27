package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class BJ_Main_17281_야구_노호종 {
	// 입력용
	static int N; // 총 게임수
	
	// 풀이용
	static int[][] scores; // 선수공격 결과 row: 이닝 번호, col : 선수 번호
	static int[] ts;
	static Deque<Integer> gamets; // 게임에 쓰일 타순
	static int res; // 정답

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		scores = new int[N][];
		for(int i=0; i<N; i++) {
			scores[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		ts = new int[9];
		res = 0;
		gamets = new ArrayDeque();
		permutation(0, 1<<8);
		System.out.println(res);
		br.close();
	}
	
	static void permutation(int k, int flag) {
		if(k == 9) { // 다 정해진 경우 게임해야됨
			gamets.clear();
			for(int i=0; i<9; i++) {
				gamets.offer(ts[i]);
			}
//			System.out.println(Arrays.toString(ts));
			game();
			return;
		}
		if(k == 3) {
			permutation(k+1, flag);
			return;
		}
		for(int i=1; i<9; i++) {
			if((flag & (1<<(i-1))) != 0) continue;
			ts[k] = i;
			permutation(k+1, (flag | (1<<(i-1))));
		}
	}
	
	static void game() {
		int totalScore = 0;
		for(int ining = 0; ining < N; ining++) { // 이닝
			int outs = 0; // 아웃 개수
			int one=-1, two=-1, three=-1;
			while(outs < 3) {
				int np = gamets.pollFirst(); // 현재 플레이어
				int result = scores[ining][np];
				// 결과. 0: 아웃, 1: 안타, 2: 2루타, 3: 3루타, 4: 홈런
				switch(result) {
				case 0:
					outs++;
					break;
				case 1:
					if(three != -1) totalScore++;
					three = two;
					two = one;
					one = np;
					break;
				case 2:
					if(three != -1) totalScore++;
					if(two != -1) totalScore++;
					three = one;
					two = np;
					one = -1;
					break;
				case 3:
					if(three != -1) totalScore++;
					if(two != -1) totalScore++;
					if(one != -1) totalScore++;
					three = np;
					two = -1;
					one = -1;
					break;
				case 4:
					if(three != -1) totalScore++;
					if(two != -1) totalScore++;
					if(one != -1) totalScore++;
					totalScore++;
					three = -1;
					two = -1;
					one = -1;
					break;
				}
				gamets.offerLast(np); // 다시 대기
			}
		}
		res = Math.max(res, totalScore);
	}

}
