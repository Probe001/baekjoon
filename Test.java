package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

import algorithm.BJ_Main_3197_백조의호수_노호종.Point;

public class Test {

	// 입력용
		static int R, C;
		static char[][] board;
		// 풀이용
		static Point[][] parents;  // 부모요소 배열
		static Point swan1, swan2; // 백조1, 백조2
		
		static Point findSet(int x, int y) {
			if(parents[x][y].hashCode() == Objects.hash(x, y)) return parents[x][y];
			
			return parents[x][y] = findSet(parents[x][y].x, parents[x][y].y);
		}
		
		static boolean unionSet(int x1, int y1, int x2, int y2) {
			Point rA = findSet(x1, y1);
			Point rB = findSet(x2, y2);
			if(rA.equals(rB)) return false;
			
			parents[rB.x][rB.y] = rA;
			return true;
		}
		
		static class Point {
			int x, y;

			public Point(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			
			@Override
			public boolean equals(Object obj) {
				if(obj instanceof Point) {				
					Point pnt = (Point)obj;
					if(pnt.x == this.x && pnt.y == this.y) {					
						return true;
					}
				}
				return false;
			}
			@Override
			public int hashCode() {
				// TODO Auto-generated method stub
				return Objects.hash(x, y);
			}

			@Override
			public String toString() {
				return "Point [x=" + x + ", y=" + y + "]";
			}
		}
		
		// BFS용
		static boolean[][] vis;
		static int[][] dxy = {{0,1},{1,0},{0,-1},{-1,0}};
		
		public static void main(String[] args) throws IOException, InterruptedException {
			// 입력
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			board = new char[R][];
			
			// 풀이
			parents = new Point[R][C];
			for(int x=0; x<R; x++) {
				board[x] = br.readLine().toCharArray();
				for(int y=0; y<C; y++) {
					parents[x][y] = new Point(x, y);
					if(board[x][y] == 'L') { // 백조 위치 파악
						if(swan1 == null) swan1 = new Point(x, y);
						else swan2 = parents[x][y];
					}
				}
			}
			print();
			unionSet(0,0,1,1);
			unionSet(1,1,2,1);
			print();
			
			br.close();
		}
		static void print() {
			for(int x=0; x<R; x++) {
				System.out.println(Arrays.toString(parents[x]));
			}
			System.out.println();
		}
}
