#pragma once
#include"PrintScreen.h"

void PrintScreen::SetColor(unsigned short color)
{
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), color);

	return;
}

void PrintScreen::Goto_xy(short x, short y)
{
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	Position pos = { x, y };
	SetConsoleCursorPosition(handle, pos);
}

void PrintScreen::PrintString(short x, short y, string printStr, unsigned short color)
{
	SetColor(color);
	Goto_xy(x, y);
	cout << printStr;
	SetColor(COLOR_WHITE);
}

void PrintScreen::PrintTile(short x, short y, string tileStr, unsigned short color)
{
	PrintScreen::PrintString(x + 0, y, "忙", color);
	PrintScreen::PrintString(x + 1, y, "式", color);
	PrintScreen::PrintString(x + 2, y, "式", color);
	PrintScreen::PrintString(x + 3, y, "式", color);
	PrintScreen::PrintString(x + 4, y, "忖", color);

	PrintScreen::PrintString(x + 0, y + 1, "弛", color);
	PrintScreen::PrintString(x + 1, y + 1, tileStr.substr(0, 3), color);
	PrintScreen::PrintString(x + 4, y + 1, "弛", color);

	PrintScreen::PrintString(x + 0, y + 2, "戌", color);
	PrintScreen::PrintString(x + 1, y + 2, "式", color);
	PrintScreen::PrintString(x + 2, y + 2, "式", color);
	PrintScreen::PrintString(x + 3, y + 2, "式", color);
	PrintScreen::PrintString(x + 4, y + 2, "戎", color);
}

