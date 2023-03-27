package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_Main_3197_백조의호수_노호종 {
	// 입력용
	static int R, C;
	static int[][] board;
	
	// 풀이용
//	static int[][] area; // 구역
	static List<Integer> parents; // 서로소집합 루트
	static int swan1, swan2; // 백조의 영역
	static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
	
	static Queue<Point> iceQue; // 녹을 얼음들 위치
	
	static class Point{
		int x, y;
		int beforeNum;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		public Point(int x, int y, int beforeNum) {
			super();
			this.x = x;
			this.y = y;
			this.beforeNum = beforeNum;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		board = new int[R][C];
		// '.' : 0, 'X' : -1, 'L' : -2
		for(int x=0; x<R; x++) {
			char[] tmp = br.readLine().toCharArray();
			for(int y=0; y<C; y++) {
				if(tmp[y] == '.') {	
					board[x][y] = 0;
				} else if(tmp[y] == 'X') {
					board[x][y] = -1;
				} else {
					board[x][y] = -2;
				}
			}
		}
		// 풀이
		// 1. 구역 나누기
		int areaNum = 1;
		parents = new ArrayList<>();
		parents.add(0); // 1-based index용 더미데이터
		iceQue = new ArrayDeque<>();
		for(int x=0; x<R; x++) {
			for(int y=0; y<C; y++) {
				if(board[x][y] == 0) {
					bfs1(x, y, areaNum);// 영역 표시
					parents.add(areaNum++); // 서로소 집합 세팅
				}
			}
		}
		
		int time = 1;
		while(!isMeetSwan()) {
			// 백조가 만나지 못했다면
			// 1. 얼음을 녹인다.
			meltIce();
			// 2. 얼음이 한번 다 녹으면 시간 +1
			time++;
//			print();
		}
		System.out.println(time);
		
		br.close();
	}
	
	static int findSet(int a) { // 루트 요소 반환
		if(a == parents.get(a)) return a;
		parents.set(a, findSet(parents.get(a))); // 루트 요소 저장.
		return parents.get(a);
	}
	
	static boolean unionSet(int a, int b) { // 유니온 파인드
		int rootA = findSet(a);
		int rootB = findSet(b);
		if(rootA == rootB) return false;
		
		parents.set(rootB, rootA);
		return true;
	}
	
	static boolean isMeetSwan() { // 백조 두마리가 만났는가?
		int root1 = findSet(swan1);
		int root2 = findSet(swan2);
		return root1 == root2;
	}
	
	static boolean bfs1(int x, int y, int areaNum) { // 초기 영역 BFS
		boolean isSwan = false; // 현재 영역에 백조가 있는지?
		Queue<Point> que = new ArrayDeque<>();
		if(board[x][y] == -2) isSwan = true;
		board[x][y] = areaNum;
		que.offer(new Point(x, y));
		while(!que.isEmpty()) {
			Point now = que.poll();
			for(int i=0; i<4; i++) {
				int nx = now.x +dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || R <= nx || ny < 0 || C <= ny) continue;
				if(board[nx][ny] > 0 || board[nx][ny] < -2) continue; // 이미 방문한 곳이면 넘어가
				if(board[nx][ny] == -1) { // 주변이 얼음인 경우
					// 해당 부분을 녹이고, 큐에 넣어준다.
					board[nx][ny] = areaNum;
//					for(int k=0; k<4; k++) {
//						int nnx = nx+dxy[k][0], nny = ny+dxy[k][1];
//						if(nnx < 0 || R <= nnx || nny < 0 || C <= nny) continue;
//						if(board[nnx][nny] <= 0 || board[nnx][nny] == board[nx][ny]) continue;
//						unionSet(board[nnx][nny], board[nx][ny]);
//					}
					iceQue.offer(new Point(nx, ny, areaNum));
				}
				else { // 주변이 얼음이 아니고 방문도 안한곳이면?
					if(board[nx][ny] == -2) {
						if(swan1 == 0) swan1 = areaNum;
						else swan2 = areaNum;
					}
					que.offer(new Point(nx, ny));
					board[nx][ny] = areaNum;
				}
			}
		}
		return isSwan;
	}
	
	static void meltIce() throws InterruptedException {
		int size = iceQue.size();
		for(int s=0; s<size; s++) {
			Point now = iceQue.poll();
//			board[now.x][now.y] = now.beforeNum;
			// 현재 board[now.x][now.y] 에는 areaNum이 저장돼있다.
			// 이미 처음부터 접근 가능한 영역에는 영역번호를 부여했음
			// 얼음일경우 해당부분엔 0, 구역일 경우 해당 부분에 구역번호.
			for(int i=0; i<4; i++) {
				int nx = now.x + dxy[i][0], ny = now.y + dxy[i][1];
				if(nx < 0 || R <= nx || ny < 0 || C <= ny) continue;
//				System.out.printf("%s %d %d\n",now, nx, ny);
//				System.out.println(board[now.x][now.y]+" "+ board[nx][ny]);
//				print();
				if(board[nx][ny] == now.beforeNum) continue;
				if(board[nx][ny] == -1) {
					// 얼음인경우
					// 녹아 없어질 큐에 넣어준다.
					board[nx][ny] = board[now.x][now.y];
					iceQue.offer(new Point(nx, ny, now.beforeNum));
					for(int k=0; k<4; k++) {
						int nnx = nx+dxy[k][0], nny = ny+dxy[k][1];
						if(nnx < 0 || R <= nnx || nny < 0 || C <= nny) continue;
						if(board[nnx][nny] <= 0 || board[nnx][nny] == board[nx][ny]) continue;
						unionSet(board[nnx][nny], board[nx][ny]);
					}
				}else if(board[nx][ny] > 0) {
					unionSet(board[now.x][now.y], board[nx][ny]);
				}
				if(isMeetSwan()) return;
//				System.out.println(parents);
//				Thread.sleep(1000);
//				print();
			}
		}
	}
	
	static void print() {
		for(int x=0; x<R; x++) {
			System.out.println(Arrays.toString(board[x]));
		}
		System.out.println();
	}
}