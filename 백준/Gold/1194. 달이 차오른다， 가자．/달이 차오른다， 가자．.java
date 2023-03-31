/**
 * ## 문제 요약

1. 직사각형 모양의 미로 탈출
2. 미로의 모습
	- '.' : 빈칸. 이동가능
	- '#' : 벽. 이동 불가
	- 'a~f': 키. 문을 열 수 있음
	- 'A~F': 문. 대응되는 열쇠로 열림
	- '0' : 민식이의 현재 위치
	- '1' : 출구
3. 미로를 탈출하는데 걸리는 이동횟수의 최솟값은?

## 입력 및 제약조건

1. 첫째 줄 : 세로크기 N, 가로크기 M
1 <= N, M <= 50

2. N 개 줄 : 미로의 모양
같은 타입의 열쇠와 문은 여러개일 수 있다.
문에 대응하는 열쇠가 없을 수도 있다.
'0'은 1개, '1'은 한개 이상 존재한다.
열쇠는 여러번 사용 가능

## 출력

1. 첫째 줄 : 미로를 탈출하기 위한 이동 횟수 최솟값 출력
탈출 불가하면 -1 출력

## 문제 풀이

1. 민식이 객체를 만든다.
```java
static class Minsik{
	int x, y; // 민식이 좌표
	int time; // 민식이가 이동한 시간
	int keys; // 민식이가 가진 열쇠 flag. 1<<6이 최대값이다
}
```
2. 아무 의미없이 왔던 길을 반복할 수 있으므로 vis 배열이 필요하다.
- key가 총 6종류이므로 3차원 ArrayList<Integer> 배열로 만든다.
- vis[x좌표][y좌표][들고있는 key 종류]
key는 flag를 이용한다. 1<<6

3. 민식이를 4방탐색해서 보낸다.
- 해당 영역의 vis를 이분탐색해서 같은 키를 들고있는지 확인한다.
- 같은 키를 들고있다면 안가도 됨
- 방문체크한다.
- 1) 해당 영역이 '.'인 경우
	time+1 된 민식이를 보낸다.
- 2) 해당 영역이 열쇠인 경우
	time+1과 keys | 열쇠 된 민식이를 보낸다.
- 3) 해당 영역이 key인 경우
	& 연산해서 키가 있는 경우만 보낸다.
	현재 자리에 동일하게 방문처리 해준다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	// 입력용
	static int N, M;
	static char[][] board;
	// 풀이용
	static ArrayList<Integer>[][] vis;
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	static class Minsik{
		int x, y; // 민식이 좌표
		int time; // 민식이가 이동한 시간
		int keys; // 민식이가 들고있는 key flag(bit)
		
		public Minsik(int x, int y, int time, int keys) {
			super();
			this.x = x;
			this.y = y;
			this.time = time;
			this.keys = keys;
		}

		@Override
		public String toString() {
			return "Minsik [x=" + x + ", y=" + y + ", time=" + time + ", keys=" + Integer.toBinaryString(keys) + "]";
		}
		
	}
	static Queue<Minsik> que;
	static TreeSet<Integer> res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new char[N][M];
		que = new ArrayDeque<>();
		vis = new ArrayList[N][M];
		for(int x=0; x<N; x++) {
			char[] tmp = br.readLine().toCharArray();
			for(int y=0; y<M; y++) {
				board[x][y] = tmp[y];
				vis[x][y] = new ArrayList<>();
				if(board[x][y] == '0') {
					que.offer(new Minsik(x, y, 0, 1<<6));
					vis[x][y].add(1<<6);
				}
			}
		}
		// 풀이
		res = new TreeSet<>();
		bfs();
		if(res.isEmpty()) System.out.println(-1);
		else System.out.println(res.pollFirst());
		
		br.close();
	}
	static void bfs() {
		while(!que.isEmpty()) {
			Minsik now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(board[nx][ny] == '#') continue;
				if(isVisited(vis[nx][ny], now.keys)) continue;
				vis[nx][ny].add(now.keys);
				if(board[nx][ny] == '.' || board[nx][ny] == '0') {
					// 빈칸인 경우
					que.offer(new Minsik(nx, ny, now.time+1, now.keys));
				} else if('a'<=board[nx][ny] && board[nx][ny] <= 'f') {
					// 열쇠인 경우
					int flag = 1<<(board[nx][ny]-'a');
					vis[nx][ny].add(now.keys|flag);
					que.offer(new Minsik(nx, ny, now.time+1, now.keys | flag));
				} else if('A'<=board[nx][ny] && board[nx][ny] <= 'F') {
					// 문인 경우
					int flag = 1<<(board[nx][ny]-'A');
					if((flag & now.keys)>0) { // 현재 키가 있는 경우
						que.offer(new Minsik(nx, ny, now.time+1, now.keys));
					}
				} else if(board[nx][ny] == '1') {
					// 도착
					res.add(now.time+1);
				}
			}
		}
	}
	static boolean isVisited(ArrayList<Integer> list, int flag) {
		if(list.size() == 0) return false;
		else {
			for(int i=0; i<list.size(); i++) {
				if(list.get(i) == flag) return true;
			}
			return false;
		}
	}
	static void print() {
		for(int x=0; x<N ; x++) {
			for(int y=0; y<N; y++) {
				for(int i=0; i<vis[x][y].size(); i++) {
					System.out.print(Integer.toBinaryString(vis[x][y].get(i)));
					System.out.print(" ");
				}
				System.out.print("|");
			}
			System.out.println();
		}
	}

}