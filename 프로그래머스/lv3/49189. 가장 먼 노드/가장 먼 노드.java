/**
# 문제 요약
1. n개의 노드 (1-based index)
2. 1번 노드로부터 가장 멀리 떨어진 노드의 개수는?
- 최단 경로로 이동했을 때 간선의 개수가 가장 많은 노드
- 가중치가 없으므로 BFS
3. 간선의 정보가 주어진다.
- 인접 리스트로 구현.
# 문제 풀이
1. 각 노드를 1번부터 BFS로 순회한다.
2. 최대 거리가 갱신될 때 마다 결과 List를 초기화 한뒤 집어넣는다.
3. 결과 List의 사이즈를 반환한다.
*/
import java.util.*;
import java.io.*;

class Solution {
    static Node[] board; // 인접리스트
    
    static int res; // 결과값
    
    public int solution(int n, int[][] edge) {
        int answer = 0;
        
        board = new Node[n+1]; // 1-based index 연결 리스트 배열
        for(int i=0; i<edge.length; i++){
            int a = edge[i][0];
            int b = edge[i][1];
            board[a] = new Node(b, board[a]); // a의 인접 노드
            board[b] = new Node(a, board[b]); // b의 인접 노드
        }
        // bfs 돌기
        //print();
        bfs();
        
        answer = res;
        return answer;
    }
    
    static void print(){
        for(int i=1; i<board.length; i++){
            Node now = board[i];
            while(now != null){
                System.out.print(now.num+" ");
                now = now.next;
            }
            System.out.println();
        }
    }
    
    static void bfs(){
        Queue<Point> que = new ArrayDeque<>();
        boolean[] vis = new boolean[board.length];
        que.offer(new Point(1));
        vis[1] = true;
        
        int step = 1; // 거리는 1부터 시작
        while(!que.isEmpty()){
            int stepSize = que.size(); // 현재 단계의 노드의 개수
            //System.out.println(que);
            //System.out.println(Arrays.toString(vis));
            res = stepSize;
            for(int i=0; i<stepSize; i++){ // 현재 단계만큼만 진행한다.
                Point now = que.poll();
                Node near = board[now.num];
                while(near != null){
                    if(vis[near.num]) {
                        near = near.next;
                        continue;
                    }
                    vis[near.num] = true;
                    que.offer(new Point(near.num));
                }
            }
        }
    }
    static class Point{
        int num;
        Point(int num){
            this.num = num;
        }
        @Override
        public String toString(){
            return "["+this.num+"]";
        }
    }
    
    static class Node{
        int num; // 노드의 번호
        Node next; // 다음 노드
        
        Node(int num, Node next){
            this.num = num;
            this.next = next;
        }
        
        Node(int num){
            this.num = num;
            this.next = null;
        }
    }
}