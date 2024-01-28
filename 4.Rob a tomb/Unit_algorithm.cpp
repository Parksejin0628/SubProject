#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#include<stdbool.h>
//플레이어 관련
#define EASY_PLAYER_HP 99
#define NORMAL_PLAYER_HP 60
#define HARD_PLAYER_HP 30
#define HELL_PLAYER_HP 1

//재화 관련
#define SEED_INIT 500 //함정 설치가능한 재화

//함정 관련
#define TRIANGLE_TRAP_DAMAGE 3
#define RECTANGLE_TRAP_DAMAGE 4
#define HEXAGON_TRAP_DAMAGE 6
#define STAR_TRAP_DAMAGE 10
#define TRAP_SET_UP_SEED 30//함정 설치 비용

//적 유닛 관련
#define Enemy_HP 3 //적 유닛 초기 hp

//본진 좌표 관련
#define NEXUS_XPOS 0
#define NEXUS_YPOS 0

//스테이지 관련
int stagestep = 1;
//본진 좌표 - 너비추가 해야 될지는 이번주 피드백을 통해... 탑뷰라 필요 없을 수도?

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
    Circle
} Shape;

typedef struct Trap {
    int damage;
    int xpos;
    int ypos;
    Shape shape;
}Trap;

typedef struct Enemy {
    int HP;
    int xpos;
    int ypos;
    Shape shape;
}Enemy;

typedef struct EnemySpawner {
    
};
void InitGame() {
    int difficultySelection;

    while (1) {
        printf("난이도를 선택하세요\n");
        printf("Easy : 1, Normal : 2, Hard : 3, Hell : 4\n");
        scanf("%d", &difficultySelection);
        player.Seed = SEED_INIT;
        if (difficultySelection >= 1 && difficultySelection <= 4) {
            switch (difficultySelection) {
            case 1:
                player.HP = EASY_PLAYER_HP;
                break;
            case 2:
                player.HP = NORMAL_PLAYER_HP;
                break;
            case 3:
                player.HP = HARD_PLAYER_HP;
                break;
            case 4:
                player.HP = HELL_PLAYER_HP;
                break;
            }
            InitNexus();
            break;
        }
        else {
            printf("잘못된 입력입니다.\n");
            printf("올바른 입력을 다시 입력하십시오\n");
        }
    }
}

int TrapSignal() {//트랩 설치 신호를 보냄
    int randomValue = rand() % 100 + 1;

    if (randomValue <= 45)
        return 1;
    else if (randomValue <= 80)
        return 2;
    else if (randomValue <= 95)
        return 3;
    else
        return 4;
}
Trap InitTrap() { //신호에 맞는 트랩 생성
    int trapSignal = TrapSignal();
    Trap trap;
    switch (trapSignal) {
    case 1:
        trap = { TRIANGLE_TRAP_DAMAGE ,TRIANGLE };
        break;
    case 2:
        trap = { RECTANGLE_TRAP_DAMAGE,RECTANGLE };
        break;
    case 3:
        trap = { HEXAGON_TRAP_DAMAGE,HEXAGON };
        break;
    case 4:
        trap = { STAR_TRAP_DAMAGE,STAR };
    }
    return trap;
}
void EnemySpawn() { //적 유닛 생성함수
    //스폰 위치 지정 필요
    Enemy enemy;
    enemy.shape = Circle;
    if (stagestep == 1) {//wave 내일 물어본 뒤 수정할 예정
        enemy.HP = 3;
    }
}

void EnemyMove() { //적 유닛 이동함수
    //알고리즘 파악해서 방향 설정뒤에 이동 ->input에서 하지 않을까?
}
void EnemyDamage(Enemy* enemy,Trap* trap) {//적 유닛이 함정을 지나가면 데미지를 입는다.
    if (isCrashWithTrap(enemy, trap)) {
        enemy->HP -= trap->damage;
    }
}

bool isCrashWithPlayer(Enemy*enemy,Player* player) {
    if ((player->nexus.xpos == enemy->xpos) && (player->nexus.ypos == enemy->ypos))
        return true;
    return false;
}

bool isCrashWithTrap(Enemy* enemy, Trap* trap) {
    if ((enemy->xpos == trap->xpos) && (enemy->ypos == trap->ypos))
        return true;
    return false;
}
void TrapUpgrade() {//업그레이드 알고리즘 사용

}

void PlayerHPDecrease(Enemy*enemy,Player*player) {
    if (isCrashWithPlayer(enemy, player))
        player->HP--;
}

void IsNextStage() {
    stagestep++;
}

void InitNexus() {
    player.nexus.xpos = NEXUS_XPOS;
    player.nexus.ypos = NEXUS_YPOS;
}
int main() {
   
}
//유닛 생성 및 삭제 malloc free써야되나?, wave가 stage개념인지 물어보고, DPS니까 delay가 있는건지 물어봐야 되고->적 유닛 속도도 고려해야 될까?