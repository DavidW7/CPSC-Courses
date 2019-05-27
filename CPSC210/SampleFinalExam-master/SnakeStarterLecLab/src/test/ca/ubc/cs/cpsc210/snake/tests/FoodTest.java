package ca.ubc.cs.cpsc210.snake.tests;

import ca.ubc.cs.cpsc210.snake.model.Cell;
import ca.ubc.cs.cpsc210.snake.model.Food;
import ca.ubc.cs.cpsc210.snake.model.SnakeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// jUnit tests for Food class
public class FoodTest {
    private Food testFood;

    @BeforeEach
    public void runBefore() {
        testFood = new Food(new Cell(SnakeGame.BOARD_ROWS / 2, SnakeGame.BOARD_COLS / 2));
    }

    @Test
    public void testTemplate() {
        // use this as a template for designing your tests
    }
}