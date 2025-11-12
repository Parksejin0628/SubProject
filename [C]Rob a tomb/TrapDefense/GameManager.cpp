#include"GameManager.h"

char inputManager()
{
	char input;

	if (kbhit())
	{
		input = getch();
		switch (input)
		{
		case 'a': //원하는 키 넣고
			//이 부분에 원하는 기능 (기능은 함수로 구현하고 함수 명만 놓는 것을 추천)
			break;
		}
		return input;
	}
}