import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	// 입력용
	static int N, M; // 가로길이, 세로길이
	static int[][] board;
	// 풀이용
	// BFS용
	static int islands; // 섬의 개수
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}}; // 방향벡터
	static boolean[][] vis; // BFS용
	static class Point{ // 좌표 걕체
		int x, y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	// MST용
	static class Edge implements Comparable<Edge>{
		int st, en; // 시작, 끝점
		int weight; // 다리 길이
		
		public Edge(int st, int en, int weight) {
			super();
			this.st = st;
			this.en = en;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
		}

		@Override
		public String toString() {
			return "Edge [st=" + st + ", en=" + en + ", weight=" + weight + "]";
		}
		
	}
	static PriorityQueue<Edge> pq;
	static int[] island;

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][];
		for(int i=0; i<N; i++) {
			board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		}
		// 풀이
		// 섬 구분하기
		islands = 0;
		vis = new boolean[N][M];
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				if(board[x][y] != 0 && !vis[x][y]) bfs(x, y);
			}
		}
//		print();
		// 다리 후보군 넣기
		pq = new PriorityQueue<>(); // 간선리스트
		horBridge();
		verBridge();
//		while(!pq.isEmpty()) {
//			System.out.println(pq.poll());
//		}
		
		// 크루스칼 알고리즘
		island = new int[islands+1];
		makeSet();
		int totalWeight = 0, unions = islands;
		while(!pq.isEmpty()) {
			Edge now = pq.poll();
			if(unionSet(now.st, now.en)) {
				totalWeight += now.weight;
				unions--;
			}
		}
//		System.out.println(unions);
//		System.out.println(Arrays.toString(island));
		if(unions == 1) {
			System.out.println(totalWeight);
		} else {
			System.out.println(-1);
		}
		
		br.close();
	}
	static void print() {
		for(int x=0; x<N; x++) {
			System.out.println(Arrays.toString(board[x]));
		}
		System.out.println();
	}
	
	static void makeSet() {
		for(int i=0; i<=islands; i++) {
			island[i] = i;
		}
	}
	static int findSet(int a) {
		if(a == island[a]) return a;
		
		return island[a] = findSet(island[a]);
	}
	static boolean unionSet(int a, int b) {
		int rootA = findSet(a);
		int rootB = findSet(b);
		if(rootA == rootB) return false;
		
		island[rootB] = rootA;
		return true;
	}
	
	static void bfs(int x, int y) {
		islands++;
		Queue<Point> que = new ArrayDeque<>();
		board[x][y] = islands;
		vis[x][y] = true;
		que.offer(new Point(x, y));
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x+dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || N <= nx || ny < 0 || M <= ny) continue;
				if(board[nx][ny] == 0) continue;
				if(vis[nx][ny]) continue;
				board[nx][ny] = islands;
				vis[nx][ny] = true;
				que.offer(new Point(nx, ny));
			}
		}
	}
	
	static void horBridge() {
		for(int x=0; x<N; x++) { // 가로 후보군
			int before = board[x][0];
			int dist = 0;
			for(int y=1; y<M; y++) {
				int now = board[x][y];
				if(now != 0) { // 현재 육지고
					if (now != before) { // 이전 값이 지금과 다르다
						if(before != 0 && dist > 1) { // 다리 길이가 2 이상일 때
							pq.offer(new Edge(before, now, dist)); // 간선 후보군
						}
						dist = 0; // 다리 길이 0으로 초기화
						before = now;
					}
					else{
						dist = 0;
					}
				} else { // 현재 바다라면
					dist++;
				}
			}
		}
	}
	static void verBridge() {
		for(int y=0; y<M; y++) { // 세로 후보군
			int before = board[0][y];
			int dist = 0;
			for(int x=1; x<N; x++) {
				int now = board[x][y];
				if(now != 0) { // 현재 육지고
					if (now != before) { // 이전 값이 지금과 다르다
						if(before != 0 && dist > 1) { // 다리 길이가 2 이상일 때
							pq.offer(new Edge(before, now, dist)); // 간선 후보군
						}
						dist = 0; // 다리 길이 0으로 초기화
						before = now;
					}
					else {
						dist = 0;
					}
				}
				else { // 현재 바다라면
					dist++;
				}
			}
		}
	}

}