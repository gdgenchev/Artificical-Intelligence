#ifndef HW_01_MAZE_H
#define HW_01_MAZE_H

#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include "Point.h"

using Grid = std::vector<std::vector<int> >;

/**
 * Represents a single cell in the matrix.
 */
enum Cell {
    WALL = 0,
    EMPTY = 1,
    TELEPORT = 2
};

/**
 * This class represents a 2D maze, which supports multiple teleports.
 * Usage:
 * 1. Provide the grid which is represented as a 2D vector to the constructor;
 * 2. Set start and end points;
 * 3. Call method solve().
 */
class Maze {
private:
    const Grid& grid;
    Point start;
    Point end;
    int maxDistance;

    std::vector<Point> teleports;
    std::unordered_map<Point, Point, PointHash> parents;

    void fillTeleports();

    int fillParentsWithBfs();

    bool isTeleport(const Point& point) const;

    bool isValid(const Point& point) const;

    bool isReachable(const Point& point) const;

    std::vector<Point> getAdjacentToPoint(const Point& point) const;

    void printRecursive(const Point& point);

public:
    explicit Maze(const Grid& grid);

    void setStartPoint(const Point& start);

    void setEndPoint(const Point& end);

    void solve();

    void setMaxDepth(const int maxDepth);
};

#endif //HW_01_MAZE_H
