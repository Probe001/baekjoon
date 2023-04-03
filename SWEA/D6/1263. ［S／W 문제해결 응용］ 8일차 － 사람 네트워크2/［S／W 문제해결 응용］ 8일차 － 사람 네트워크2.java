/**
 * ## 문제 요약

1. 누가 가장 영향력이 있는 사람인가?
2. N은 입력 사람 네트워크 그래프의 노드 수
3. CC는 한 사용자가 다른 모든 사람에 대해 얼마나 가까운가
4. CC(i)는 i번째 사용자와 다른 모든 사용자까지의 거리의 총합

## 입력 및 제약 사항

1. 첫번째 줄 : 테스트케이스의 수 T

2. TC 1번 줄에 모든 정보가 주어진다.
사람 수인 N이 0번재에 주어지고 인접행렬이 행 우선으로 주어짐

- 자기 자신을 연결하는 간선은 없다.
- 모든 네트워크는 하나의 그래프다.
- 최대 사용자 수는 1000 이하

테스트 케이스는 아래 그룹으로 이뤄져있다.
그룹 1 싸이클이 없고 N <= 10 인 경우
그룹 2 싸이클이 없고 10 < N <= 100 인경우
그룹 3 싸이클이 있고 N<= 10
그룹 4 싸이클이 있고10 < N <= 100
그룹 5 모든 경우가 존재하고 100 < N <= 1000

## 출력

1. 첫번째 줄 : #[TC번호] [CC값 중 최솟값]

## 문제 풀이

0. 입력을 받으면서 dist 배열 초기화
1. 플로이드 워샬 알고리즘 도입
	1. dist[][]배열 초기화 ((i,i)는 0, 바로 갈 수 있으면 1, 없으면 INF)
	2. 3중 for문을 돌며 dist를 갱신
2. dist의 총합을 구성하며 최소값을 찾는다.
3. 최소값 출력

0 1 1 2 2
1 0 1 1 1
1 1 0 2 1
2 1 2 0 2
2 1 1 2 0 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	static final int INF = Integer.MAX_VALUE;
	// 입력용
	static int N;
	
	// 풀이용
	static long[][] dist;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream(new File("input.txt")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		StringBuilder sb = new StringBuilder();
		for(int tc = 1; tc<= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			// 풀이
			dist = new long[N][N];
			for(int x=0; x<N; x++) {
				for(int y=0; y<N; y++) {
					int now = Integer.parseInt(st.nextToken());
					if(x == y) dist[x][y] = 0;
					else if(now == 1) dist[x][y] = now;
					else dist[x][y] = INF;
				}
			}
//			print();
			for(int g=0; g<N; g++) { // 경유지
				for(int x=0; x<N; x++) {
					for(int y=0; y<N; y++) {
						dist[x][y] = Math.min(dist[x][y], dist[x][g]+dist[g][y]);
					}
				}
//				print();
			}
			int CC = INF;
			for(int x=0; x<N; x++) {
				int sum = 0;
				for(int y=0; y<N; y++) {
					sum += dist[x][y];
				}
				CC = Math.min(CC, sum);
//				System.out.println(sum);
			}
			sb.append('#').append(tc).append(' ').append(CC).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(dist[x]));
		}
		System.out.println();
	}

}