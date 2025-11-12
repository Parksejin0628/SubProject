#pragma once

#include"ProgramBase.h"

int bigger(int arr1, int arr2) //큰 수 비교
{
    return (arr1 > arr2) ? arr1 : arr2;
}

//shape에 사용될 정보
#define TRIANGLE 1
#define RECTANGLE 2
#define HEXAGON 3
#define STAR 4 
#define Circle 5

//Trap구조체로 이루어진 연결리스트로 함정관리하면 좋을듯
typedef struct Trap {
    int damage;
    int xpos;
    int ypos;
    int shape;
    int up_value;
    int up_cost;
}Trap;

//함정 업그레이드시 사용하는 가격 정보 (값은 예시)
int info_up_cost[5][5];

//함정 업그레이드시 밸류 정보 (값은 예시)
int info_up_value[5][5];

//01가방 알고리즘으로 최대 밸류값으로 업데이트 할 함정을 선택하는 함수
int bag_zeroone(int n, int M, int** bag, Trap trap[], bool* selectedTraps);