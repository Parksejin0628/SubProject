#include"ProgramBase.h"
#include"Upgrade.h"

//함정 업그레이드시 사용하는 가격 정보 (값은 예시)
info_up_cost[5][5] = { {10, 20, 30, 40, 50},
                  {20, 40, 60, 80, 100},
                  {30, 60, 90, 120, 150},
                  {40, 80, 120, 160, 200},
                  {50, 100, 150, 200, 250} };

//함정 업그레이드시 밸류 정보 (값은 예시)
info_up_value[5][5] = { {1, 2, 3, 4, 5},
                     {2, 4, 6, 8, 10},
                     {3, 6, 9, 12, 15},
                     {4, 8, 10, 16, 20},
                     {5, 10, 15, 20, 25} };

//01가방문제 알고리즘
int bag_zeroone(int n, int M, int **bag, Trap trap[], bool* selectedTraps)
{
    int i, w;

    //최적해를 구하는 알고리즘
    for (i = 0; i <= n; i++)
    {
        for (w = 0; w <= M; w++)
        {
            if (i == 0 || w == 0)
            {
                bag[i][w] = 0;
            }
            else if (w < trap[i].up_cost)
            {
                bag[i][w] = bag[i - 1][w];
            }
            else
            {
                bag[i][w] = bigger(bag[i - 1][w], bag[i - 1][w - trap[i].up_cost] + trap[i].up_value);
            }
        }
    }

    //선택된 함정들을 selectedTraps 배열에 저장한다
    i = n;
    w = M;
    while (i > 0 && w > 0) {
        if (bag[i][w] != bag[i - 1][w]) {
            selectedTraps[i - 1] = true; // 해당 아이템이 선택되었음을 표시
            w -= trap[i - 1].up_cost;
        }
        --i;
    }
    return bag[n][M];
}

/*
int main(void)
{
    int n = 0; //모든 함정 개수의 합, Traps배열의 크기?를 구해서 초기화할듯
    int money = 0; //현재 보유한 돈

    Trap traps[2]; //구현된 함정 배열, 실제로는 동적으로 할당되어있음

    bool* selectedTraps = (bool*)calloc(n, sizeof(bool)); //선택된 함정 정보를 저장하는 배열

    //01배낭 알고리즘을 이용하기 위한 배낭 동적 할당
    int** bag;
    bag = (int**)malloc(sizeof(int*) * n);
    for (int i = 0; i < n; i++) {
        bag[i] = (int*)malloc(sizeof(int) * money);
    }

	int propit = bag_zeroone(n, money, bag, traps, selectedTraps); //최대밸류값을 리턴하긴하는데 굳이 필요없을거같긴함
	
    //동적 할당받은 2차원 배열 free해주기
    for (int i = 0; i < n; i++)
    {
        free(bag[i]);
    }
    free(bag);

    //selectedTraps free해주기
    free(selectedTraps);

    return 0;
}
*/