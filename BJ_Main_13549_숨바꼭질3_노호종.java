package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_Main_13549_숨바꼭질3_노호종 {
	// 입력용
	static int N, K;
	// 풀이용
	static boolean[] vis;
	static int res;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		vis = new boolean[Math.max(N, K)*2+1];
//		res = Integer.MAX_VALUE;
		if(N != K) bfs(N);
		System.out.println(res);
//		System.out.println();
		br.close();
	}
	static class Point implements Comparable<Point>{
		int num;
		int time;
		public Point(int num, int time) {
			super();
			this.num = num;
			this.time = time;
		}
		@Override
		public String toString() {
			return "Point [num=" + num + ", time=" + time + "]";
		}
		@Override
		public int compareTo(Point o) {
			// TODO Auto-generated method stub
			return this.time - o.time;
		}
		
	}
	
	static void bfs(int start) {
		int[] minTime = new int[2*Math.max(N, K)+1];
		Arrays.fill(minTime, Integer.MAX_VALUE);
		
		minTime[start] = 0;
		PriorityQueue<Point> que = new PriorityQueue<>();
		que.offer(new Point(start, 0));
		
		while(!que.isEmpty()) {
			Point now = que.poll();
			if(vis[now.num]) continue;
			vis[now.num] = true;
			minTime[now.num] = now.time;
			if(now.num == K) {
				res = now.time;
				return;
			}
			int[] next = {now.num*2, now.num+1, now.num-1}; // 위치
			for(int i=0; i<3; i++) {
				if(next[i] < 0 || next[i] > 2*Math.max(N, K)) continue;
				int nextTime = i == 0 ? now.time : now.time+1;
				if(minTime[next[i]] <= nextTime) continue;
				que.offer(new Point(next[i], nextTime));
			}
		}
	}
}
