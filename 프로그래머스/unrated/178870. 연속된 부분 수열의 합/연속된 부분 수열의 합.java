/**
## 문제 요약
1. 부분 수열의 합이 k인 부분수열 중 길이가 짧은 수열을 찾는다.
2. 길이가 짧은 수열이 여러개일 때 시작 인덱스가 작은 수열을 찾는다.

## 문제 풀이
1. 슬라이딩 윈도우 기법을 활용한다.
2. 인덱스 0부터 출발.
3. 부분 수열의 합이 k가 아니면 오른쪽 인덱스 +1
4. k를 넘어가면 왼쪽 인덱스 +1
5. 부분 수열의 합이 k가 될 때 오른쪽 인덱스 - 왼쪽 인덱스 + 1 이 수열의 길이
*/
class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = {0, 0};
        int orilen = Integer.MAX_VALUE;
        
        int li=0, ri=0;
        int tmp = sequence[0];
        while(li < sequence.length){
            if(tmp < k){
                if(ri == sequence.length-1) break;
                tmp += sequence[++ri];
            }
            else if(tmp > k){
                if(li == sequence.length-1) break;
                tmp -= sequence[li++];
            } else{
                int length = ri-li+1;
                if(orilen > length) {
                    answer[1] = ri;
                    answer[0] = li;
                    orilen = length;
                }
                if(ri < sequence.length-1){
                    tmp += sequence[++ri];
                } else if(li < sequence.length-1){
                    tmp -= sequence[li++];
                } else break;
            }
        }
        
        return answer;
    }
}