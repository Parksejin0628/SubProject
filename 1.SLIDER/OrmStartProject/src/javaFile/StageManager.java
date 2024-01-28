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
	static String stage[] = new String[16];	//�о�� ������ ���ڿ��� ������ ����
	static String stageFirst[] = new String[16];
	Image bufferImage;
	Graphics screenGraphic;
	int playerX = 200;					//�÷��̾��� �� X��ǥ
	int playerY = 200;					//�÷��̾��� �� Y��ǥ
	int playerSpeed = 5;				//�÷��̾��� �����̴� �ӵ�
	int playerMoveDelay = 10;			//�÷��̾ �� �����Ӵ� ��ٷ��� �ϴ� �ð�(�������� �ӵ��� �������� �̵��� �ε巯������)
	static int level = 0;						//���� �������� ����
	int disappearBlockX = 0;
	int disappearBlockY = 0;
	static int leftKey = 0;
	int nowDir = -1;
	boolean turn = true;				//true�� ��� �÷��̾��� �����¿� �Է��� �޴´�.
	static boolean up = false;					//true�� ��� ���� �÷��̾ �������� �����̰� �ִ�
	static boolean down = false;				//true�� ��� ���� �÷��̾ �Ʒ������� �����̰� �ִ�
	static boolean left = false;				//true�� ��� ���� �÷��̾ �������� �����̰� �ִ�
	static boolean right = false;				//true�� ��� ���� �÷��̾ ���������� �����̰� �ִ�
	static boolean firstPlayerPos=false;		//true�� ��� �÷��̾��� ��ġ�� �������� �� ó�� ��ġ�� �ű�� false�� �ȴ�
	boolean mainScreen = true;			//true�� ��� ����ȭ�� �̹����� ����Ѵ�
	boolean howToPlay1 = false;			//true�� ��� ���ӹ��1 �̹����� ����Ѵ�
	boolean howToPlay2 = false;			//true�� ��� ���ӹ��2 �̹����� ����Ѵ�
	boolean game = false;				//true�� ��� �����÷��̰� �����ϴ�
	boolean clear = false;
	boolean disappear = false;
	static boolean crash = false;
	
	static Image mainImage = new ImageIcon("src/images/����ȭ��_����.png").getImage();
	static Image howToPlayImage1 = new ImageIcon("src/images/��������.png").getImage();
	static Image howToPlayImage2 = new ImageIcon("src/images/���Ӽ�����.png").getImage();
	static Image endingImage = new ImageIcon("src/images/����.png").getImage();
	static Image player_up = new ImageIcon("src/images/��ϻ�/player_up.png").getImage();
	static Image player_down = new ImageIcon("src/images/��ϻ�/player_down.png").getImage();
	static Image player_right = new ImageIcon("src/images/��ϻ�/player_right.png").getImage();
	static Image player_left = new ImageIcon("src/images/��ϻ�/player_left.png").getImage();
	static Image player_now = player_down;
	public Image block[] =
		{
			new ImageIcon("src/images/��ϻ�/black.png").getImage(),
			new ImageIcon("src/images/��ϻ�/white.png").getImage(),
			new ImageIcon("src/images/��ϻ�/red.png").getImage(),
			new ImageIcon("src/images/��ϻ�/player_down.png").getImage(),
			new ImageIcon("src/images/��ϻ�/yellow.png").getImage(),
			new ImageIcon("src/images/��ϻ�/orange.png").getImage(),
			new ImageIcon("src/images/��ϻ�/sky.png").getImage(),
			new ImageIcon("src/images/��ϻ�/sky.png").getImage(),
			new ImageIcon("src/images/��ϻ�/up.png").getImage(),
			new ImageIcon("src/images/��ϻ�/right.png").getImage(),
			new ImageIcon("src/images/��ϻ�/left.png").getImage(),
			new ImageIcon("src/images/��ϻ�/down.png").getImage()
		};		//��� �̹��� ����
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
		};		//�������� �ؽ�Ʈ���� ��� ����
	

	public void createFrame(String title)
	{
		this.setTitle(title); 					//������â ����
		this.setSize(800, 800 + 25);  			//�������� ũ�� ����
		this.setResizable(false);  				//����ڰ� ������ ũ�� ���� false
		this.setLocationRelativeTo(null);		//������ â�� ȭ�� ����� ������ ����
		this.setLayout(null);					//���̾ƿ��� ������� ������ �� �ֵ��� ����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//���������� JFrame�� ����ǵ��� ����
		this.setVisible(true);									//������ Ȱ��ȭ
		System.out.println("OK");
	}
	
	//�������� �ؽ�Ʈ ���Ͽ��� �ش� ���������� ������ �ҷ��´�.
	public void loadStage(FileReader stageTxt) throws IOException
	{
		BufferedReader br = new BufferedReader(stageTxt);			//������ �о�� ����
		
		
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
	
	//����ȭ���� ����Ѵ�. �������� ������� �� �˾Ƽ� ����Ǵ� �Լ��̴�.
	public void paint(Graphics g)
	{
		//System.out.printf("check1\n");
		bufferImage = createImage(800, 800 + 25);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0, 0, null);
	}
	
	//ȭ���� �������� �ε巴�� ���ش�.
	public void screenDraw(Graphics g) 
	{
		//System.out.println("ck1");
		if(mainScreen)
		{
			g.drawImage(mainImage, 0, 25, null); //����ȭ�� �̹��� ÷������
			return;
		}
		if(howToPlay1)
		{
			g.drawImage(howToPlayImage1, 0, 25, null); //���Ӽ���1 �̹��� ÷������
			return;
		}
		if(howToPlay2)
		{
			g.drawImage(howToPlayImage2, 0, 25, null); //���Ӽ���2 �̹��� ÷������
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
				if(stage[y].charAt(x)=='0')		//���ڰ� 0�� ��� ������ ��� ���
				{
					g.drawImage(block[0], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='W')	//���ڰ� W�� ��� �Ͼ�� ��� ���
				{
					g.drawImage(block[1], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='R')	//���ڰ� R�� ��� ������ ��� ���
				{
					g.drawImage(block[2], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='B')	//���ڰ� B�� ��� �Ķ��� ��� ���
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
				else if(stage[y].charAt(x)=='Y')	//���ڰ� Y�� ��� ����� ��� ���
				{
					g.drawImage(block[4], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='O')	//���ڰ� O�� ��� ��Ȳ�� ��� ���
				{
					g.drawImage(block[5], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='S')	//���ڰ� S�� ��� �ϴû� ��� ���
				{
					g.drawImage(block[6], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='P')	//���ڰ� P�� ��� ��ȫ�� ��� ���
				{
					g.drawImage(block[7], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='8')	//���ڰ� 8�� ��� ���� ��� ���
				{
					g.drawImage(block[8], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='6')	//���ڰ� 6�� ��� ������ ��� ���
				{
					g.drawImage(block[9], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='4')	//���ڰ� 4�� ��� ���� ��� ���
				{
					g.drawImage(block[10], 50*x, 25+50*y, null);
				}
				else if(stage[y].charAt(x)=='2')	//���ڰ� 2�� ��� �Ʒ��� ��� ���
				{
					g.drawImage(block[11], 50*x, 25+50*y, null);
				}
			}
		}
		g.drawImage(player_now, playerX, playerY, null);
		//System.out.printf("playerPos : %d %d\n", playerX, playerY);
		repaint();	
	}
	
	//�÷��̾��� �Է��� �޴� �Լ��̴�.
	public void playerCtrl() throws IOException
	{
		addKeyListener(new KeyAdapter() 	//�Է� �Ǵ�
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
						nextPage(); //�ʿ��� �޼��带 ������ �� 
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
	
	//�÷��̾�� ����� �浹�� Ȯ�����ִ� �Լ��̴�. judgeBlock�� �浹��������̸� judgeBlock = '����ڵ�'�� if������ ����� �ش� ��Ϻ� ����� �߰��� �� �ִ�. ����ڵ�� src//stage//��Ϻ� ���ڿ� �����ִ�.
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
	
	//�����̽��ٸ� ������ �� ����Ǵ� �Լ�
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

	
	//������ ������ �� ȣ���Ѵ�.
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



