// Copyright Epic Games, Inc. All Rights Reserved.

#include "BubbleInDreamGameMode.h"
#include "BubbleInDreamPlayerController.h"
#include "BubbleInDreamCharacter.h"
#include "UObject/ConstructorHelpers.h"

ABubbleInDreamGameMode::ABubbleInDreamGameMode()
{
	// use our custom PlayerController class
	PlayerControllerClass = ABubbleInDreamPlayerController::StaticClass();

	// set default pawn class to our Blueprinted character
	static ConstructorHelpers::FClassFinder<APawn> PlayerPawnBPClass(TEXT("/Game/TopDown/Blueprints/BP_TopDownCharacter"));
	if (PlayerPawnBPClass.Class != nullptr)
	{
		DefaultPawnClass = PlayerPawnBPClass.Class;
	}

	// set default controller to our Blueprinted controller
	static ConstructorHelpers::FClassFinder<APlayerController> PlayerControllerBPClass(TEXT("/Game/TopDown/Blueprints/BP_TopDownPlayerController"));
	if(PlayerControllerBPClass.Class != NULL)
	{
		PlayerControllerClass = PlayerControllerBPClass.Class;
	}
}