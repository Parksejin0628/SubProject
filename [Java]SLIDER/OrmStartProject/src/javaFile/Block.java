package javaFile;

import java.io.FileReader;
import java.io.IOException;

public class Block {
	public int blockX;
	public int blockY;
	SoundManager soundManager = new SoundManager();
	
	public void sound(String name)
	{
		soundManager.soundOutPut(name);
	}
	
	
}

class SpecialBlock extends Block{
	
	
	class NormalBlock{
		public void NormalBlock(){
			sound("crash");
			StageManager stageManager = new StageManager();
			stageManager.crash = true;
		}
	}
	
	class DisappearBlock{
		public void disappear(int x, int y, String line) {
			StageManager stageManager = new StageManager();
			
			blockX = x;
			blockY = y;
			
			System.out.printf("check blockX : %d, blockY : %d\n",blockX, blockY);
			String temp = line.substring(0, blockX) + "0" + line.substring(blockX+1, 16);
			sound("disappear");
			System.out.println(temp);
			stageManager.stage[blockY] = temp;
		}
	}
	
	class ShootBlock{
		public void ShootBlock(char dir) {
			StageManager stageManager = new StageManager();
			sound("effect1");
			if(dir == '2')
			{
				stageManager.up = false;
				stageManager.down = true;
				stageManager.left = false;
				stageManager.right = false;
				stageManager.player_now = stageManager.player_down;
			}
			else if(dir == '4')
			{
				stageManager.up = false;
				stageManager.down = false;
				stageManager.left = true;
				stageManager.right = false;
				stageManager.player_now = stageManager.player_left;
			}
			else if(dir == '6')
			{
				stageManager.up = false;
				stageManager.down = false;
				stageManager.left = false;
				stageManager.right = true;
				stageManager.player_now = stageManager.player_right;
			}
			else if(dir == '8')
			{
				stageManager.up = true;
				stageManager.down = false;
				stageManager.left = false;
				stageManager.right = false;
				stageManager.player_now = stageManager.player_down;
			}
		}
	}
	
	class ActiveBlock{
		public ActiveBlock() {
			boolean active = true;
		}
	}
}

class ThornBlock extends Block{
	public void ThornBlock(){
		StageManager stageManager = new StageManager();
		stageManager.crash = true;
		sound("effect2");
		for(int y=0 ; y<16 ; y++)
		{
			//System.out.println(stageManager.stageFirst[y]);
			stageManager.stage[y] = stageManager.stageFirst[y];
		}
		stageManager.firstPlayerPos = false;
	}
}

class ObjectBlock extends Block{
	
	class DoorBlock{
		public void DoorBlock() throws IOException {
			StageManager stageManager = new StageManager();
			stageManager.crash = true;
			
			
			if(stageManager.leftKey<=0)
			{
				//System.out.printf("clear!\n");
				sound("clear");
				stageManager.level++;
				if(stageManager.level<8)
				{
					stageManager.loadStage(new FileReader(stageManager.stageTxt[stageManager.level]));
					stageManager.firstPlayerPos = false;
				}
			}
		}
		
	}
	
	class KeyBlock{
		public void KeyBlock(int x, int y) {
			StageManager stageManager = new StageManager();
			blockX = x;
			blockY = y;
			
			String temp = stageManager.stage[blockY].substring(0, blockX) + "0" + stageManager.stage[blockY].substring(blockX+1, 16);
			stageManager.stage[blockY] = temp;
			sound("key");
			
			
			stageManager.leftKey--;
			System.out.printf("leftKey : %d\n",stageManager.leftKey);
		}
	}
}
