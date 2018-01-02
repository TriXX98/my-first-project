/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.swing;

import com.oracle.nio.BufferSecrets;
import java.awt.AWTEventMulticaster;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import javafx.scene.layout.Border;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author tritu
 */
public class notepad extends JFrame {

    JMenuBar bar;
    JMenu menuFile, menuEdit, menuView, menuHelp, MenuFomat;
    JMenuItem itemNew, itemOpen, itemSave, itemExit,
            itemCopy, itemPaste, itemUndo, itemCut, itemRedo;

    JTextArea area;
    JFileChooser chooser = new JFileChooser();

    public notepad(String title) {
        super(title);
        addControlls();
        addEvent();

    }

    private void addControlls() {
        bar = new JMenuBar();
        this.setJMenuBar(bar);

        menuFile = new JMenu("FILE");
        bar.add(menuFile);
        menuEdit = new JMenu("EDIT");
        bar.add(menuEdit);

        itemNew = new JMenuItem("New");
        itemOpen = new JMenuItem("Open");
        itemSave = new JMenuItem("Save");
        itemExit = new JMenuItem("Exit");
        menuFile.add(itemNew);
        menuFile.addSeparator();
        menuFile.add(itemOpen);
        menuFile.addSeparator();
        menuFile.add(itemSave);
        menuFile.addSeparator();
        menuFile.add(itemExit);

        itemCopy = new JMenuItem("Copy");
        itemCopy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        itemPaste = new JMenuItem("Paste");
        itemPaste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        itemUndo = new JMenuItem("Undo");
        itemUndo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        itemCut = new JMenuItem("Cut");
        itemCut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        itemRedo = new JMenuItem("Redo");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        menuEdit.add(itemUndo);
        menuEdit.addSeparator();
        menuEdit.add(itemCopy);
        menuEdit.addSeparator();
        menuEdit.add(itemPaste);
        menuEdit.addSeparator();
        menuEdit.add(itemRedo);
        menuEdit.addSeparator();
        menuEdit.add(itemCut);

        MenuFomat = new JMenu("FORMAT");
        bar.add(MenuFomat);

        menuView = new JMenu("VIEW");
        bar.add(menuView);

        menuHelp = new JMenu("HELP");
        bar.add(menuHelp);

        Container container = new Container();
        container.setLayout(new BorderLayout());

        area = new JTextArea();
        JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        container.add(pane, BorderLayout.CENTER);
        this.add(pane);

    }

    private void addEvent() {

        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                xulyThoat();
            }

            private void xulyThoat() {

                int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát", "Hộp thoại hỏi thoát",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if (ret == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });

        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }

            private void newFile() {
                int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn tạo file mới không", "Hộp thoại hỏi tạo mới", JOptionPane.YES_NO_CANCEL_OPTION);

                if (ret == JOptionPane.NO_OPTION) {
                    area.setText("");
                } else if (ret == JOptionPane.YES_OPTION) {
                    saveFile();
                } else {
                    return;
                }
            }
        });

        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }

            private void openFile() {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File openFile = chooser.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(openFile)));
                        String line = br.readLine();
                        StringBuffer buffer = new StringBuffer();
                        while (line != null) {
                            buffer.append(line + "\n");
                            line = br.readLine();

                        }
                        area.setText(buffer.toString());
                        br.close();

                    } catch (Exception e) {
                        e.getMessage();
                    }

                }

            }
        });

        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

    }

    private void saveFile() {

        String line = area.getText();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu file", "Hỏi lưu file", JOptionPane.YES_NO_CANCEL_OPTION);
            if (ret == JOptionPane.NO_OPTION || ret == JOptionPane.CANCEL_OPTION) {
                return;
            }

            try {

                PrintWriter pw = new PrintWriter(new FileOutputStream(selectedFile));
                pw.println(line);
                pw.close();
                JOptionPane.showMessageDialog(null, "Lưu File Thành Công");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showWindow() {

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
