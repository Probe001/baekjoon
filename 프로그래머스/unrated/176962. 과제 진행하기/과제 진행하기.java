/**
# 문제 풀이
1. 기존 과제 : PQ left
2. 진행중 과제 : Deque doing
3. 끝난 과제 : Que done
4. 과제 : Class Homework
    - name, start, playtime, donetime
5. doing이 비어있다 -> PQ를 확인
    - PQ 비어있다 -> 끝남
    - PQ 안비어있다
        - PQ -> doing으로 offerFirst
        - nowTime은 시작시간으로 설정
6. doing이 비어있지 않다
    - PQ 비어있다
        - doing을 차례대로 끝냄
    - PQ 안비어있다.
        - doing의 맨앞부분 확인
            - start + (playtime - donetime) > PQ 첫부분 start 이면 미뤄야됨
                - doing의 donetime 설정 : PQ 첫부분 start - doing의 start
                - pq 첫부분을 doing으로 offerFirst
                - nowtime = pq 첫부분.start
            - start + (playtime - donetime) <= PQ 첫부분 start 이면 done으로 offer
7. done에서 하나씩 꺼내면서 answer 채워나가기.
*/
import java.util.*;
import java.io.*;

class Solution {
    static PriorityQueue<Homework> left;
    static Deque<Homework> doing;
    static Queue<Homework> done;
    
    
    public String[] solution(String[][] plans) {
        String[] answer = new String[plans.length];
        
        left = new PriorityQueue<>();
        doing = new ArrayDeque<>();
        done = new ArrayDeque<>();
        
        // left 채우기
        for(int i=0; i<plans.length; i++){
            String name = plans[i][0];
            int hour = Integer.parseInt(plans[i][1].substring(0,2));
            int min = Integer.parseInt(plans[i][1].substring(3,5));
            int time = hour*60+min;
            int playtime = Integer.parseInt(plans[i][2]);
            left.offer(new Homework(name, time, playtime));
        }
        // 풀이
        int nowTime = -1;
        while(true){
            if(doing.isEmpty()){
                if(left.isEmpty()) break;
                nowTime = left.peek().start;
                // System.out.println(nowTime);
                doing.offerFirst(left.poll());
            } else {
                if(left.isEmpty()){
                    while(!doing.isEmpty()){
                        Homework tmp = doing.pollFirst();
                        // tmp.donetime = nowTime + (tmp.playtime - tmp.donetime);
                        // nowTime = tmp.donetime;
                        done.offer(tmp);
                    }
                } else {
                    // 현재 과제와 다음 과제의 시간 비교
                    Homework now = doing.peekFirst();
                    int nowHomeTime = nowTime + (now.playtime - now.donetime);
                    int nextHomeTime = left.peek().start;
                    if(nowHomeTime > nextHomeTime){
                        now.donetime += nextHomeTime - nowTime;
                        nowTime = nextHomeTime;
                        doing.offerFirst(left.poll());
                    } else {
                        now.donetime += nowHomeTime - nowTime;
                        nowTime = nowHomeTime;
                        done.offer(doing.pollFirst());
                    }
                }
            }
        }
        // 결과 넣기
        for(int i=0; i<plans.length; i++){
            System.out.println(done.peek());
            answer[i] = done.poll().name;
        }
        
        return answer;
    }
    
    static class Homework implements Comparable<Homework>{
        String name;
        int start;
        int playtime;
        int donetime;
        
        Homework(String name, int start, int playtime){
            this.name = name;
            this.start = start;
            this.playtime = playtime;
            this.donetime = 0;
        }
        
        @Override
        public int compareTo(Homework h){
            return this.start - h.start;
        }
        
        @Override
        public String toString(){
            return "["+name+" "+start+" "+playtime+" "+donetime+"]";
        }
    }
}