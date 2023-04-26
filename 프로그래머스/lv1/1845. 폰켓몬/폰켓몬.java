/**
# 문제요약
1. N마리 포켓몬 중 N/2마리 가져갈 수 있다.
2. 포켓몬은 같은 종류 여러마리가 존재
3. 최대한 다양한 종류의 포켓몬을 가져가고 싶을 때 N/2마리를 선택하는 방법.
4. N/2마리를 골랐을 때 가장 많은 종류의 개수는?
# 문제풀이
1. HashMap을 이용해 모든 포켓몬의 종류 저장
2. HashMap의 key 배열의 개수가 N/2보다 크다 -> 답은 N/2
3. 작다 -> 답은 key 배열의 개수.
*/
import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<nums.length; i++){
            map.put(nums[i], 1);
        }
        //System.out.println(map.keySet());
        int size = map.keySet().size();
        int N = nums.length;
        if(size >= N/2) answer = N/2;
        else answer = size;
        
        return answer;
    }
}