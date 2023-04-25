/**
## 문제 요약
1. 2차원 직교 좌표계. 중심이 원점인 다른 크기의 동심원
2. 반지름 r1, r2
3. 두 원 사이의 공간에 x와 y 좌표가 모두 정수인 점의 개수는?
4. 각 원 위의 점도 포함해서 세야 한다.

## 문제 풀이
1. 원 내부의 점의 개수 : x좌표에 따라서 가능한 y좌표가 달라진다.
2. (x, y)의 거리 : r^2 = x^2 + y^2
3. y^2 = r^2 - x^2 -> - sqrt(r^2 - X^2) <= y <= sqrt(r^2 - x^2)
4. 정수로 나타내야 하므로 둘 다 int형으로 바꿔준다.
5. 1사분면의 점의 개수를 구한 뒤 *4를 해주면 된다.
*/

class Solution {
    
    public long solution(int r1, int r2) {
        long answer = 0;
        
        for(int x=1; x<=r2; x++){
            long bigY = (long) Math.floor(Math.sqrt(Math.pow(r2,2) - Math.pow(x,2)));
            if(x <= r1){
                long smY = (long) Math.floor(Math.sqrt(Math.pow(r1, 2) - Math.pow(x,2)));
                long smDist = (long) Math.floor(Math.sqrt(Math.pow(x,2)+Math.pow(smY, 2)));
                if(smDist == r1) smY--;
                if(smY >= 0) answer += (bigY - smY);
                else answer += bigY+1;
            } else {
                answer += bigY+1;
            }
        }
        
        return answer*4;
    }
}