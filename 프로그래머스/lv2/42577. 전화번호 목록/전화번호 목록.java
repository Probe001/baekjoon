/**
#문제 풀이
1. phone_book을 사전순으로 정렬한다.
2. 기준은 0부터 시작.
3. 현재 기준과 그 다음 문자열을 비교.
4. 다르면 기준을 다음 문자열로 바꾸고 넘어간다.
*/
import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        
        Arrays.sort(phone_book);
        String tmp = phone_book[0];
        for(String now:phone_book){
            if(!now.equals(tmp) && now.indexOf(tmp) == 0) answer = false;
            tmp = now;
        }
        
        return answer;
    }
}