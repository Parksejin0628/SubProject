#pragma once
#include"ProgramBase.h"

//플레이어 관련
#define EASY_PLAYER_HP 99
#define NORMAL_PLAYER_HP 60
#define HARD_PLAYER_HP 30
#define HELL_PLAYER_HP 1

//재화 관련
#define SEED_INIT 500 //함정 설치가능한 재화
#define GOLD_INIT 0 //함정 업그레이드 가능한 재화
#define REWARD_GOLD 10//적 처리시 골드 보상
#define REWARD_SEED 10//적 처리시 확률적으로 SEED 획득한 양
#define RANDOM_REWARD_SEED 50//Seed 확률 랜덤으로 획득 50퍼로 임의로 설정

//함정 관련
#define TRIANGLE_TRAP_DAMAGE 3
#define RECTANGLE_TRAP_DAMAGE 4
#define HEXAGON_TRAP_DAMAGE 6
#define STAR_TRAP_DAMAGE 10
#define TRAP_SET_UP_SEED 30//함정 설치 비용

//적 유닛 관련
#define ENEMY_HP 3 //적 유닛 초기 hp
#define ENEMY_INIT_NUMBER 10

//본진 좌표 관련
#define NEXUS_XPOS -20
#define NEXUS_YPOS -20

//스테이지 관련
int stagestep = 1;
//본진 좌표 - 너비추가 해야 될지는 이번주 피드백을 통해... 탑뷰라 필요 없을 수도?

//적 스폰 위치
#define ENEMY_SPAWN_XPOS 20
#define ENEMY_SPAWN_YPOS 20

//메인함수 Enemy 타입 배열 생성위해 만듦
#define MAX_ENEMY 100
typedef struct Nexus {
    int xpos;
    int ypos;
}Nexus;
//플레이어 관련 정보
typedef struct Player {
    int HP;
    int Seed;
    int Gold;
    Nexus nexus;
}Player;
Player player;

typedef enum {
    TRIANGLE,
    RECTANGLE,
    HEXAGON,
    STAR,
    Circle,
    Diamond //보스 모양
} Shape;

typedef struct Trap {
    int damage;//트랩 데미지
    int upgrade;//트랩의 업그레이드 단계
    int xpos;
    int ypos;
    Shape shape;
}Trap;

typedef struct Enemy {
    int HP;
    int value;//적이 3라운드마다 hp가 상승하기 때문에 value로 지정
    int xpos;
    int ypos;
    Shape shape;
    bool isActive;
    bool isBoss;
}Enemy;

typedef struct EnemySpawner {
    int xpos;
    int ypos;
}EnemySpawner;

void InitGame();
int TrapSignal();//트랩 설치 신호를 보냄
Trap InitTrap();//신호에 맞는 트랩 생성
void EnemySpawn(); //적 유닛 생성함수
void EnemyMove(); //적 유닛 이동함수
void EnemyDamage(Enemy* enemy, Trap* trap);//적 유닛이 함정을 지나가면 데미지를 입는다.
bool isCrashWithPlayer(Enemy* enemy, Player* player);
bool isCrashWithTrap(Enemy* enemy, Trap* trap);
void TrapUpgrade(); //업그레이드 알고리즘 사용
void PlayerHPDecrease(Enemy* enemy, Player* player);
void IsNextStage();
void InitNexus();