#pragma once
#include"ProgramBase.h"

enum Color
{
	COLOR_BLACK = 0, COLOR_BLUE, COLOR_GREEN, COLOR_BLUEGREEN, COLOR_RED, COLOR_PURPLE, COLOR_YELLOW, COLOR_WHITE,
	COLOR_GRAY, COLOR_LIGHTBLUE, COLOR_LIGHTGREEN, COLOR_LIGHTBLUEGREEN, COLOR_LIGHTRED, COLOR_LIGHTPURPLE, COLOR_LIGHTYELLOW, COLOR_LIGHTWHITE
};

typedef COORD Position;

static void SetColor(unsigned short color);	//출력의 색을 변화시키는 함수
static void Goto_xy(short x, short y);	//출력 시작 부분을 x, y부분으로 옮김, 좌측상단이 (0, 0)이다. 행렬을 생각하면 편하다.
static void PrintShape(short x, short y, char shape, unsigned short color);	//콘솔의 x, y좌표에 color 색으로 shape를 출력