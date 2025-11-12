#include "BubbleDreamGameMode.h"

// 초기 점수 0
ABubbleDreamGameMode::ABubbleDreamGameMode()
{
	MemoryPoints = 0;
}

// Called when the game starts or when spawned
void ABubbleDreamGameMode::BeginPlay()
{
	Super::BeginPlay();
}

// 메모리 점수 추가(점수 변동 시 1을 변수화)
void ABubbleDreamGameMode::AddMemoryPoint()
{
	MemoryPoints += 1;
}

// 메모리 포인트 Get
int32 ABubbleDreamGameMode::GetMemoryPoints() const
{
	return MemoryPoints;
}
