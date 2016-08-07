/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mactabi
 */
public class KeyBoard extends JPanel
{
    static Runnable deleter;
    JavaApplication4 parent;
    public static boolean deleterActive = false;
    public static boolean firstCharDelay = true;

    public KeyBoard(JavaApplication4 javaApplication4, Dimension size, final JTextField txt, EnterActionPerformListener enterActionPerformListener)
    {

        deleter = new Runnable()
        {
            @Override
            public void run()
            {
                while (KeyBoard.deleterActive == true)
                {
                    parent.setListenToKeyboardShow(false);
                    txt.requestFocusInWindow();
                    parent.setListenToKeyboardShow(true);
                    if (KeyBoard.firstCharDelay == true)
                    {
                        try
                        {
                            Thread.sleep(500);
                        } catch (InterruptedException ex)
                        {
                            Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        KeyBoard.firstCharDelay = false;
                    } else
                    {
                        try
                        {
                            Robot robot = new Robot();

                            KeyStroke awtKS = KeyStroke.getKeyStroke(8, 0);
                            robot.keyPress(awtKS.getKeyCode());
                            try
                            {
                                Thread.sleep(50);
                            } catch (InterruptedException ex)
                            {
                                Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            robot.keyRelease(awtKS.getKeyCode());


                        } catch (AWTException ex)
                        {
                            Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                KeyBoard.firstCharDelay = true;
            }
        };

        parent = javaApplication4;
        JPanel frm = this;
        setLayout(null);
        frm.setSize(size);
        final int keysHeight = (int) size.getHeight() / 4;
        final int keysWidth = (int) size.getWidth() / 14;


        Point currentLocation = new Point(0, frm.getHeight() - keysHeight * 5);
        final char[][] upperKeys = {
                {'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '|'},
                {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '{', '}'},
                {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ':', '"'},
                {'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>', '?'},
        };
        final char[][] lowerKeys = {
                {'`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', '\\'},
                {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '[', ']'},
                {'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ';', '\''},
                {'Z', 'X', 'C', 'V', 'B', 'N', 'M', ',', '.', '/'},
        };
        MButton[][] btns = new MButton[4][14];
        JButton shift = new JButton("Shift");
        JButton backSpace = new JButton("Delete");
        JButton enter = new JButton("Enter");
        JButton dotcom = new JButton(".com");
        JButton atsign = new JButton("@");
        enter.setBackground(Color.yellow);
        shift.setBackground(Color.yellow);
        backSpace.setBackground(Color.yellow);
        dotcom.setBackground(Color.green);
        atsign.setBackground(Color.green);
        
        
        shift.setFocusable(false);
        backSpace.setFocusable(false);
        enter.setFocusable(false);

        for (int i = 0; i <= lowerKeys.length - 1; i++)
        {
            currentLocation.setLocation(i * keysWidth / 3, currentLocation.getY() + keysHeight);
            for (int j = 0; j <= lowerKeys[i].length - 1; j++)
            {
                MButton btn = btns[i][j] = new MButton(i, j);
                btn.setFocusable(false);
                String s = lowerKeys[i][j] + "";
                btn.setText(s);
                btn.setSize(keysWidth, keysHeight);
                btn.setLocation(currentLocation);
                currentLocation.setLocation(keysWidth + currentLocation.getX(), currentLocation.getY());
                frm.add(btn);
                btn.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(Color.black);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(null);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        
                    }
                });
                btn.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        parent.setListenToKeyboardShow(false);
                        txt.requestFocusInWindow();
                        parent.setListenToKeyboardShow(true);

                        MButton b = ((MButton) e.getSource());
//                    char a;
//                    char[] b=new char[1];
//                    ((JButton)e.getSource()).getText().getChars(0, 1, b, 0);
//                    a=b[0];

                        try
                        {
                            Robot robot = new Robot();

                            KeyStroke awtKS = KeyStroke.getKeyStroke(lowerKeys[b.i][b.j], 0);
                            int key_code = awtKS.getKeyCode();
                            if (b.i == 2 && b.j == 10)
                            {
                                robot.keyPress(KeyEvent.VK_QUOTE);
                                robot.keyRelease(KeyEvent.VK_QUOTE);
                            } else if (b.i == 0 && b.j == 0)
                            {
                                robot.keyPress(KeyEvent.VK_BACK_QUOTE);
                                robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
                            } else
                            {
                                robot.keyPress(key_code);
                                robot.keyRelease(key_code);
                            }
                            System.out.println(key_code);

                        } catch (AWTException ex)
                        {
                            Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                });

            }


        }
        
        frm.add(dotcom);
        currentLocation.setLocation(currentLocation.getX() , currentLocation.getY());
        dotcom.setLocation(currentLocation);
        dotcom.setSize(keysWidth*2/3 , keysHeight);
        frm.add(shift);
        currentLocation.setLocation(currentLocation.getX() + keysWidth * 2 / 3, currentLocation.getY() );
        shift.setLocation(currentLocation);
        shift.setSize(keysWidth *7/3, keysHeight);
        frm.add(atsign);
        currentLocation.setLocation(currentLocation.getX(), currentLocation.getY()-keysHeight );
        atsign.setLocation(currentLocation);
        atsign.setSize(keysWidth *2/3, keysHeight);
        frm.add(enter);
        currentLocation.setLocation(currentLocation.getX() + keysWidth * 2 / 3, currentLocation.getY());
        enter.setLocation(currentLocation);
        enter.setSize(keysWidth * 5 / 3, keysHeight );
        frm.add(backSpace);
        currentLocation.setLocation(currentLocation.getX() , currentLocation.getY() - keysHeight);
        backSpace.setLocation(currentLocation);
        backSpace.setSize(keysWidth * 5 / 3, keysHeight );
        atsign.setFocusable(false);
        dotcom.setFocusable(false);


        frm.repaint();

        shift.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                if (((JButton) e.getSource()).getBackground() == Color.red)
                {
                    ((JButton) e.getSource()).setBackground(Color.yellow);
                    for (int i = 0; i <= 3; i++)
                        for (int j = 0; j <= 13; j++)
                            if (btns[i][j] != null) btns[i][j].setText(lowerKeys[i][j] + "");
                } else
                {
                    ((JButton) e.getSource()).setBackground(Color.red);
                    for (int i = 0; i <= 3; i++)
                        for (int j = 0; j <= 13; j++)
                            if (btns[i][j] != null) btns[i][j].setText(upperKeys[i][j] + "");
                }
                //((JButton)e.getSource()).setBackground(Color.red);
                ((JButton) e.getSource()).repaint();

                try
                {
                    Robot robot = new Robot();

                    if (((JButton) e.getSource()).getBackground() == Color.red)
                        robot.keyPress(KeyEvent.VK_SHIFT);
                    else
                        robot.keyRelease(KeyEvent.VK_SHIFT);


                } catch (AWTException ex)
                {
                    Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        backSpace.addMouseListener(new deletorListener());
        backSpace.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.setListenToKeyboardShow(false);
                txt.requestFocusInWindow();
                parent.setListenToKeyboardShow(true);

                try
                {
                    Robot robot = new Robot();

                    KeyStroke awtKS = KeyStroke.getKeyStroke(8, 0);
                    robot.keyPress(awtKS.getKeyCode());
                    robot.keyRelease(awtKS.getKeyCode());


                } catch (AWTException ex)
                {
                    Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        enter.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(Color.black);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(Color.yellow);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        
                    }
                });
        enter.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                enterActionPerformListener.EnterActionPerform();
            }
        });
        
        atsign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setListenToKeyboardShow(false);
                txt.requestFocusInWindow();
                parent.setListenToKeyboardShow(true);

                try
                {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(50);
                    robot.keyRelease(50);
                    robot.keyRelease(KeyEvent.VK_SHIFT);

                } catch (AWTException ex)
                {
                    Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        atsign.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ((JButton)e.getSource()).setBackground(Color.black);
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((JButton)e.getSource()).setBackground(Color.green);
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        
        
        
        
        
        dotcom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setListenToKeyboardShow(false);
                txt.requestFocusInWindow();
                parent.setListenToKeyboardShow(true);

                try
                {
                    Robot robot = new Robot();

                    robot.keyPress(46);
                    robot.keyRelease(46);
                    robot.keyPress(67);
                    robot.keyRelease(67);
                    robot.keyPress(79);
                    robot.keyRelease(79);
                    robot.keyPress(77);
                    robot.keyRelease(77);


                } catch (AWTException ex)
                {
                    Logger.getLogger(KeyBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        dotcom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ((JButton)e.getSource()).setBackground(Color.black);
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((JButton)e.getSource()).setBackground(Color.green);
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        
        
        
        
        
        

    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.

    }
    


};

class MButton extends JButton
{
    int i;
    int j;

    MButton(int i, int j)
    {
        this.i = i;
        this.j = j;
    }


}


class deletorListener implements MouseListener
{
    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        ((JButton)e.getSource()).setBackground(Color.black);
        KeyBoard.deleterActive = true;
        new Thread(KeyBoard.deleter).start();

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        KeyBoard.deleterActive = false;
        ((JButton)e.getSource()).setBackground(Color.yellow);
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}

