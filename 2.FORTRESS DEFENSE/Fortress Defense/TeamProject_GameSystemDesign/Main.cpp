#include"ProgramBase.h"
#include"PrintScreen.h"
#define MAXSIZE 5

int main(void)
{
	string tileStr;
	for (int y = 0; y < MAXSIZE; y++)
	{
		for (int x = 0; x < MAXSIZE; x++)
		{
			tileStr = to_string(x) + "," + to_string(y);
			PrintScreen::PrintTile(0 + TILESIZE_X * x, 0 + TILESIZE_Y * y, tileStr , COLOR_BLUE);
		}
	}
	for (int y = 0; y < 5; y++)
	{
		for (int x = 0; x < 5; x++)
		{
			
		}

	}

	

	return 0;
}