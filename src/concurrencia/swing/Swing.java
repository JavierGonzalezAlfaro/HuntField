package concurrencia.swing;

import concurrencia.HuntField;
import concurrencia.Position;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Swing {
    private JLabel label = new JLabel();
    private JFrame frame = new JFrame();
    private HuntField matrix;
    private JButton[][] bMatrix;

    public Swing(HuntField matrix) {
        this.matrix = matrix;
        bMatrix = new JButton[matrix.getXLength()][matrix.getYLength()];
        frame.setTitle("Hunt Field");
        frame.setSize(1350, 730);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        createButtonsMatrix();
        createComponents();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void show(HuntField matrix) {
        frame.setVisible(true);
        refresh(matrix);
    }

    private void createButtonsMatrix() {
        for (int i = 0; i < bMatrix.length; i++) {
            for (int j = 0; j < bMatrix[0].length; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                bMatrix[i][j] = button;
            }
        }
    }

    private void createComponents() {
        frame.add(createMatrixPanel());
        frame.add(createStats(), BorderLayout.SOUTH);
    }

    private JPanel createMatrixPanel() {
        JPanel panel = new JPanel();
        for (int i = 0; i < bMatrix.length; i++) {
            for (int j = 0; j < bMatrix[0].length; j++) {
                panel.add(bMatrix[i][j]);
            }
        }
        return panel;
    }

    private void refresh(HuntField matrix) {
        label.setText("Hunters "+matrix.getNumberOfItems('H')+ " Ducks "+matrix.getNumberOfItems('D'));
        for (int i = 0; i < bMatrix.length; i++) {
            for (int j = 0; j < bMatrix[0].length; j++) {
                if (matrix.getItemType(new Position(i, j)) == 'D') {
                    bMatrix[i][j].setIcon(new ImageIcon("Images//patoPequeño.png"));
                }
                if (matrix.getItemType(new Position(i, j)) == 'H') {
                    bMatrix[i][j].setIcon(new ImageIcon("Images//cazador.png"));
                }
                if (matrix.getItemType(new Position(i, j)) == 'T') {
                    bMatrix[i][j].setIcon(new ImageIcon("Images//arbol.png"));
                }
                if (matrix.getItemType(new Position(i, j)) == ' ') {
                    bMatrix[i][j].setIcon(new ImageIcon("Images//campo.png"));
                }
            }
        }
    }

    private JPanel createStats() {
        JPanel panel = new JPanel();
        
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
