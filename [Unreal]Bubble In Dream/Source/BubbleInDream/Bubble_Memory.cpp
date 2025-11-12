#include "Bubble_Memory.h"
#include "Components/SphereComponent.h"
#include "Components/StaticMeshComponent.h"
#include "Kismet/GameplayStatics.h"
#include "BubbleDreamGameMode.h"

// Sets default values
ABubble_Memory::ABubble_Memory()
{
	// Set this actor to call Tick() every frame
	PrimaryActorTick.bCanEverTick = true;

	// Initialize the sphere collision component
	CollisionComponent = CreateDefaultSubobject<USphereComponent>(TEXT("CollisionComponent"));
	RootComponent = CollisionComponent;
	CollisionComponent->InitSphereRadius(50.0f);
	CollisionComponent->SetCollisionProfileName(TEXT("OverlapAllDynamic"));

	// Initialize the static mesh component
	MeshComponent = CreateDefaultSubobject<UStaticMeshComponent>(TEXT("MeshComponent"));
	MeshComponent->SetupAttachment(RootComponent);
	MeshComponent->SetCollisionProfileName(TEXT("NoCollision"));

	// Bind the overlap event
	CollisionComponent->OnComponentBeginOverlap.AddDynamic(this, &ABubble_Memory::OnOverlapBegin);
}

// Called when the game starts or when spawned
void ABubble_Memory::BeginPlay()
{
	Super::BeginPlay();

	// Ensure the overlap event is bound correctly
	CollisionComponent->OnComponentBeginOverlap.AddDynamic(this, &ABubble_Memory::OnOverlapBegin);
}

// Called every frame
void ABubble_Memory::Tick(float DeltaTime)
{
	Super::Tick(DeltaTime);
}

// Called when the player overlaps with the bubble
void ABubble_Memory::OnOverlapBegin(UPrimitiveComponent* OverlappedComp, AActor* OtherActor,
	UPrimitiveComponent* OtherComp, int32 OtherBodyIndex, bool bFromSweep, const FHitResult& SweepResult)
{
	if (OtherActor && (OtherActor != this))
	{
		AddScore();
		Destroy();
	}
}

// Adds score to the game mode
void ABubble_Memory::AddScore()
{
	ABubbleDreamGameMode* GameMode = Cast<ABubbleDreamGameMode>(UGameplayStatics::GetGameMode(GetWorld()));
	if (GameMode)
	{
		GameMode->AddMemoryPoint();
	}
}
