package javaFile;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import java.io.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StageManager extends JFrame
{
	static StageManager stageManager = new StageManager();
	SoundManager soundManager = new SoundManager();
	SpecialBlock specialBlock = new SpecialBlock();
	SpecialBlock.NormalBlock normalBlock= specialBlock.new NormalBlock();
	SpecialBlock.DisappearBlock disappearBlock= specialBlock.new DisappearBlock();
	SpecialBlock.ShootBlock shootBlock= specialBlock.new ShootBlock();
	ThornBlock thornBlock = new ThornBlock();
	ObjectBlock objectBlock = new ObjectBlock();
	ObjectBlock.DoorBlock doorBlock = objectBlock.new DoorBlock();
	ObjectBlock.KeyBlock keyBlock = objectBlock.new KeyBlock();
	static String stage[] = new String[16];	//읽어온 파일의 문자열을 저장할 변수
	static String stageFirst[] = new String[16];
	Image bufferImage;
	Graphics screenGraphic;
	int playerX = 200;					//플레이어의 현 X좌표
	int playerY = 200;					//플레이어의 현 Y좌표
	int playerSpeed = 5;				//플레이어의 움직이는 속도
	int playerMoveDelay = 10;			//플레이어가 각 움직임당 기다려야 하는 시간(낮을수록 속도가 빨라지고 이동이 부드러워진다)
	static int level = 0;						//현재 스테이지 레벨
	int disappearBlockX = 0;
	int disappearBlockY = 0;
	static int leftKey = 0;
	int nowDir = -1;
	boolean turn = true;				//true일 경우 플레이어의 상하좌우 입력을 받는다.
	static boolean up = false;					//true일 경우 현재 플레이어가 위쪽으로 움직이고 있다
	static boolean down = false;				//true일 경우 현재 플레이어가 아래쪽으로 움직이고 있다
	static boolean left = false;				//true일 경우 현재 플레이어가 왼쪽으로 움직이고 있다
	static boolean right = false;				//true일 경우 현재 플레이어가 오른쪽으로 움직이고 있다
	static boolean firstPlayerPos=false;		//true일 경우 플레이어의 위치를 스테이지 맨 처음 위치로 옮기고 false가 된다
	boolean mainScreen = true;			//true일 경우 메인화면 이미지를 출력한다
	boolean howToPlay1 = false;			//true일 경우 게임방법1 이미지를 출력한다
	boolean howToPlay2 = false;			//true일 경우 게임방법2 이미지를 출력한다
	boolean game = false;				//true일 경우 게임플레이가 가능하다
	boolean clear = false;
	boolean disappear = false;
	static boolean crash = false;
	
	static Image mainImage = new ImageIcon("src/images/메인화면_최종.png").getImage();
	static Image howToPlayImage1 = new ImageIcon("src/images/게임조작.png").getImage();
	static Image howToPlayImage2 = new ImageIcon("src/images/게임설명블록.png").getImage();
	static Image endingImage = new ImageIcon("src/images/엔딩.png").getImage();
	static Image player_up = new ImageIcon("src/images/블록색/player_up.png").getImage();
	static Image player_down = new ImageIcon("src/images/블록색/player_down.png").getImage();
	static Image player_right = new ImageIcon("src/images/블록색/player_right.png").getImage();
	static Image player_left = new ImageIcon("src/images/블록색/player_left.png").getImage();
	static Image player_now = player_down;
	public Image block[] =
		{
			new ImageIcon("src/images/블록색/black.png").getImage(),
			new ImageIcon("src/images/블록색/white.png").getImage(),
			new ImageIcon("src/images/블록색/red.png").getImage(),
			new ImageIcon("src/images/블록색/player_down.png").getImage(),
			new ImageIcon("src/images/블록색/yellow.png").getImage(),
			new ImageIcon("src/images/블록색/orange.png").getImage(),
			new ImageIcon("src/images/블록색/sky.png").getImage(),
			new ImageIcon("src/images/블록색/sky.png").getImage(),
			new ImageIcon("src/images/블록색/up.png").getImage(),
			new ImageIcon("src/images/블록색/right.png").getImage(),
			new ImageIcon("src/images/블록색/left.png").getImage(),
			new ImageIcon("src/images/블록색/down.png").getImage()
		};		//블록 이미지 모음
	public String stageTxt[] =
		{
				"src/stage/stage1.txt",
				"src/stage/stage2.txt",
				"src/stage/stage3.txt",
				"src/stage/stage4.txt",
				"src/stage/stage5.txt",
				"src/stage/stage6.txt",
				"src/stage/stage7.txt",
				"src/stage/stage8.txt"
		};		//스테이지 텍스트파일 경로 모음
	

	public void createFrame(String title)
	{
		this.setTitle(title); 					//윈도우창 제목
		this.setSize(800, 800 + 25);  			//프레임의 크기 설정
		this.setResizable(false);  				//사용자가 프레임 크기 조정 false
		this.setLocationRelativeTo(null);		//윈도우 창이 화면 가운데로 오도록 설정
		this.setLayout(null);					//레이아웃을 내맘대로 설정할 수 있도록 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//정상적으로 JFrame이 종료되도록 설정
		this.setVisible(true);									//프레임 활성화
		System.out.println("OK");
	}
	
	//스테이지 텍스트 파일에서 해당 스테이지의 정보를 불러온다.
	public void loadStage(FileReader stageTxt) throws IOException
	{
		BufferedReader br = new BufferedReader(stageTxt);			//파일을 읽어올 변수
		
		
		for(int y=0 ; y<16 ; y++)
		{
			stage[y] = br.readLine();
			stageFirst[y] = stage[y];
		}
		for(int y=0 ; y<16 ; y++)
		{
			for(int x=0 ; x<16 ; x++)
			{
				if(stage[y].charAt(x)=='O')
				{
					leftKey++;
				}
			}
		}
		player_now = player_down;
		
		//System.out.printf("check4\n");
		br.close();
	}
	
	//게임화면을 출력한다. 프레임이 만들어질 떄 알아서 실행되는 함수이다.
	public void paint(Graphics g)
	{
		//System.out.printf("check1\n");
		bufferImage = createImage(800, 800 + 25);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0, 0, null);
	}
	
	//화면의 움직임이 부드럽게 해준다.
	public void screenDraw(Graphics g) 
	{
		//System.out.println("ck1");
		if(mainScreen)
		{
			g.drawImage(mainImage, 0, 25, null); //메인화면 이미지 첨가예정
			return;
		}
		if(howToPlay1)
		{
			g.drawImage(howToPlayImage1, 0, 25, null); //게임설명1 이미지 첨가예정
			return;
		}
		if(howToPlay2)
		{
			g.drawImage(howToPlayImage2, 0, 25, null); //게임설명2 이미지 첨가예정
			return;
		}
		if(level >= 8)
		{
			clear = true;
			g.drawImage(endingImage, 0, 25 ,null);
			System.out.println("clear!");
			return;
		}
		for(int y=0 ; y<16 ; y++)
		{
			for(int x=0 ; x<16 ; x++)
			{
				if(stage[y].charAt(x)=='0')		//글자가 0일 경우 검은색 블록 출력
				{
					g.drawImage(block[0], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='W')	//글자가 W일 경우 하얀색 블록 출력
				{
					g.drawImage(block[1], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='R')	//글자가 R일 경우 빨간색 블록 출력
				{
					g.drawImage(block[2], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='B')	//글자가 B일 경우 파란색 블록 출력
				{
					if(!firstPlayerPos)
					{
					g.drawImage(block[3], 50*x, 25+50*y, null);
					playerX = 50*x;
					playerY = 25 + 50*y;
					firstPlayerPos = true;
					player_now = player_down;
					nowDir = -1;
					}
					else
					{
						g.drawImage(block[0], 50*x, 25+50*y, null);
					}
					
				}
				else if(stage[y].charAt(x)=='Y')	//글자가 Y일 경우 노란색 블록 출력
				{
					g.drawImage(block[4], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='O')	//글자가 O일 경우 주황색 블록 출력
				{
					g.drawImage(block[5], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='S')	//글자가 S일 경우 하늘색 블록 출력
				{
					g.drawImage(block[6], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='P')	//글자가 P일 경우 분홍색 블록 출력
				{
					g.drawImage(block[7], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='8')	//글자가 8일 경우 위쪽 블록 출력
				{
					g.drawImage(block[8], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='6')	//글자가 6일 경우 오른색 블록 출력
				{
					g.drawImage(block[9], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='4')	//글자가 4일 경우 왼쪽 블록 출력
				{
					g.drawImage(block[10], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='2')	//글자가 2일 경우 아래색 블록 출력
				{
					g.drawImage(block[11], 50*x, 25+50*y, null);
				}
			}
		}
		g.drawImage(player_now, playerX, playerY, null);
		//System.out.printf("playerPos : %d %d\n", playerX, playerY);
		repaint();	
	}
	
	//플레이어의 입력을 받는 함수이다.
	public void playerCtrl() throws IOException
	{
		addKeyListener(new KeyAdapter() 	//입력 판단
		{
			public void keyPressed(KeyEvent e) 
			{

				switch(e.getKeyCode()) 
				{
					case KeyEvent.VK_W:
						if(turn == true && nowDir !=0) {
						nowDir = 0;
						up = true;		
						turn = false;
						soundManager.soundOutPut("effect1");
						player_now = player_up;
						if(disappear)
						{
							disappearBlock.disappear(disappearBlockX, disappearBlockY, stage[disappearBlockY]);
							disappear = false;
						}
						}
						break;
					case KeyEvent.VK_S:
						if(turn == true && nowDir !=1) {
							nowDir = 1;
						down = true;
						turn = false;
						soundManager.soundOutPut("effect1");
						player_now = player_down;
						if(disappear)
						{
							disappearBlock.disappear(disappearBlockX, disappearBlockY, stage[disappearBlockY]);
							disappear = false;
						}
						}
						break;
					case KeyEvent.VK_A:
						if(turn == true && nowDir !=2) {
							nowDir = 2;
						left = true;
						turn = false;
						soundManager.soundOutPut("effect1");
						player_now = player_left;
						if(disappear)
						{
							disappearBlock.disappear(disappearBlockX, disappearBlockY, stage[disappearBlockY]);
							disappear = false;
						}
						}
						break;
					case KeyEvent.VK_D:
						if(turn == true && nowDir !=3) {
							nowDir = 3;
						right = true;
						turn = false;
						soundManager.soundOutPut("effect1");
						player_now = player_right;
						if(disappear)
						{
							disappearBlock.disappear(disappearBlockX, disappearBlockY, stage[disappearBlockY]);
							disappear = false;
						}
						}
						break;
					case KeyEvent.VK_SPACE:
						nextPage(); //필요한 메서드를 넣으면 됨 
					}
				}
				
		});
		
		while(true)
		{
			try
			{
				Thread.sleep(playerMoveDelay);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			judgeCrash();
			if(up)	playerY -= playerSpeed;
			else if(down)	playerY += playerSpeed;
			else if(left)	playerX -= playerSpeed;
			else if(right)	playerX += playerSpeed;
			super.repaint();
			//System.out.printf("%d %d\n", playerX, playerY);
		}
	}
	
	//플레이어와 블록의 충돌을 확인해주는 함수이다. judgeBlock이 충돌예정블록이며 judgeBlock = '블록코드'를 if문으로 만들면 해당 블록별 기능을 추가할 수 있다. 블록코드는 src//stage//블록별 문자에 나와있다.
	public void judgeCrash() throws IOException
	{
		int judgeBlockX = playerX;
		int judgeBlockY = playerY;
		char judgeBlock = '\0';
		
		if(up)	judgeBlockY -= playerSpeed - 1;
		else if(down)	judgeBlockY += playerSpeed + 50 - 1;
		else if(left)	judgeBlockX -= playerSpeed - 1;
		else if(right)	judgeBlockX += playerSpeed + 50 - 1;
		
		if(judgeBlockX<0 || judgeBlockX > 800)	thornBlock.ThornBlock();
		if(judgeBlockY<25 || judgeBlockY > 825)	thornBlock.ThornBlock();
		
		if(!crash)
		{
			judgeBlock = stage[(judgeBlockY-25)/50].charAt(judgeBlockX/50);
			//System.out.printf("playerPos : %d %d\n", playerX, playerY);
			//System.out.printf("judgeBlockPos : %d %d\n",judgeBlockX, judgeBlockY);
			//System.out.printf("judgeBlock : %c\n",judgeBlock);
			//if(judgeBlock!='0' && judgeBlock != 'B')	crash = true;
			if(judgeBlock == 'W')
			{
				normalBlock.NormalBlock();
			}
			else if(judgeBlock == 'R')
			{
				thornBlock.ThornBlock();
			}
			else if(judgeBlock == 'Y')
			{
				doorBlock.DoorBlock();
				/*System.out.printf("clear!\n");
				level++;
				stageManager.loadStage(new FileReader(stageTxt[level]));
				firstPlayerPos = false;*/
			}
			else if(judgeBlock == 'O')
			{
				keyBlock.KeyBlock(judgeBlockX / 50, (judgeBlockY - 25) / 50);
			}
			else if(judgeBlock == 'S')
			{
				crash = true;
				System.out.printf("checkS\n");
				soundManager.soundOutPut("crash");
				disappear = true;
				disappearBlockX = judgeBlockX / 50;
				disappearBlockY = (judgeBlockY - 25) / 50;
				
			}
			else if(judgeBlock == '2' ||judgeBlock == '4' ||judgeBlock == '6' ||judgeBlock == '8')
			{
				System.out.println("check shoot");
				shootBlock.ShootBlock(judgeBlock);
			}
		}
		if(crash)
		{
			up = false;
			down = false;
			left = false;
			right = false;
			turn = true;
			crash = false;
		}
		
		return;
	}
	
	//스페이스바를 눌렀을 때 실행되는 함수
	public void nextPage()
	{
		if(mainScreen)
		{
			mainScreen = false;
			howToPlay1 = true;
		}
		else if(howToPlay1)
		{
			howToPlay1 = false;
			howToPlay2 = true;
		}
		else if(howToPlay2)
		{
			howToPlay2 = false;
			game = true;
		}
		else if(clear)
		{
			System.exit(0);
		}
	}

	
	//게임을 시작할 떄 호출한다.
	public void startGame() throws IOException
	{
		stageManager.loadStage(new FileReader(stageTxt[level]));
		soundManager.soundOutPut("bgm");
		stageManager.createFrame("slider");
		stageManager.playerCtrl();
		
	}
	
	public static void main(String[] args) throws IOException
	{
		stageManager.startGame();
	}

}



