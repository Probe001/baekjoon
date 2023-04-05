/**
 * ## 문제 요약

1. 두개의 문자열 P와 T. P가 T 중간에 몇번, 어느 위치에서 나타내는지 알아내기
2. T의 길이는 n, P의 길이는 m. 일반적으로 n >= m
3. T의 i번째 문자 => T[i]
4. P의 i번째 문자 => P[i]
5. KMP 알고리즘을 사용하여 문제를 풀어라

## 입력 및 제약조건

1. 첫번째 줄 : 문자열 T
2. 두번째 줄 : 문자열 P

1 <= T와 P의 길이 n, m <= 1000000
알파벳 대소문자 및 공백으로 이뤄져있음

## 출력

1. 첫번째 줄 : T 중간에 P가 나타나는 횟수
2. 두번째 줄 : P가 나타나는 위치를 차례대로 출력

## 문제 풀이 - KMP 알고리즘

1. 먼저 주어진 패턴 P에 대해 Pi 배열을 만든다.
	1. pi[0] = 0 이므로 패턴 P의 1번째 인덱스부터 인덱싱한다. ( by i )
	2. 비교할 패턴의 인덱스로는 j로 지정하고 0부터 시작한다.
	3. P[i] != P[j] 일 때 j == 0이라면 i+1, j != 0이라면 j = pi[j-1] 이다.
	4. P[i] == p[j] 라면 pi[i] = ++j 이다.
2. 이제 완성된 pi를 가지고 문자열 T에 대해 매칭을 시작한다.
	1. 문자열 T의 0번째부터 인덱싱한다. ( by i )
	2. 패턴 P 또한 j로 인덱싱한다.
	3. T[i] != P[j] 인 경우 j == 0이라면 i+1, j != 0 이라면 j = pi[j-1]이다.
	4. P[i] == P[j] 인 경우 j == m-1이라면 문자열이 전부 다 맞은 것이다.
	-> 그렇게 되면 i+1을 해주고, j = pi[j]를 해준다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	// 입력용
	static String T, P;
	static int n, m;
	// 풀이용
	static int[] pi;
	static int count;
	static List<Integer> place;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		T = br.readLine();
		P = br.readLine();
		n = T.length();
		m = P.length();
		// 풀이
		pi = new int[m];
		makingPi();
//		System.out.println(Arrays.toString(pi));
		count = 0;
		place = new ArrayList<>();
//		System.out.println(n+" "+m);
		KMP();
		StringBuilder sb = new StringBuilder();
		sb.append(count).append('\n');
		for(int i=0; i<place.size(); i++) {
			sb.append(place.get(i)).append(' ');
		}
		System.out.println(sb);
		
		br.close();
	}
	static void makingPi() {
		int j = 0; // 패턴의 인덱스
		for(int i=1; i<m; i++) { // pi[0] = 0, 비교하는 인덱스
			while(j > 0 && P.charAt(i) != P.charAt(j)) {
				j = pi[j-1];
			}
			if(P.charAt(i) == P.charAt(j)) {
				pi[i] = ++j;
			}
		}
	}
	static void KMP() {
		int j = 0; // 패턴의 인덱스
		for(int i=0; i<n; i++) { // 비교 인덱스
			while(j>0 && T.charAt(i) != P.charAt(j)) {
				j = pi[j-1];
			}
			if(T.charAt(i) == P.charAt(j)) {
				if(j == m-1) {
					count++;
					place.add(i-j+1);
					j = pi[j];
//					System.out.println(i+" "+j);
				}
				else {					
					j++;
				}
			}
		}
		
	}

}