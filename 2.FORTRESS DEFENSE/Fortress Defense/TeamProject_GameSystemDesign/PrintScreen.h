#pragma once
#include"ProgramBase.h"

#define TILESIZE_X 5
#define TILESIZE_Y 3

enum Color
{
	COLOR_BLACK = 0, COLOR_BLUE, COLOR_GREEN, COLOR_BLUEGREEN, COLOR_RED, COLOR_PURPLE, COLOR_YELLOW, COLOR_WHITE,
	COLOR_GRAY, COLOR_LIGHTBLUE, COLOR_LIGHTGREEN, COLOR_LIGHTBLUEGREEN, COLOR_LIGHTRED, COLOR_LIGHTPURPLE, COLOR_LIGHTYELLOW, COLOR_LIGHTWHITE
};
//COORD는 window.h 헤더파일에서 x, y좌표를 담고있는 구조체
//구성멤버 : short X / short Y
typedef COORD Position;

class PrintScreen
{
private:

public:
	static void SetColor(unsigned short color = COLOR_WHITE);	//출력의 색을 변화시키는 함수
	static void Goto_xy(short x, short y);						//출력 시작 부분을 x, y부분으로 옮김, 좌측상단이 (0, 0)이다. 행렬을 생각하면 편하다.
	static void PrintString(short x, short y, string printStr, unsigned short color = COLOR_WHITE);	//콘솔의 x, y좌표에 color 색으로 printStr을 출력
	static void PrintTile(short x, short y, string tileStr = "   ", unsigned short color = COLOR_WHITE); //콘솔 x, y좌표에 color 색의 타일 출력, 타일 안에 tileStr 출력
};
