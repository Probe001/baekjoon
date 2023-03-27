package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_2117_홈방범서비스_노호종 {
	/* 문제 요약
	 * 운영 비용: 서비스 영역의 면적. K*K + (K-1)*(K-1);
	 * 서비스 영역 : 중심을 기준으로 가로 길이가 2K-1 인 정마름모
	 * 즉, 중심부에서 목표까지의 맨하탄 거리가 K-1 이하인 지점들.
	 * 도시 크기 : N, 하나의 집이 지불하는 비용: M
	 * 한 격자당 한 집. 손해보지 않는 선에서 최대한 많은 집에 서비스 필요
	 * 이익: 현재 포함된 집 수 * M - 운영 비용
	 * 
	 * 제약 조건
	 * 5 <= N <= 20
	 * 1 <= M <= 10
	 * 아마도 서비스 영역의 개수는 1개인 듯.
	 * 도시의 집의 개수는 최대 400개.
	 * 
	 * 접근
	 * 1. 완전 탐색
	 * 1.1 K는 1 부터 N까지
	 * 1.2 각 칸마다 가능한 최대 영역크기를 계산
	 * 
	 * 최대 크기 구하기
	 * 1. 각 칸에서 집들을 순회하면서 거리들을 구해 배열에 저장
	 * 2. k==1 부터 누적합을 더해가며 손익을 계산함.
	 * 3. 손익을 보지 않는 최대 범위를 갱신.
	 * 
	 * 시간 복잡도
	 * 각 격자 * 집 개수 * 영역 크기
	 * 20*20 * 400 * 20 -> 320만
	 */
	static int N, M;
	static ArrayList<Point> homes;
	
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	static int res;
	

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream(new File("input.txt")));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			homes = new ArrayList<>();
			for(int x=0; x<N;x++) {
				st = new StringTokenizer(br.readLine());
				for(int y=0; y<N; y++) {
					int now = Integer.parseInt(st.nextToken());
					if(now==1) homes.add(new Point(x, y));
				}
			}
			// 풀이
			res = 0;
			for(int x=0; x<N; x++) {
				for(int y=0; y<N; y++) {
					func(x,y);
				}
			}
			sb.append('#').append(tc).append(' ').append(res).append('\n');
		}
		System.out.println(sb);
		
		br.close();
	}
	
	static void func(int x, int y) {
		int[] homeNum = new int[N*2]; // 거리당 집의 개수
		
		for(int i = 0; i<homes.size(); i++) {
			Point now = homes.get(i);
			int d = Math.abs(now.x-x) + Math.abs(now.y-y);
			homeNum[d]++;
		} // 거리당 집의 개수 갱신
		
		int accumSum = 0;
		for(int i=0; i<homeNum.length; i++) {
			accumSum += homeNum[i]; // 서비스 받는 집들의 개수
			int plus = accumSum * M; // 이득
			int minus = i*i + (i+1)*(i+1); // 손해
			int total = plus - minus; // 손익
			if(total >= 0) {
				res = Math.max(res, accumSum);
			}
		}
	}

}
