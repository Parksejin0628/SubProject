#pragma once
#include"Unit_algorithm.h"

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
Enemy EnemySpawn() { //적 유닛 생성함수
    //스폰 위치 지정 필요, 입력이랑 말 맞춰야 되야 겠지?
    //보스 스테이지가 6 11 16 21 에 생성 only 한마리만
    if ((stagestep - 1) / 5 >= 1) {
        Enemy boss;
        boss.shape = Diamond;
        boss.HP = (ENEMY_HP + stagestep - 1) * 2;
        boss.isActive = true;
        boss.isBoss = true;
        return boss;
    }
    else {
        for (int i = 0; i < ENEMY_INIT_NUMBER + ((stagestep - 1) / 3) * 5; i++) {
            Enemy enemy;
            enemy.shape = Circle;
            enemy.HP = ENEMY_HP + stagestep - 1;
            enemy.isActive = true;
            enemy.isBoss = false;
            return enemy;
        }
    }
    
}
void EnemyMove() { //적 유닛 이동함수
    //알고리즘 파악해서 방향 설정뒤에 이동 ->input에서 하지 않을까?
}

bool isCrashWithPlayer(Enemy*enemy,Player* player) { //적과 본진 충돌
    if ((player->nexus.xpos == enemy->xpos) && (player->nexus.ypos == enemy->ypos))
        return true;
    return false;
}

void PlayerHPDecrease(Enemy* enemy, Player* player) {
    if (isCrashWithPlayer(enemy, player))
        player->HP--;
}

bool isCrashWithTrap(Enemy* enemy, Trap* trap) {//적과 함정 출돌
    if ((enemy->xpos == trap->xpos) && (enemy->ypos == trap->ypos))
        return true;
    return false;
}

void EnemyDamage(Enemy* enemy, Trap* trap) {//적 유닛이 함정을 지나가면 데미지를 입는다.
    if (isCrashWithTrap(enemy, trap)) {
        enemy->HP -= trap->damage;
        if (enemy->HP <= 0) {
            enemy->isActive = false;
            player.Gold += REWARD_GOLD;
            SeedReward();
        }
    }
}

void TrapUpgrade() {//업그레이드 알고리즘 사용

}


void SeedReward() { //확률적으로 Seed 획득이라 임의로 50으로 설정함
    int randomValue = rand() % 100 + 1;

    if (randomValue <= RANDOM_REWARD_SEED) {
        player.Seed += REWARD_SEED;
    }
}

//int main() {
//    srand(time(NULL));
//    Enemy enemies[MAX_ENEMY];
//    while (/*게임이 돌아가는 동안*/) {
//        for (int i = 0; i < ENEMY_INIT_NUMBER + ((stagestep - 1) / 3) * 5; i++) {
//            if (enemies[i].isActive) {
//            // 렌더링.. 
//            }
//        }
//    }
//}