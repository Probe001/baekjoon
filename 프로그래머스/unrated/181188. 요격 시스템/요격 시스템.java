/**
1. S, E 사이에 쏴야 됨 (소수점 가능). S나 E점에서는 불가능.
3. TARGET의 길이(미사일 개수)는 최대 500000
4. 구간의 길이는 0 <= S < E <= 100,000,000
    -> 배열 사용 불가
5. 최소한의 미사일 수를 구해야 한다.
6. 끝나는 시간을 기준으로 오름차순 정렬한다.
7. 시작하는 시간을 기준으로 오름차순 정렬한다.
8. 해당 구간을 벗어나면 +1을 해준다.
*/

import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        Arrays.sort(targets, (x1, x2)-> {
            if(x2[1] == x1[1]) return x1[0] - x2[0];
            return x1[1]-x2[1];
            });
        
        int end = 0;
        for(int i=0; i<targets.length; i++){
            if(end <= targets[i][0]) {
                answer++;
                end = targets[i][1];
            }
        }
        
        return answer;
    }
}