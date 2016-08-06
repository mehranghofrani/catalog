/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import db.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static Utilities.EmailUtils.isValidEmailAddress;
import static java.lang.Math.abs;


public class JavaApplication4
{


    private JLabel infoLable;
    private JButton submitBtn;
    private JLabel statusLabel;
    private GhostText ghostText;
    private TouchJTextField emailInputField;
    private JLabel emailLabel;

    private JPanel keyboardPanel;
    private JPanel mainPanel;

    public JavaApplication4()
    {
        initComponents();
    }

    private void initComponents()
    {
        final DBManager dbManager = null;
//        dbManager = new DBManager();
        JFrame frm = new JFrame();


        listLookAndFeels();
        String nameSnippet = "NimbusLookAndFeel".toLowerCase();
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : plafs)
        {
            if (info.getClassName().toLowerCase().contains(nameSnippet))
            {
                try
                {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                } catch (InstantiationException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e)
                {
                    e.printStackTrace();
                }
            }
        }


        frm.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        frm.setLocationRelativeTo(null);
        frm.setUndecorated(true);
        frm.setLayout(null);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane lp = frm.getLayeredPane();

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setSize(frm.getSize());
        mainPanel.setLocation(0, 0);

        keyboardPanel = new JPanel();
        keyboardPanel.setSize(mainPanel.getWidth(), mainPanel.getHeight() / 2);
        keyboardPanel.setLocation(0, mainPanel.getHeight() / 2);
        keyboardPanel.setBackground(Color.BLUE);
        keyboardPanel.setVisible(false);


        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        initializeLayout(mainPanel);
        GridBagConstraints c = new GridBagConstraints();
        Font headingFont = new Font("B Nazanin", Font.CENTER_BASELINE, 20);
        Font bodyFont = headingFont.deriveFont(14.0f);

        c.ipady = 20;
        c.insets = new Insets(10, 0, 30, 0);
        c.weighty = 0;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        String infoMsg = "در صورت تمایل، ایمیل خود را برای دریافت کاتالوگ وارد نمایید";
        infoLable = new JLabel(infoMsg);
        infoLable.setFont(headingFont);
        mainPanel.add(infoLable, c);

        c.insets = new Insets(10, 0, 0, 20);
        c.ipadx = 0;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        emailLabel = new JLabel("ایمیل:");
        emailLabel.setFont(bodyFont);
        mainPanel.add(emailLabel, c);

        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        emailInputField = new TouchJTextField(this);
        ghostText = new GhostText(emailInputField, "example@host.com");
        mainPanel.add(emailInputField, c);
//        KeyBoard kb = new KeyBoard(frm);


        submitBtn = new JButton("ارسال");
        submitBtn.setFont(bodyFont);
        c.ipadx = 100;
        c.ipady = 0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.CENTER;
        mainPanel.add(submitBtn, c);

        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        statusLabel = new JLabel(" ");
        statusLabel.setFont(bodyFont.deriveFont(18.0f));
        mainPanel.add(statusLabel, c);

        submitBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isValidEmailAddress(ghostText.isEmpty() ? "" : emailInputField.getText()))
                {
                    String tempEmail = emailInputField.getText();
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            dbManager.addEmail(tempEmail);
                            EmailSend.send(tempEmail);
                        }
                    }).start();
                    setVisibleAll(false);
                    statusLabel.setText("<html>کاتالوگ به آدرس " + tempEmail + " <font color='green'>ارسال شد</font></html>");
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(7000);
                            } catch (InterruptedException e1)
                            {
                                e1.printStackTrace();
                            }
                            resetStatusLabel();
                            emailInputField.setText("");
                            setVisibleAll(true);
                            emailInputField.requestFocus();
                            submitBtn.requestFocus();
                        }
                    }).start();
                } else
                {
                    statusLabel.setText("<html>ایمیل <font color='red'>اشتباه</font> وارد شده است</html>");
                }


            }
        });
        emailInputField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                resetStatusLabel();
            }

            @Override
            public void focusLost(FocusEvent e)
            {

            }
        });
        lp.add(mainPanel, new Integer(1));
        lp.add(keyboardPanel, new Integer(2));
        frm.setVisible(true);
        submitBtn.requestFocus();
    }

    public static void main(String[] args)
    {

        JavaApplication4 javaApplication4 = new JavaApplication4();
    }

    private void resetStatusLabel()
    {
        statusLabel.setText(" ");
    }

    private void setVisibleAll(boolean b)
    {
        infoLable.setVisible(b);
        emailInputField.setVisible(b);
        emailLabel.setVisible(b);
        submitBtn.setVisible(b);
    }

    private static void listLookAndFeels()
    {
        UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : plafs)
        {
            System.out.println(info.getClassName());
        }
    }

    private static void initializeLayout(JPanel layout)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        for (int i = 0; i < 4; i++)
        {
            c.gridx = i;
            JLabel temp = new JLabel("");
            layout.add(temp, c);
        }

        c.gridx = 0;
        for (int i = 0; i < 3; i++)
        {
            c.gridy = i;
            JLabel temp = new JLabel("");
            layout.add(temp, c);
        }
    }


    public void showKeyBoard()
    {
        keyboardPanel.setVisible(true);
        int freeSpace = (mainPanel.getHeight() - keyboardPanel.getHeight());
        int offset = freeSpace / 2 - emailInputField.getY();
        System.out.println("offset: " + offset);
        if (offset > 0)
        {
            mainPanel.setLocation(0, 0);
        } else if (abs(offset) + freeSpace > mainPanel.getHeight())
        {
            mainPanel.setLocation(0, freeSpace - mainPanel.getHeight());
        } else
            mainPanel.setLocation(0, offset);
    }

    public void hideKeyBoard()
    {
        keyboardPanel.setVisible(false);
        mainPanel.setLocation(0, 0);
    }
}
