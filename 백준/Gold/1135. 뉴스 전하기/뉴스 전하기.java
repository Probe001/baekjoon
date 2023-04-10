/**
## 문제 요약

1. 모든 직원은 정확히 한명의 직속 상사. 트리구조
2. 루트는 민식이
3. 민식이는 자기 직속 부하에게 한번에 한 사람씩 전화를 함
4. 각 부하는 그 밑의 직속에게 한번에 한 사람씩 전화를 검
5. 모든 직원이 뉴스를 들을 때까지 계속됨
6. 전화는 정확히 1분 걸림
7. 모든 직원이 소식을 듣는 데 걸리는 시간의 최솟값을 구하라
8. 민식의 번호는 0, 사원은 1부터 시작

## 입력 및 제약조건

1. 첫째 줄 : 직원 수 N
1 <= N <= 50

2. 둘째 줄 : 0번부터 그들의 상사 번호 ( 오민식(0번)은 -1 )

시간 제한 : 2초
메모리 제한 : 128MB

## 출력

1. 모든 소식을 전하는 데 걸리는 시간의 최솟값

## 문제 풀이 - 트리, 그리디, BFS

1. 가장 탐색을 많이 해야하는 서브 트리부터 탐색해주는 것이 유리하다.
2. dp[i] : i 이하에 존재하는 탐색 단계의 수
3. getTime(i) : i 의 하위 노드에 뉴스를 전달하는 최소 시간
- dp[i] = 1 + getTime(i)
- 하위 노드들을 dp[i]를 기준으로 역순정렬한다.
- 하위 노드들의 dp[i]는 1 + getTime()으로 구한다.
- 각 하위 노드들을 돌며 하위 노드들의 dp값+1 중 최대를 구한다.
- 단, 하위 노드들을 돌면서도 time이 증가하므로 time값을 더해준다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	// 입력용
	static int N;
	static int[] myBoss; // 직원의 상사 번호 배열
	
	//풀이용
	static ArrayList<Integer>[] childs;
	static int[] dp;	// dp[i] : i 번 노드의 최대 탐색단계 수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		N = Integer.parseInt(br.readLine());
		myBoss = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		//풀이
		dp = new int[N];
		childs = new ArrayList[N];
		for(int i=0; i<N; i++) {
			childs[i] = new ArrayList<>();
		}
		for(int i=1; i<N; i++) {
			childs[myBoss[i]].add(i);
		}
		
		System.out.println(news(0));
		
		br.close();
	}
	
	static int news(int ind) {
		for(Integer child : childs[ind]) {
			dp[child] = 1 + news(child);
		}
		Collections.sort(childs[ind], (a, b) -> dp[b]-dp[a]);
		int tmp = 0;
		for(int time=0; time<childs[ind].size(); time++) {
			tmp = Math.max(tmp, time+dp[childs[ind].get(time)]);
		}
		return tmp;
	}
	
}