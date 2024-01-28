package javaFile;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

/*import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;*/
import java.io.*;

public class StageMakingTest extends JFrame
{
	//색깔별 블록 추가
	public Image block[] =
	{
		new ImageIcon("src/images/블록색/black.png").getImage(),
		new ImageIcon("src/images/블록색/white.png").getImage(),
		new ImageIcon("src/images/블록색/red.png").getImage(),
		new ImageIcon("src/images/블록색/blue.png").getImage(),
		new ImageIcon("src/images/블록색/yellow.png").getImage(),
		new ImageIcon("src/images/블록색/orange.png").getImage(),
		new ImageIcon("src/images/블록색/sky.png").getImage(),
		new ImageIcon("src/images/블록색/up.png").getImage(),
		new ImageIcon("src/images/블록색/right.png").getImage(),
		new ImageIcon("src/images/블록색/left.png").getImage(),
		new ImageIcon("src/images/블록색/down.png").getImage()
	};
	
	//프레임 설정
	public void createFrame()
	{
		setTitle("스테이지제작테스트"); 			//윈도우창 제목
		setSize(800, 800 + 25);  			//프레임의 크기 설정
		setResizable(false);  				//사용자가 프레임 크기 조정 false
		setVisible(true);					//윈도우창이 보이도록 설정
		setLocationRelativeTo(null);		//윈도우 창이 화면 가운데로 오도록 설정
		setLayout(null);					//레이아웃을 내맘대로 설정할 수 있도록 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//정상적으로 JFrame이 종료되도록 설정
	}

	//스테이지 제작
	public void paint(Graphics g) 
	{
		BufferedReader br = null;			//파일을 읽어올 변수
		String line = null;					//읽어온 파일에서 문장을 저장할 변수
		try  //파일 입출력 관련 내용을 사용할 때는 try catch문을 사용해야 한다.
		{
		br = new BufferedReader(new FileReader("src/stage/stage8.txt"));	//stage1 텍스트 파일을 불러옴
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		for(int y=0 ; y<16 ; y++)
		{
			try
			{
			line = br.readLine();			//stage1 텍스트 파일에서 각 문장을 읽어옴
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			if(line == null)	break;
			//								//읽어온 문장에서 x번째 글자가 무엇인지에 따라 50*x, 25 + 50*y 좌표에 해당 블록 이미지를 출력한다.
			for(int x=0 ; x<16 ; x++)
			{
				if(line.charAt(x)=='0')		//글자가 0일 경우 검은색 블록 출력
				{
					g.drawImage(block[0], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='W')	//글자가 W일 경우 하얀색 블록 출력
				{
					g.drawImage(block[1], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='R')	//글자가 B일 경우 빨간색 블록 출력
				{
					g.drawImage(block[2], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='B')	//글자가 R일 경우 파란색 블록 출력
				{
					g.drawImage(block[3], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='Y')	//글자가 Y일 경우 노란색 블록 출력
				{
					g.drawImage(block[4], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='O')	//글자가 O일 경우 주황색 블록 출력
				{
					g.drawImage(block[5], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='S')	//글자가 S일 경우 하늘색 블록 출력
				{
					g.drawImage(block[6], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='P')	//글자가 P일 경우 분홍색 블록 출력
				{
					g.drawImage(block[7], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='8')	//글자가 8일 경우 위쪽 블록 출력
				{
					g.drawImage(block[8], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='6')	//글자가 6일 경우 오른색 블록 출력
				{
					g.drawImage(block[9], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='4')	//글자가 4일 경우 왼쪽 블록 출력
				{
					g.drawImage(block[10], 50*x, 25+50*y, null);
				}
				else if(line.charAt(x)=='2')	//글자가 2일 경우 아래색 블록 출력
				{
					g.drawImage(block[11], 50*x, 25+50*y, null);
				}
			}
		}
		try
		{
			br.close();					//stage1파일 종료
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		StageMakingTest smt = new StageMakingTest();
		smt.createFrame();
	}

}
