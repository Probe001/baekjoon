/**
 * ## 문제 요약

1. 평면 상 N개의 점
2. 점들의 집합 P
3. 벡터매칭은 벡터의 집합
4. 모든 벡터는 P의 한 점에서 또 다른 점으로 끝남
5. 모든 점은 한 번 씩 쓰여야 한다.
6. 벡터의 개수는 P의 점의 절반
7. 평면 상의 점이 주어질 때, 벡터 합의 길이의 최솟값을 구하라

## 입력 및 제약 조건

1. 첫번째 줄 : 테스트 케이스 개수 T
2. TC 1번 줄 : 점의 수 N
N <= 20, 짝수
3. TC N개 줄 : 점의 좌표
-100000<= 좌표 <= 100000
모든 점은 서로 다르다.

## 출력

1. TC마다 정답을 double로 출력 ( 오차는 0.000001까지 허용 )

## 문제 풀이 - DFS

1. 벡터는 다음과 같이 표시된다.
    
    $V_{12} = ((p1.x-p2.x),(p1.y-p2.y))$
    
2. 같은 점에서 나올 수 있는 벡터는 2개다.
3. 벡터의 합은 다음과 같다.
    
    $V_{12} + V_{34} = [(V_{12}.x+V_{34}.x),(V_{12}.y+V_{34}.y)]$
    
    $=[(p_1.x-p_2.x)+(p_3.x-p_4.x),(p_1.y-p_2.y)+(p_3.y-p_4.y)]$
    
    $= [(p_1.x-p_2.x+p_3.x-p_4.x),(p_1.y-p_2.y+p_3.y-p_4.y)]$
    
4. N개의 점이 있을 때, 벡터가 N/2개가 되어야 하고 모든 점이 사용돼야 하므로, 점이 두 번 이상 사용되는 일은 발생하지 않는다.
5. 즉, N개의 점 중 절반을 선택해 더하고, 나머지를 빼주었을 때 완성되는 벡터의 길이를 보면 된다.

### 시간복잡도

$O(N) = _NC_{N/2}=_{20}C_{10}=184,756$
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N;			// 점의 개수
	static Point[] P;		// 점
	// 풀이용
	static double res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=T; tc++) {
			N = Integer.parseInt(br.readLine());
			P = new Point[N];
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				P[i] = new Point(a, b);
			}
			// 풀이
			res = Double.MAX_VALUE;
			combination(0, 0, 0);
			System.out.println(res);
		}
		br.close();
	}
	static void combination(int k, int ind, int flag) {
		if(k == N/2) {
			// 벡터 구하기
			int vx=0, vy=0;
			for(int i=0; i<N; i++) {
				if((flag & (1<<i)) != 0) { // +인 경우
					vx += P[i].x;
					vy += P[i].y;
				} else {	// -인 경우
					vx -= P[i].x;
					vy -= P[i].y;
				}
			}
			double tmp = Math.abs(Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2)));
			res = Math.min(tmp, res);
			return;
		}
		for(int i=ind; i<N; i++) {
			combination(k+1, i+1, flag | (1<<i));
		}
	}
	
	static class Point{
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

}