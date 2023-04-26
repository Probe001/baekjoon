/**
# 문제 요약
1. n명의 권투 선수 (1-based index)
2. 권투 경기는 1:1, 둘 중 실력이 좋은 선수가 이긴다.
3. 경기 결과를 가지고 순위를 매길 때 몇부분이 비어있다.
4. 정확하게 순위를 매길 수 있는 선수의 수를 return하라.

4 3, 2
3 2
1 2
2 5

1   -
2   1 3 4
3   4
4   -
5   2 1 3 4

4 1 3 2 5
1 4 3 2 5
4 3 1 2 5

조상노드 개수    0 1 2 3 4 5
totalP         2 1 0 1 1 0
accum          2 3 3 4 5 5

1. totalP: 조상노드의 개수가 동일한 노드의 수
2. accum : 지금까지 줄을 설 수 있는 노드의 수
3. totalP가 1이며 조상 노드의 개수가 accum과 동일하면 count+1
*/
import java.util.*;
import java.io.*;

class Solution {
    static int[] totalP;    // 조상 노드의 개수
    static Node[] board;    // 인접 리스트
    
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        // 인접 리스트 만들기
        // 부모를 원소로 가진다.
        board = new Node[n+1];
        for(int i=0; i<results.length; i++){
            int a = results[i][0];
            int b = results[i][1];
            
            board[b] = new Node(a, board[b]);
        }
        // 모든 원소에 대해 bfs를 돌아 조상 노드의 개수를 구한다.
        totalP = new int[n+1];
        for(int i=1; i<=n;i++){
            int parents = bfs(i);
            totalP[parents]++;
        }
        // totalP를 0부터 순회하며 판단한다.
        //System.out.println(Arrays.toString(totalP));
        int accum = 0;
        for(int i=0; i<=n; i++){
            accum += totalP[i];
            //System.out.print((accum-1)+" ");
            if(totalP[i]==1 && i == accum-1) {
                answer++;
            }
        }
        
        
        
        return answer;
    }
    static int bfs(int origin){
        Queue<Integer> que = new ArrayDeque<>();
        boolean[] vis = new boolean[board.length];
        que.offer(origin);
        vis[origin] = true;
        int count = 0;
        while(!que.isEmpty()){
            Integer now = que.poll();
            for(Node ind = board[now]; ind != null; ind = ind.next){
                if(vis[ind.num]) continue;
                vis[ind.num] = true;
                count++;
                que.offer(ind.num);
            }
        }
        return count;
    }
    
    static class Point implements Comparable<Point> {
        int num;
        int parents;
        
        Point(int num, int parents){
            this.num = num;
            this.parents = parents;
        }
        @Override
        public int compareTo(Point p){
            return this.parents - p.parents;
        }
    }
    
    static class Node {
        int num; // 노드 번호
        Node next; // 다음 노드
        Node(int num, Node next){
            this.num = num;
            this.next = next;
        }
    }
}