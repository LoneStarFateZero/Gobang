package pers.lonestar.gobang.maingui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GobangLauncher {
	BufferedImage table;
	BufferedImage black;
	BufferedImage white;
	BufferedImage selected;

	//定义先后手
	private boolean order = true;
	//定义棋盘的大小
	private static int BOARD_SIZE = 15;
	//定义棋盘宽高的像素
	private final int TABLE_WIDTH = 660;
	private final int TABLE_HEIGHT = 660;
	//定义棋盘坐标的像素值和棋盘数组之间的比率
	private final int RATE = TABLE_WIDTH / BOARD_SIZE;
	//定义棋盘坐标的像素值和棋盘数组之间的偏移距离
	private final int X_OFFSET = 0;
	private final int Y_OFFSET = 0;
	//定义一个二维数组来充当棋盘
	private byte[][] board = new byte[BOARD_SIZE][BOARD_SIZE];
	//五子棋游戏的窗口
	JFrame f = new JFrame("五子棋游戏");
	//五子棋游戏棋盘对应的Canvas组件
	ChessBoard chessBoard = new ChessBoard();
	//选中点的坐标
	private int selectedX = -1;
	private int selectedY = -1;

	public void init() throws Exception {

		table = ImageIO.read(getClass().getResource("/resource/image/board.png"));
		black = ImageIO.read(getClass().getResource("/resource/image/black.png"));
		white = ImageIO.read(getClass().getResource("/resource/image/white.png"));
		selected = ImageIO.read(getClass().getResource("/resource/image/selected.png"));

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
			}
		}

		chessBoard.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		chessBoard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				selectedX = -1;
				selectedY = -1;
				chessBoard.repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//将用户鼠标点击事件转换成棋子数组的坐标
				int xPos = (e.getX() - X_OFFSET) / RATE;
				int yPos = (e.getY() - Y_OFFSET) / RATE;
				//轮到黑棋
				if (order && board[xPos][yPos] == 0) {
					board[xPos][yPos] = 1;
					order = false;
				} else if (!order && board[xPos][yPos] == 0) {
					board[xPos][yPos] = -1;
					order = true;
				}
				/**
				 * 电脑随机生成两个随机数
				 */
				chessBoard.repaint();
			}
		});

		chessBoard.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				selectedX = (e.getX() - X_OFFSET) / RATE;
				selectedY = (e.getY() - Y_OFFSET) / RATE;
				chessBoard.repaint();
			}
		});

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.add(chessBoard);
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
	}

	class ChessBoard extends JPanel {
		@Override
		public void paint(Graphics g) {
			//绘制五子棋棋盘
			g.drawImage(table, 0, 0, null);
			//绘制选中点的红框
			if (selectedX >= 0 && selectedY >= 0) {
				g.drawImage(selected, selectedX * RATE + X_OFFSET, selectedY * RATE + Y_OFFSET, null);
			}
			//遍历数组，绘制棋子
			for (int i = 0; i < BOARD_SIZE; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					//绘制黑棋
					if (board[i][j] == 1) {
						g.drawImage(black, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					//绘制白棋
					else if (board[i][j] == -1) {
						g.drawImage(white, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		GobangLauncher gbl = new GobangLauncher();
		gbl.init();
	}
}
