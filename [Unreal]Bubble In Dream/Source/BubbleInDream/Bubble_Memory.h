#pragma once

#include "CoreMinimal.h"
#include "GameFramework/Actor.h"
#include "Bubble_Memory.generated.h"

UCLASS()
class BUBBLEINDREAM_API ABubble_Memory : public AActor
{
	GENERATED_BODY()

public:
	// Sets default values for this actor's properties
	ABubble_Memory();

protected:
	// Called when the game starts or when spawned
	virtual void BeginPlay() override;

public:
	// Called every frame
	virtual void Tick(float DeltaTime) override;

	// Sphere collision component
	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "Components")
	class USphereComponent* CollisionComponent;

	// Static mesh component
	UPROPERTY(VisibleAnywhere, BlueprintReadOnly, Category = "Components")
	class UStaticMeshComponent* MeshComponent;

	// Called when the player overlaps with the bubble
	UFUNCTION()
	void OnOverlapBegin(class UPrimitiveComponent* OverlappedComp, class AActor* OtherActor,
		class UPrimitiveComponent* OtherComp, int32 OtherBodyIndex, bool bFromSweep, const FHitResult& SweepResult);

private:
	// Adds score to the game mode
	void AddScore();
};
