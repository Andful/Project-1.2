package org.lwjglb.engine;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created by Lucas on 20/04/2017.
 */
public class MainMenu extends JFrame {
    private JPanel rootpanel;
    private JButton backwardsButton;
    private JButton upButton;
    private JButton forwardsButton1;
    private JButton downButton1;
    private JButton rightButton;
    private JButton leftButton;
    private JButton focusButton;
    private JButton prevRobotButton;
    private JButton nextRobotButton;
    private JButton playButton;
    private JButton nextTimeStepButton;
    private JButton previousTimeStepButton;
    private JButton stopButton;
    private JButton loadFileButton;
    private JButton loadStartEndConfigButton;
    private JButton loadEnvirmentSizeButton;
    private JButton loadObstaclesButton;
    private JFileChooser fileChooser=new JFileChooser();

    private volatile File startEndConfiguration=null;
    private volatile File envirmentSize=null;
    private volatile File obstacles=null;

    private volatile boolean bbackwardsButton;
    private volatile boolean bupButton;
    private volatile boolean bforwardsButton1;
    private volatile boolean bdownButton1;
    private volatile boolean brightButton;
    private volatile boolean bleftButton;
    private volatile boolean bfocusButton;
    private volatile boolean bprevRobotButton;
    private volatile boolean bnextRobotButton;
    private volatile boolean bplayButton;
    private volatile boolean bbpreviousTimeStepButton;
    private volatile boolean bstopButton;
    private volatile boolean bloadFileButton;
    private volatile boolean bnextTimeStepButton;
    private volatile boolean bpreviousTimeStepButton;

    public MainMenu() {

        super("Menu");
        setSize(650, 380);
        setContentPane(rootpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        upButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bupButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bupButton = false;
            }
        });
        downButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bdownButton1 = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bdownButton1 = false;
            }
        });
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bleftButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bleftButton = false;
            }
        });
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                brightButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                brightButton = false;
            }
        });
        backwardsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bbackwardsButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bbackwardsButton = false;
            }
        });
        forwardsButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bforwardsButton1 = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bforwardsButton1 = false;
            }
        });

        focusButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bfocusButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                    bfocusButton = false;
            }
        });
        prevRobotButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bprevRobotButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bprevRobotButton = false;
            }
        });
        nextRobotButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bnextRobotButton = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                bnextRobotButton = false;
            }
        });

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bplayButton = true;
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bplayButton = false;
            }
        });


        JFrame frame=this;
        loadStartEndConfigButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int ret=fileChooser.showOpenDialog(frame);
                if(ret==JFileChooser.APPROVE_OPTION)
                {
                    startEndConfiguration=fileChooser.getSelectedFile();
                }
            }
        });
        loadEnvirmentSizeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int ret=fileChooser.showOpenDialog(frame);
                if(ret==JFileChooser.APPROVE_OPTION)
                {
                    envirmentSize=fileChooser.getSelectedFile();
                }
            }
        });
        loadObstaclesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int ret=fileChooser.showOpenDialog(frame);
                if(ret==JFileChooser.APPROVE_OPTION)
                {
                    obstacles=fileChooser.getSelectedFile();
                }
            }
        });
        nextTimeStepButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                System.out.println(e.getButton());
                if(e.getButton()==MouseEvent.BUTTON1)
                {
                    bnextTimeStepButton=true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(e.getButton()==MouseEvent.BUTTON1)
                {
                    bnextTimeStepButton=false;
                }
            }
        });
        previousTimeStepButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if(e.getButton()==MouseEvent.BUTTON1)
                {
                    bpreviousTimeStepButton=true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(e.getButton()==MouseEvent.BUTTON1)
                {
                    bpreviousTimeStepButton=false;
                }
            }
        });
    }

    public static void main(String[] args) {

        MainMenu test = new MainMenu();

//        System.out.println("sup");
        while (true) {
            //System.out.println(test.cameraUp());
//            System.out.println(test.getSize());


        }
    }

    public boolean cameraUp() {
        return bupButton;
    }

    public boolean cameraDown() {
        return bdownButton1;
    }

    public boolean cameraLeft() {
        return bleftButton;
    }

    public boolean cameraRight() {
        return brightButton;
    }

    public boolean cameraForwards() {
        return bforwardsButton1;
    }

    public boolean cameraBackwards() {
        return bbackwardsButton;
    }

    public boolean nextTimeStep()
    {
        return bnextTimeStepButton;
    }
    public boolean previouseTimeStep()
    {
        return bpreviousTimeStepButton;
    }

    public boolean isBprevRobotButton() {return bprevRobotButton;}
    public boolean isBnextRobotButton() {return bnextRobotButton;}
    public boolean isBplayButton() {return bplayButton;}
    public boolean isBstopButton() {return bstopButton;}


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootpanel = new JPanel();
        rootpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(""));
        playButton = new JButton();
        playButton.setText("play");
        panel2.add(playButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        previousTimeStepButton = new JButton();
        previousTimeStepButton.setText("previous time step");
        panel2.add(previousTimeStepButton, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextRobotButton = new JButton();
        nextRobotButton.setText("next robot");
        panel2.add(nextRobotButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        focusButton = new JButton();
        focusButton.setText("focus");
        panel2.add(focusButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        prevRobotButton = new JButton();
        prevRobotButton.setText("prev robot");
        panel2.add(prevRobotButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextTimeStepButton = new JButton();
        nextTimeStepButton.setText("next time step");
        panel2.add(nextTimeStepButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel2.add(stopButton, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadEnvirmentSizeButton = new JButton();
        loadEnvirmentSizeButton.setText("load envirment size");
        panel1.add(loadEnvirmentSizeButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadObstaclesButton = new JButton();
        loadObstaclesButton.setText("load obstacles ");
        panel1.add(loadObstaclesButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadStartEndConfigButton = new JButton();
        loadStartEndConfigButton.setText("load start/end config");
        panel1.add(loadStartEndConfigButton, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadFileButton = new JButton();
        loadFileButton.setText("Load file");
        panel1.add(loadFileButton, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        rootpanel.add(panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(2, 2), new Dimension(100, 100), new Dimension(100, 100), 0, false));
        upButton = new JButton();
        upButton.setText("up");
        panel4.add(upButton, BorderLayout.CENTER);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel3.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(100, 100), new Dimension(100, 100), 0, false));
        downButton1 = new JButton();
        downButton1.setText("down");
        panel5.add(downButton1, BorderLayout.CENTER);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel3.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rightButton = new JButton();
        rightButton.setText("right");
        panel6.add(rightButton, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel3.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        leftButton = new JButton();
        leftButton.setText("left");
        panel7.add(leftButton, BorderLayout.CENTER);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel3.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        forwardsButton1 = new JButton();
        forwardsButton1.setText("forwards");
        panel8.add(forwardsButton1, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel3.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backwardsButton = new JButton();
        backwardsButton.setText("backwards");
        panel9.add(backwardsButton, BorderLayout.CENTER);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        rootpanel.add(panel10);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootpanel;
    }
}
