package com.shikk.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TestList extends JFrame {
    private String[] flavors = { "A", "B", "C", "D", "E", "F" };
    private DefaultListModel lItems = new DefaultListModel();
    private JList lst = new JList(lItems);
    private JTextArea t = new JTextArea(flavors.length, 20);
    private JButton b = new JButton("Add Item");
    private ActionListener bl = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (count < flavors.length)
                lItems.add(0, flavors[count++]);
            else
                b.setEnabled(false);
        }
    };
    private ListSelectionListener lsl = new ListSelectionListener() {
        @SuppressWarnings("deprecation")
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting())
                return;
            t.setText("");
            for (Object item : lst.getSelectedValues())
                t.append(item + "\n");
        }
    };
    private int count = 0;
    public TestList() {
        t.setEditable(true);
        setLayout(new FlowLayout());
        Border brd = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.black);
        lst.setBorder(brd);
        t.setBorder(brd);
        for (int i = 0; i < 4; i++)
            lItems.addElement(flavors[count++]);
        add(t);
        add(lst);
        add(b);
        b.addActionListener(bl);
        lst.addListSelectionListener(lsl);
    }
    public static void main(String[] args) {
       new TestList().setVisible(true);;
    }
}