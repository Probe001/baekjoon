package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_Main_4386_별자리만들기_노호종 {
	static int n; // 별의 개수
	
	static class Point{
		double x, y;

		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
	}
	
	static Point[] stars; // 별들
	static double[][] dist;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine());
		stars = new Point[n];
		
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			stars[i] = new Point(x, y);
		}
		
		// 인접행렬 생성
		dist = new double[n][n];
		for(int x=0; x<n; x++) {
			for(int y=0; y<n; y++) {
				if(x != y) {
					Point start = stars[x];
					Point end = stars[y];
					double ver = start.x - end.x;
					double hor = start.y - end.y;
					dist[x][y] = Math.sqrt(Math.pow(ver, 2)+Math.pow(hor, 2));
				}
//				System.out.print(dist[x][y]+" ");
			}
//			System.out.println();
		}
		// 프림 알고리즘
		MST mst = new MST(n);
		mst.makeMST();
		System.out.printf("%.2f", mst.totaldist);
		
		br.close();
	}
	static class Node implements Comparable<Node>{
		int num;
		double dist;
		
		public Node(int num, double dist) {
			super();
			this.num = num;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			// TODO Auto-generated method stub
			return Double.compare(this.dist, o.dist);
		}
	}
	
	static class MST{
		double totaldist;
		double[] distance;
		boolean[] isInMST;
		
		final double INF = Double.MAX_VALUE;
		
		MST(int length){
			totaldist = 0;
			distance = new double[length];
			isInMST = new boolean[length];
			for(int i=0; i<length; i++) {
				distance[i] = INF;
			}
		}
		
		void makeMST(){
			PriorityQueue<Node> pq = new PriorityQueue<>();
			pq.offer(new Node(0, 0));
			while(!pq.isEmpty()) {
				Node now = pq.poll();
				
				if(isInMST[now.num]) continue;
				isInMST[now.num] = true;
				distance[now.num] = now.dist;
				totaldist += now.dist;
				
				// 다음 노드 찾기
				for(int i=0; i<dist[now.num].length; i++) {
					if(isInMST[i]) continue;
					if(dist[now.num][i] >= distance[i]) continue;
					pq.offer(new Node(i, dist[now.num][i]));
				}
			}
		}
	}
	
}
