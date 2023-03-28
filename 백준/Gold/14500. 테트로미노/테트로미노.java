/**
 * ## 문제 요약

1. 폴리오미노: 1*1 정사각형을 붙여 만든 도형. 다음의 조건 만족

- 정사각형은 서로 겹치면 안됨
- 도형은 모두 연결 돼 있어야 함
- 정사각형의 변 끼리 연결돼있어야 함

2. 테트로미노 : 정사각형이 4개인 폴리오미노. 5가지 종류 존재

3. N*M 종이 위에 테트로미노 하나를 놓음

4. 테트로미노 하나를 적절히 놓아 테트로미노가 있는 칸의 합이 최대가 돼야 함

5. 테트로미노는 대칭이나 회전이 가능

6. 최대값을 도출하라

## 입력 및 제약조건

1. 첫째 줄 : 세로 크기 N, 가로 크기 M
4 <= N, M <= 500

2. N 개 줄 : 종이에 쓰여있는 수
1 <= 입력 수 <= 1000

## 출력

1. 첫째 줄 : 테트로미노가 놓인 칸에 쓰인 수들의 합의 최댓값 출력

## 문제 풀이

1. 각 테트로미노가 가지는 모양을 전부 프리셋으로 저장한다.
```java
class Tetromino{
    int width, height; // 가로넓이, 세로넓이
    int[][] dxy = {} ; // 좌 상단 좌표를 기준으로 값을 가져올 위치좌표
}
```

2. 다음은 각 테트로미노의 모양과 dxy다. (ㅁ는 네모, ..은 빈칸)
1) width = 4, height = 1, dxy = {{0,0},{0,1},{0,2},{0,3}};
ㅁㅁㅁㅁ

2) width = 3, height = 2, dxy = {{0,0},{0,1},{0,2},{1,2}};
ㅁㅁㅁ
....ㅁ

3) width = 3, height = 2, dxy = {{0,0},{0,1},{0,2},{1,1}};
ㅁㅁㅁ
..ㅁ..

4) width = 3, height = 2, dxy = {{0,0},{0,1},{0,2},{1,0}};
ㅁㅁㅁ
ㅁ....

5) width = 3, height = 2, dxy = {{0,0},{0,1},{1,1},{1,2}};
ㅁㅁ..
..ㅁㅁ

6) width = 3, height = 2, dxy = {{0,1},{0,2},{1,0},{1,1}};
..ㅁㅁ
ㅁㅁ..

7) width = 3, height = 2, dxy = {{0,0},{1,0},{1,1},{1,2}};
ㅁ....
ㅁㅁㅁ

8) width = 3, height = 2, dxy = {{0,1},{1,0},{1,1},{1,2}};
..ㅁ..
ㅁㅁㅁ

9) width = 3, height = 2, dxy = {{0,2},{1,0},{1,1},{1,2}};
....ㅁ
ㅁㅁㅁ

10) width = 2, height = 2, dxy = {{0,0},{0,1},{1,0},{1,1}};
ㅁㅁ
ㅁㅁ

11) width = 2, height = 3, dxy = {{0,0},{0,1},{1,0},{2,0}};
ㅁㅁ
ㅁ..
ㅁ..

12) width = 2, height = 3, dxy = {{0,0},{1,0},{2,0},{1,1}};
ㅁ..
ㅁㅁ
ㅁ..

13) width = 2, height = 3, dxy = {{0,0},{1,0},{2,0},{2,1}};
ㅁ..
ㅁ..
ㅁㅁ

14) width = 2, height = 3, dxy = {{0,0},{0,1},{1,1},{2,1}};
ㅁㅁ
..ㅁ
..ㅁ

15) width = 2, height = 3, dxy = {{1,0},{0,1},{1,1},{2,1}};
..ㅁ
ㅁㅁ
..ㅁ

16) width = 2, height = 3, dxy = {{2,0},{0,1},{1,1},{2,1}};
..ㅁ
..ㅁ
ㅁㅁ

17) width = 2, height = 3, dxy = {{0,0},{1,0},{1,1},{2,1}};
ㅁ..
ㅁㅁ
..ㅁ

18) width = 2, height = 3, dxy = {{0,1},{1,0},{1,1},{2,0}};
..ㅁ
ㅁㅁ
ㅁ..

19) width = 1, height = 4, dxy = {{0,0},{1,0},{2,0},{3,0}};
ㅁ
ㅁ
ㅁ
ㅁ

3. 0,0 부터 테트로미노가 들어갈 수 있는 경우들을 구해서 최대값을 도출한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M;
	static int[][] board;
	
	static class Tetromino{
		int width, height;
		int[][] dxy;
		
		Tetromino(int width, int height, int[][] dxy){
			this.width = width;
			this.height = height;
			this.dxy = dxy;
		}
	}
	static Tetromino[] tetromino = new Tetromino[19];
	static int res;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		for(int x=0; x<N; x++) {
			st = new StringTokenizer(br.readLine());
			for(int y=0; y<M; y++) {
				board[x][y] = Integer.parseInt(st.nextToken());
			}
		}
		makeTetro();
		res = Integer.MIN_VALUE;
		for(int x=0; x<N; x++) {
			for(int y=0; y<M; y++) {
				findMax(x, y);
			}
		}
		System.out.println(res);
		br.close();
	}
	
	private static void findMax(int x, int y) {
		int tmpMax = Integer.MIN_VALUE;
		
		for(int i=0; i<19; i++) {
			Tetromino now = tetromino[i];
			if(x+now.height >= N) continue;
			if(y+now.width >= M) continue;
			int tmp = 0;
			for(int k=0; k<4; k++) {
				int nx = x + now.dxy[k][0];
				int ny = y + now.dxy[k][1];
				tmp += board[nx][ny];
			}
			tmpMax = Math.max(tmpMax, tmp);
		}
		res = Math.max(res, tmpMax);
		
	}

	static void makeTetro() {		
		tetromino[0] = new Tetromino(3, 0, new int[][] {{0,0},{0,1},{0,2},{0,3}});
		tetromino[1] = new Tetromino(2, 1, new int[][] {{0,0},{0,1},{0,2},{1,2}});
		tetromino[2] = new Tetromino(2, 1, new int[][] {{0,0},{0,1},{0,2},{1,1}});
		tetromino[3] = new Tetromino(2, 1, new int[][] {{0,0},{0,1},{0,2},{1,0}});
		tetromino[4] = new Tetromino(2, 1, new int[][] {{0,0},{0,1},{1,1},{1,2}});
		tetromino[5] = new Tetromino(2, 1, new int[][] {{0,1},{0,2},{1,0},{1,1}});
		tetromino[6] = new Tetromino(2, 1, new int[][] {{0,0},{1,0},{1,1},{1,2}});
		tetromino[7] = new Tetromino(2, 1, new int[][] {{0,1},{1,0},{1,1},{1,2}});
		tetromino[8] = new Tetromino(2, 1, new int[][] {{0,2},{1,0},{1,1},{1,2}});
		tetromino[9] = new Tetromino(1, 1, new int[][] {{0,0},{0,1},{1,0},{1,1}});
		tetromino[10] = new Tetromino(1, 2, new int[][] {{0,0},{0,1},{1,0},{2,0}});
		tetromino[11] = new Tetromino(1, 2, new int[][] {{0,0},{1,0},{2,0},{1,1}});
		tetromino[12] = new Tetromino(1, 2, new int[][] {{0,0},{1,0},{2,0},{2,1}});
		tetromino[13] = new Tetromino(1, 2, new int[][] {{0,0},{0,1},{1,1},{2,1}});
		tetromino[14] = new Tetromino(1, 2, new int[][] {{1,0},{0,1},{1,1},{2,1}});
		tetromino[15] = new Tetromino(1, 2, new int[][] {{2,0},{0,1},{1,1},{2,1}});
		tetromino[16] = new Tetromino(1, 2, new int[][] {{0,0},{1,0},{1,1},{2,1}});
		tetromino[17] = new Tetromino(1, 2, new int[][] {{0,1},{1,0},{1,1},{2,0}});
		tetromino[18] = new Tetromino(0, 3, new int[][] {{0,0},{1,0},{2,0},{3,0}});
	}

}