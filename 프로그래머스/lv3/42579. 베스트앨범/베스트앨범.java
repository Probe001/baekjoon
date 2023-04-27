/**
요약
1. 장르별로 가장 많이 재생된 노래를 두개 씩 모음
2. 장르에 속한 노래가 많이 재생된 장르 먼저 수록
3. 장르 내에서 많이 재생된 노래 먼저 수록
4. 재생횟수가 같은 노래 중에는 고유번호가 낮은 노래를 먼저 수록

풀이
1. 장르별로 PriorityQueue를 들고있는다.(재생수 내림차순, 고유번호 오름차순)
2. 또한 장르 배열을 재생 수 총합 기준으로 내림차순 정렬한다.
3. 장르배열을 순회하며 PQ에서 두개씩 꺼내 리스트에 넣는다.
4. 리스트를 array로 변경한다.
*/
import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        
        HashMap<String, PriorityQueue<Music>> map = new HashMap<>();
        HashMap<String, Integer> total = new HashMap<>();
        for(int i=0; i<genres.length; i++){
            String g = genres[i];
            int p = plays[i];
            
            if(!map.containsKey(g)) {
                map.put(g, new PriorityQueue<Music>());
                total.put(g, 0);
            }
            map.get(g).add(new Music(i, p));
            total.put(g, total.get(g)+p);
        }
        
        PriorityQueue<Genre> pq = new PriorityQueue<>();
        for(String now : total.keySet()){
            pq.offer(new Genre(now, total.get(now)));
        }
        
        List<Integer> res = new ArrayList<>();
        while(!pq.isEmpty()){
            Genre now = pq.poll();
            PriorityQueue<Music> pq2 = map.get(now.name);
            int cnt = 0;
            while(!pq2.isEmpty() && cnt < 2){
                Music music = pq2.poll();
                res.add(music.isbn);
                cnt++;
            }
        }
        Integer[] answer = res.toArray(new Integer[0]);
        int[] r = new int[answer.length];
        for(int i=0; i<answer.length; i++){
            r[i] = answer[i];
        }
        
        return r;
    }
    static class Genre implements Comparable<Genre>{
        String name; // 장르이름
        int total ; // 총 재생수
        Genre(String name, int total){
            this.name = name;
            this.total = total;
        }
        public int compareTo(Genre g){ // 총 재생수 내림차순
            return g.total - this.total;
        }
    }
    
    static class Music implements Comparable<Music>{
        int isbn;   // 고유번호
        int played; // 재생 수
        
        Music(int isbn, int played){
            this.isbn = isbn;
            this.played = played;
        }
        
        @Override
        public int compareTo(Music m){ // 재생수 내림차순, 고유번호 오름차순
            if(this.played == m.played) return this.isbn - m.isbn;
            return m.played - this.played;
        }
    }
}