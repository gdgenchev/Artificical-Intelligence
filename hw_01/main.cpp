#include "Point.h"
#include "Maze.h"

const Grid GRID = {
        {1, 1, 0, 1, 1, 1},
        {1, 2, 0, 0, 1, 1},
        {1, 1, 1, 1, 2, 1},
        {1, 1, 1, 1, 1, 1},
        {1, 0, 0, 1, 1, 1},
        {1, 1, 1, 1, 1, 1}
};

const Point START = Point(0, 0);
const Point END = Point(4, 4);

const int MAX_DEPTH = 4;

int main() {
    Maze maze(GRID);
    maze.setStartPoint(START);
    maze.setEndPoint(END);
    maze.setMaxDepth(MAX_DEPTH);
    maze.solve();

    return 0;
}