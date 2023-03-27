package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BJ_Main_3954_Brainfuck인터프리터_노호종 {
	// 입력용
	static int sm, sc, si; // 메모리크기, 프로그램코드 크기, 입력 크기
	static char[] coms; // 명령어
	static char[] inputs; // 프로그램 입력
	// 풀이용
	static class Interpreter{
		int[] arr;
		int pointer;
		
		Interpreter(int sm){
			arr = new int[sm];
			pointer = 0;
		}
		
		void plus() {
			if(++arr[pointer] >= 256) arr[pointer] = 0;
		}
		void minus() {
			if(--arr[pointer] < 0) arr[pointer] = 255;
		}
		void left() {
			if(--pointer < 0) pointer = arr.length-1;
		}
		void right() {
			if(++pointer >= arr.length) pointer = 0;
		}
		int getValue() {
			return arr[pointer];
		}
		void save(int a) {
			arr[pointer] = a;
		}
	}
	static class SqBraket{
		char type;
		int ind;
		public SqBraket(char type, int ind) {
			super();
			this.type = type;
			this.ind = ind;
		}
	}
	static int[] pair; // [와 ] 연결하기
	static Interpreter itpr;
	
	static int[] excutedBraket;
	
	static StringBuilder sb; // 정답

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {
			// 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			sm = Integer.parseInt(st.nextToken());
			sc = Integer.parseInt(st.nextToken());
			si = Integer.parseInt(st.nextToken());
			coms = br.readLine().toCharArray();
			inputs = br.readLine().toCharArray();
			// 풀이
			itpr = new Interpreter(sm);
			// [와 ] 짝지어주기
			Stack<SqBraket> stack = new Stack<>();
			pair = new int[sc];
			excutedBraket = new int[sc];
			for(int i=0; i<sc; i++) {
				if(coms[i] == '[') {
					stack.push(new SqBraket('[', i));
				} else if(coms[i] == ']') {
					SqBraket before = stack.pop();
					pair[i] = before.ind; // ]에는 [의 인덱스 저장
					pair[before.ind] = i; // [에는 ]의 인덱스 저장
				}
			}
			// 명령어 실행하기
			excute();
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void excute() {
		int ind = 0; // 명령어 인덱스
		int cnt = 0; // 명령어 실행 횟수
		int nextInput = 0; // 다음 입력 인덱스
		int latestBraket = 0;
		while(ind < sc) {
			switch(coms[ind]) {
			case '-':
				itpr.minus();
				break;
			case '+':
				itpr.plus();
				break;
			case '<':
				itpr.left();
				break;
			case '>':
				itpr.right();
				break;
			case '.':
				itpr.getValue();
				break;
			case ',':
				if(nextInput < si) {
					itpr.save(inputs[nextInput++]);
				} else {
					itpr.save(255);
				}
				break;
			case '[':
				if(itpr.getValue() == 0) {
					ind = pair[ind];
				}
				break;
			case ']':
				if(cnt > 50000000) {
					latestBraket = Math.max(latestBraket, ind);
					excutedBraket[ind]++;
					if(cnt > 100000000) {
						sb.append("Loops ").append(pair[latestBraket]).append(' ').append(latestBraket).append('\n');
						return;
					}
				}
				if(itpr.getValue() != 0) {
					ind = pair[ind];
				}
				break;
			}
			cnt++;
			ind++;
		}
		sb.append("Terminates\n");
		return;
	}

}
