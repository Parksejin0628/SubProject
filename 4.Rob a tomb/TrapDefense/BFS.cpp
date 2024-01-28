#include "BFS.h"
#include "Queue.h"

int is_movable(int maze[WIDTH][HEIGHT], int visited[WIDTH][HEIGHT], MapPosition pos)
{
	// 현재 위치가 미로의 범위를 벗어나는 지 확인
  if (pos.x < 0 || pos.y < 0 || pos.x >= WIDTH || pos.y >= HEIGHT)
			return (false);
	// 현재 위치가 벽이거나 이미 방문한 곳인지 확인
  if (maze[pos.y][pos.x] == WALL || maze[pos.y][pos.x] == VISIT)
	    return (false);
	return (true);
}

// BFS 
//map는 int형으로 받아온다고 되어있지만, 추후 맵 좌표 구조체로 변경예정 
MapPosition findPath(int map[WIDTH][HEIGHT], int visited[WIDTH][HEIGHT], MapPosition start, MapPosition end)
{
	Queue queue;
	MapPosition currPos;
    MapPosition nextPos;
    int nextX;
    int nextY;
	
		enqueue(&queue, start); // 처음 시작 위치를 큐에 넣기
		while (!isEmpty(&queue))
		{
			currPos = dequeue(&queue); // 큐에서 꺼내기
			if ((currPos.x = end.x) && (currPos.y == end.y)) 			// 도착 지점에 도달한 경우
		    	return currPos;
			if (visited[currPos.x][currPos.y] == VISIT) 	// 현재 위치를 방문했는지 확인
				continue ;
	      visited[currPos.x][currPos.y] = VISIT; // 현재 위치를 VISIT 으로 변경
	      
		  while (currPos.direction < NUM_DIRECTIONS)
	      {
	          // 다음 좌표 설정
	          nextX = currPos.x + DIRECTION_OFFSETS[currPos.direction][0];
	          nextY = currPos.y + DIRECTION_OFFSETS[currPos.direction][1];
	          nextPos.x = nextX;
	          nextPos.y = nextY;
	          nextPos.direction = 0;
	          if(is_movable(map, visited, nextPos))
				{
					// 다음 이동 지점에 이전 이동 거리 + 1 저장
					map[nextX][nextY] = map[currPos.x][currPos.y] + 1; 
	            	enqueue(&queue, nextPos); // 다음 이동 지점 큐에 저장
				}
	          start.direction += 1;
	      }
		}
}