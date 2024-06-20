import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JPanel implements ActionListener {

    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 300;
    private static final int CELL_SIZE = 10;
    private static final int DELAY = 100;

    private ArrayList<Point> snake;
    private Point apple;
    private boolean gameOver;
    private int direction; // 0: up, 1: right, 2: down, 3: left

    public SnakeGame() {
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
        initGame();
    }


    private void initGame() {
        snake = new ArrayList<>();
        snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
        spawnApple();
        direction = 1; // Start moving right
        gameOver = false;
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnApple() {
        Random rand = new Random();
        int x = rand.nextInt(BOARD_WIDTH / CELL_SIZE) * CELL_SIZE;
        int y = rand.nextInt(BOARD_HEIGHT / CELL_SIZE) * CELL_SIZE;
        apple = new Point(x, y);
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead;
        switch (direction) {
            case 0: // Up
                newHead = new Point(head.x, head.y - CELL_SIZE);
                break;
            case 1: // Right
                newHead = new Point(head.x + CELL_SIZE, head.y);
                break;
            case 2: // Down
                newHead = new Point(head.x, head.y + CELL_SIZE);
                break;
            case 3: // Left
                newHead = new Point(head.x - CELL_SIZE, head.y);
                break;
            default:
                return;
        }
        snake.add(0, newHead);
        if (!newHead.equals(apple)) {
            snake.remove(snake.size() - 1);
        } else {
            spawnApple();
        }
        checkCollision(newHead);
    }

    private void checkCollision(Point head) {
        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            gameOver = true; // Hit the wall
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true; // Collided with itself
                break;
            }
        }
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                direction = 0;
                break;
            case KeyEvent.VK_RIGHT:
                direction = 1;
                break;
            case KeyEvent.VK_DOWN:
                direction = 2;
                break;
            case KeyEvent.VK_LEFT:
                direction = 3;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) {
            g.setColor(Color.GREEN);
            for (Point segment : snake) {
                g.fillRect(segment.x, segment.y, CELL_SIZE, CELL_SIZE);
            }
            g.setColor(Color.RED);
            g.fillRect(apple.x, apple.y, CELL_SIZE, CELL_SIZE);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("Game Over! Press R to restart.", 100, 150);
        }
    }


    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}