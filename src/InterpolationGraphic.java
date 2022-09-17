import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterpolationGraphic {
    private JFrame frame;
    private JLabel statusLabel;
    private JTextField minXTextField;
    private JTextField maxXTextField;
    private JTextField nodeNumberTextField;
    private JTextField functionColorTextField;
    private JTextField polyColorTextField;
    private JTextField lineColorTextField;
    private JTextField nodeColorTextField;
    private GraphicPanel graphicPanel;

    public InterpolationGraphic() {
        createWindow();
        initContent();
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    private void createWindow() {
        frame = new JFrame("Interpolation");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initContent() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        JPanel bottomPanel = createBottomPanel();
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        Box rightPanel = createRightPanel();
        mainContainer.add(rightPanel, BorderLayout.EAST);

	    graphicPanel = new GraphicPanel((x) -> {
	        double lx = x;
            return Math.sin(lx);
        });
	    graphicPanel.setBackground(Color.WHITE);
	    mainContainer.add(graphicPanel);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        statusLabel = new JLabel("Content loading...");
        panel.add(statusLabel);
        return panel;
    }

    private Box createRightPanel() {
        Box panel = Box.createVerticalBox();

        JLabel title = new JLabel("Plotting a function");
        title.setFont(new Font(null, Font.BOLD, 12));
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        panel.add(new JLabel("Minimum X value:"));

        minXTextField = new JTextField("-1.0");
        minXTextField.setMaximumSize(new Dimension(300, 30));
        minXTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                minXTextField.setText(graphicPanel.setMinX(minXTextField.getText()).toString());
            }
        });
        minXTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    minXTextField.setText(graphicPanel.setMinX(minXTextField.getText()).toString());
                }
            }
        });
        panel.add(minXTextField);

        panel.add(new JLabel("Maximum X value:"));

        maxXTextField = new JTextField("1.0");
        maxXTextField.setMaximumSize(new Dimension(300, 30));
        maxXTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                maxXTextField.setText(graphicPanel.setMaxX(maxXTextField.getText()).toString());
            }
        });
        maxXTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    maxXTextField.setText(graphicPanel.setMaxX(maxXTextField.getText()).toString());
                }
            }
        });
        panel.add(maxXTextField);

        panel.add(new JLabel("Nodes number:"));

        nodeNumberTextField = new JTextField("2");
        nodeNumberTextField.setMaximumSize(new Dimension(300, 30));
        nodeNumberTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                nodeNumberTextField.setText(
                        graphicPanel.setArgumentsNumber(nodeNumberTextField.getText()).toString()
                );
            }
        });
        nodeNumberTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nodeNumberTextField.setText(
                            graphicPanel.setArgumentsNumber(nodeNumberTextField.getText()).toString()
                    );
                }
            }
        });
        panel.add(nodeNumberTextField);

        panel.add(new JLabel("Function color:"));

        functionColorTextField = new JTextField("#FF0000");
        functionColorTextField.setMaximumSize(new Dimension(300, 30));
        functionColorTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                functionColorTextField.setText(
                        graphicPanel.setFunctionColor(functionColorTextField.getText()).toString()
                );
            }
        });
        functionColorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    functionColorTextField.setText(
                            graphicPanel.setFunctionColor(functionColorTextField.getText()).toString()
                    );
                }
            }
        });
        panel.add(functionColorTextField);

        panel.add(new JLabel("Polynomial color:"));

        polyColorTextField = new JTextField("#00FF00");
        polyColorTextField.setMaximumSize(new Dimension(300, 30));
        polyColorTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                polyColorTextField.setText(
                        graphicPanel.setPolynomialColor(polyColorTextField.getText()).toString()
                );
            }
        });
        polyColorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    polyColorTextField.setText(
                            graphicPanel.setPolynomialColor(polyColorTextField.getText()).toString()
                    );
                }
            }
        });
        panel.add(polyColorTextField);

        panel.add(new JLabel("Line function color:"));

        lineColorTextField = new JTextField("#0000FF");
        lineColorTextField.setMaximumSize(new Dimension(300, 30));
        lineColorTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                lineColorTextField.setText(
                        graphicPanel.setLineFuncColor(lineColorTextField.getText()).toString()
                );
            }
        });
        lineColorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    lineColorTextField.setText(
                            graphicPanel.setLineFuncColor(lineColorTextField.getText()).toString()
                    );
                }
            }
        });
        panel.add(lineColorTextField);

        panel.add(new JLabel("Nodes color:"));

        nodeColorTextField = new JTextField("#FF00FF");
        nodeColorTextField.setMaximumSize(new Dimension(300, 30));
        nodeColorTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                nodeColorTextField.setText(
                        graphicPanel.setNodeColor(nodeColorTextField.getText()).toString()
                );
            }
        });
        nodeColorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nodeColorTextField.setText(
                            graphicPanel.setNodeColor(nodeColorTextField.getText()).toString()
                    );
                }
            }
        });
        panel.add(nodeColorTextField);

        return panel;
    }
}
