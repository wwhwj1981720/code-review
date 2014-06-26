package unicom;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;

import task.DoTaskFrame;
import tool.MyStatic;
import unicom.start.FrameTaskFun;
import unicom.start.FrameTaskRead;
import unicom.start.DosTaskRead;

import lex.CAnayase;
import lex.LexInterface;

public class CFrame extends Frame implements ActionListener {

	LexInterface lex;
	MenuBar mb = new MenuBar();// 菜单栏
	Menu fileMenu = new Menu("File");// 通常出现在菜单栏中的对象，它包含一个用户可以从中选择的操作列表。
	Menu actionMenu = new Menu("Project");
	MenuItem closeWindow = new MenuItem("Exit");// 构造具有指定的标签没有键盘快捷方式的的新菜单项。
	MenuItem openFile = new MenuItem("Open file");// 构造具有指定的标签没有键盘快捷方式的的新菜单项。
	MenuItem lexical_check = new MenuItem("Check");// 构造具有指定的标签没有键盘快捷方式的的新菜单项。

	TextArea text = new TextArea(5, 70); // 初始化textarea文本框 text
	TextArea error_text = new TextArea(20, 70); // 初始化textarea文本框 error_text
	JFileChooser fileChooser = new JFileChooser();
	File file = null;

	CFrame() {

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setLayout(new FlowLayout());

		/*
		 * 设置窗体类型 以及
		 */
		this.setMenuBar(mb);
		mb.add(fileMenu);
		mb.add(actionMenu);

		fileMenu.add(openFile);
		fileMenu.add(closeWindow);

		actionMenu.add(lexical_check);

		this.add(text);
		this.add(error_text);
		error_text.setText("Lexical Information: \n");
		closeWindow.addActionListener(this);
		openFile.addActionListener(this);
		lexical_check.addActionListener(this);
		this.setSize(600, 700);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	public TextArea getText() {
		return text;
	}

	public void setText(TextArea text) {
		this.text = text;
	}

	public TextArea getError_text() {
		return error_text;
	}

	public void setError_text(TextArea error_text) {
		this.error_text = error_text;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CFrame compiler = new CFrame();
	}

	// 定义actionPerformed事件处理方法
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeWindow) {
			System.exit(0);
		} else if (e.getSource() == openFile) {
			// file_dialog_load.setVisible(true);
			// 获得文件的路径和文件名
			// File myfile = new File(file_dialog_load.getDirectory());
			int preesButton = fileChooser.showOpenDialog(this);
			if (preesButton == fileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				text.setText(file.getAbsolutePath());
			}

		} else if (e.getSource() == lexical_check) {

			error_text.setText("");
			String input = text.getText();
			String mdr = "E:\\versionfunction\\cpp";
			mdr = file.getAbsolutePath();
			String suffix = "all";

			System.out.println(mdr + "|" + suffix);
			MyStatic.readFileSet("binary/file.txt");

			//FrameTaskRead rd = new FrameTaskRead(mdr, this);
			FrameTaskFun rd=new FrameTaskFun(mdr,this);
			rd.scanDirectry(mdr);
			try {
				rd.travelMap(suffix);
			} catch (Exception ee) {
				ee.printStackTrace();
			}
			System.out.println("end");
			// rd.getTotalresult().close();
			rd.shutDown();
			error_text.append("end");

		}
	}

}
