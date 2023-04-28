/**
n명이 입국심사
1. 가장 앞에 서 있는 사람은 비어있는 심사대로 간다.
2. 심사대에서는 각자 시간이 다 다르게 걸린다.
3. 모든 사람이 심사를 받는데 걸리는 시간을 최소로 만들자.
4. 사람이 10억명이므로 한명한명 보는 것은 못함
5. 각 창구별 시간 : 소요시간 * 처리인원
6. 전체 시간 : 가장 오래걸린 창구별 시간
7. 창구별로 처리 인원을 다르게 배정해야 한다. 어떻게?
    7 14 28
    10 20 
    시간별로 창구를 정렬한다.
    가장 낮은 시간을 배수시켜서 pq? 시간초과
    그럼 시간을 기준으로 이분탐색?
    모두 처리하지 못하는 시간을 찾아내자
8. 이분탐색
    st= 1분, en = 1,000,000,001분
    심사관을 시간순대로 정렬한 뒤, 작은애부터 현재 시간을 나눠서 사람수가 나오는지 본다.
    사람수가 나온다면 en = mid
    사람수가 안나온다면 st = mid+1;
*/
import java.util.*;

class Solution {
    static final long MAX = (long)1e18;
    public long solution(int n, int[] times) {
        long answer = 0;
        
        Arrays.sort(times);
        answer = binarySearch(n, times);
        
        return answer;
    }
    static long binarySearch(int n, int[] times){
        long st = 1, en = MAX;
        // System.out.println(st+" "+en);
        while(st < en){
            //long mid = en + (en-st)/2;
            long mid = (en+st)/2;
            long num = getNum(mid,n, times);
            // System.out.println(mid+" "+ num);
            if(num >= n){
                en = mid;
            } else {
                st = mid+1;
            }
        }
        return st;
    }
    static long getNum(long curTime,int n, int[] times){
        long cnt = 0;
        for(int i=0; i<times.length; i++){
            cnt += (curTime/times[i]);
            if(cnt >= n) return cnt;
        }
        return cnt;
    }
}