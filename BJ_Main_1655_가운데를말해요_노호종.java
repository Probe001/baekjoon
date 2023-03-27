package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class BJ_Main_1655_가운데를말해요_노호종 {
	static int N; // 전체 정수의 개수
	
	static class MiddleHeap{
		int root; // 현재 중간값
		PriorityQueue<Integer> rightPQ;
		PriorityQueue<Integer> leftPQ;
		
		MiddleHeap(){
			this.root = Integer.MAX_VALUE;
			rightPQ = new PriorityQueue<Integer>(); // minHeap
			leftPQ = new PriorityQueue<Integer>(Collections.reverseOrder()); // maxHeap
		}
		
		int peek() {
			if(rightPQ.size() >= leftPQ.size()) { // 사이즈가 홀수이거나 루트값이 중간 바로 아래라면
				return this.root;
			} else {
				return leftPQ.peek();
			}
		}
		
		void add(int num) {
			if(this.root == Integer.MAX_VALUE) {
				this.root = num;
			} else {
				// 루트값과 같은 경우
				if(num == this.root) {
					// 양쪽이 같거나 오른쪽이 한개 적으면 오른쪽에 넣기
					if(rightPQ.size() - leftPQ.size() < 1) rightPQ.offer(num);
					// 왼쪽이 적으면 왼쪽에 넣기
					else leftPQ.offer(num);
				} else if(this.root < num) {
					// 넣어줄 값이 루트값보다 큰 경우
					rightPQ.offer(num);
					// 일단 오른쪽에 넣고
					// 오른쪽과 왼쪽의 차이가 2 이상이면 오른쪽의 원소가 root가 된다.
					if(rightPQ.size() - leftPQ.size() >= 2) {
						leftPQ.offer(this.root);
						this.root = rightPQ.poll();
					}
				} else {
					// 넣어줄 값이 루트값보다 작은 경우
					leftPQ.offer(num);
					// 일단 오른쪽에 넣고
					// 오른쪽과 왼쪽의 차이가 2 이상이면 오른쪽의 원소가 root가 된다.
					if(leftPQ.size() - rightPQ.size() >= 2) {
						rightPQ.offer(this.root);
						this.root = leftPQ.poll();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		MiddleHeap mh = new MiddleHeap();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<N; i++) {
			int t = Integer.parseInt(br.readLine());
			mh.add(t);
			sb.append(mh.peek()).append('\n');
		}
		
		System.out.println(sb);
		
		br.close();
	}

}
