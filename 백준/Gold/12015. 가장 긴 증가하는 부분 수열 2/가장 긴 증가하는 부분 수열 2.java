/**
 * ## 문제 요약

1. 최장 증가 부분 수열의 길이를 출력하라.

## 입력 및 제약조건

1. 1번 줄 : 수열의 길이 N
1 <= N <= 1000

2. 2번 줄 : 수열의 원소 N개
1 <= 원소 <= Integer.MAV_VALUE

## 출력

1. 각 줄당 [LIS 길이] 출력

## 문제 풀이

1. 수열의 원소가 int의 맥스이므로 C[k]를 생각한다.
2. C[k]는 C[k]가 마지막으로 들어가는 LIS로, k가 길이가 된다.
3. 즉 수열이 {1, 3, 2, 5, 4, 7}일 때, 다음과 같이 움직인다
	1) 1
	2) 1, 3
	3) 1, 2
	4) 1, 2, 5
	5) 1, 2, 4
	6) 1, 2, 4, 7
4. 하지만 C가 LIS를 나타내지는 아니한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	// 입력용
		static int N;
		static int[] A;
		// 풀이용
		static List<Integer> c;
		
		public static void main(String[] args) throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			StringBuilder sb = new StringBuilder();
			N = Integer.parseInt(br.readLine());
			A = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			// 풀이
			c = new ArrayList<>();
			int lastInd = -1;
			for(int now:A) {
				if(c.size() == 0 || c.get(lastInd) < now) {
					c.add(now);
					lastInd++;
				} else {
					binarySearch(now);
				}
//					System.out.println(c);
			}
			System.out.println(lastInd+1);
			br.close();
		}
		static void binarySearch(int now) { // now보다 작은 값의 바로 다음 인덱스를 찾아야 한다.
			int st = 0, en = c.size();
			while(st < en) { // 같은 값이면 넣을 필요가 없긴 하다.
				int mid = st + (en-st)/2;
				if(c.get(mid)<now) st = mid+1;
				else if(c.get(mid) > now) en = mid;
				else return;
			}
			c.set(st, now);
		}
}