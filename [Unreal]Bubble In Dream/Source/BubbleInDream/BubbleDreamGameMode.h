#pragma once

#include "CoreMinimal.h"
#include "GameFramework/GameModeBase.h"
#include "BubbleDreamGameMode.generated.h"

UCLASS()
class BUBBLEINDREAM_API ABubbleDreamGameMode : public AGameModeBase
{
	GENERATED_BODY()

public:
	ABubbleDreamGameMode();

	// Function to add memory points
	void AddMemoryPoint();
	void SubtractMemoryPoint();
	// Get the current memory points
	UFUNCTION(BlueprintPure, Category = "Score")
	int32 GetMemoryPoints() const;

protected:
	// Called when the game starts or when spawned
	virtual void BeginPlay() override;

public:
	// Memory points variable, editable in the editor
	UPROPERTY(EditAnywhere, BlueprintReadWrite, Category = "Score")
	int32 MemoryPoints;
};
