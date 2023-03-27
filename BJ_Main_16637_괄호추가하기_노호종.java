package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_Main_16637_괄호추가하기_노호종 {
	/* 문제 요약
	 * 길이가 N인 수식.
	 * 0~9의 정수 및 연산자 (+, -, X)로 구성
	 * 우선순위가 동일하여 왼쪽에서부터 순서대로 계산.
	 * 단, 괄호 안에 들어있는 식은 먼저 계산해야 됨.
	 * 괄호 안에는 연산자가 하나만 들어있어야 함.
	 * 중첩 괄호문은 사용 불가
	 * 
	 * 수식이 주어질 때 괄호를 적절히 추가해 만들 수 있는 식의 최댓값은?
	 * 
	 * 제약 조건
	 * 1 <= N <= 19
	 * 
	 * 풀이 방식
	 * 1. 그리디: +를 먼저 괄호로 감싸면? => 3 - 4 * 8 + 16 의 경우 8+16이 더해져 더 작아져버림
	 * 2. 부분집합: 각 연산자를 괄호로 감쌀 경우.
	 * 연산자 최대 개수 : 18개. 2^18 = 262144
	 * 괄호로 감싸면 이 다음 연산자가 괄호 안에 있는지 없는지를 체크해야 됨.
	 */
	static int N; // 수식의 길이
	
	static int[] nums; // 피연산자
	static char[] opers; // 연산자
	static boolean[] selected;
	
	static int maxNum; // 최대값

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		String[] st = br.readLine().split("");
		nums = new int[N/2+1]; 
		opers = new char[N/2];
		int ni = 0, oi = 0;
		for(int i=0; i<N; i++) {
			if(i%2 == 0) nums[ni++] = Integer.parseInt(st[i]);
			else opers[oi++] = st[i].charAt(0);
		}
		
		// 부분집합
		selected = new boolean[N/2]; // 각 자리는 expression에서 2*ind+1 에 해당
		maxNum = Integer.MIN_VALUE;
		subset(0);
		
		System.out.println(maxNum);
		
		br.close();
	}
	
	static void subset(int k) {
		if( k >= (N/2)) { // 끝까지 다 결정한 경우
			calc(0, nums[0]); // 계산 시작
			return;
		}
		selected[k] = true;
		subset(k+2); // 연속해서 두개의 연산자가 괄호안에 있을 수는 없다.
		selected[k] = false;
		subset(k+1); 
	}
	
	static void calc(int k, int tmpRes) { // 연산자 기준.
		if(k >= (N/2)) { // 연산이 다 됐다면 최대값 찾기
			maxNum = Math.max(maxNum, tmpRes);
			return;
		}
		// 왼쪽 숫자 : nums[k], 오른쪽 숫자: nums[k+1]
		// 마지막 연산자가 아니고 다음 연산자가 괄호안에 있는 경우
		if (k < (N/2-1) && selected[k+1]) {
			int nextTmp = unitCalc(nums[k+1], nums[k+2], opers[k+1]);
			tmpRes = unitCalc(tmpRes, nextTmp, opers[k]);
			calc(k+2, tmpRes);
		} else { // 그 외에는 그냥 현재 값 연산 후 넘기기
			tmpRes = unitCalc(tmpRes, nums[k+1], opers[k]);
			calc(k+1, tmpRes);
		}
	}
	
	static int unitCalc(int a, int b, char oper) {
		switch(oper) {
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a*b;
		}
		return 0;
	}

}
