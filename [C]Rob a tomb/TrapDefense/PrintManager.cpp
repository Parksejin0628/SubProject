#pragma once
#include"PrintManager.h"

void SetColor(unsigned short color)
{
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), color);

	return;
}

void Goto_xy(short x, short y)
{
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	Position pos = { x, y };
	SetConsoleCursorPosition(handle, pos);
}

void PrintShape(short x, short y, char shape, unsigned short color)
{
	SetColor(color);
	Goto_xy(x, y);
	printf("%c", shape);
	SetColor(COLOR_WHITE);
}

