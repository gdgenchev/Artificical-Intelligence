#include <queue>
#include "Maze.h"

Maze::Maze(const Grid& grid) : grid(grid) {
    fillTeleports();
}

void Maze::fillTeleports() {
    for (size_t i = 0; i < grid.size(); i++) {
        for (size_t j = 0; j < grid[i].size(); j++) {
            if (grid[i][j] == TELEPORT) {
                teleports.emplace_back(Point(i, j));
            }
        }
    }
}

void Maze::setStartPoint(const Point& start) {
    this->start = start;
}

void Maze::setEndPoint(const Point& end) {
    this->end = end;
}

void Maze::solve() {
    int distance = fillParentsWithBfs();
    if (distance != -1) {
        std::cout << "Distance: " << distance << std::endl;
        printRecursive(end);
    } else {
        std::cout << "Could not find a path.\n";
    }
}

int Maze::fillParentsWithBfs() {
    if (isTeleport(start) || isTeleport(end)
        || !isReachable(start) || !isReachable(end)) {
        return false;
    }

    std::queue<Point> queue;
    std::unordered_set<Point, PointHash> visited;
    queue.push(start);

    int currentDistance = 0;
    while (!queue.empty()) {
        Point current = queue.front();
        queue.pop();
        if (current.distance > maxDistance) {
            return -1;
        }
        if (current == end && currentDistance < maxDistance) {
            return current.distance;
        }

        std::vector<Point> children;
        if (isTeleport(current)) {
            for (auto& currentTeleport : teleports) {
                if (currentTeleport != current) {
                    visited.insert(currentTeleport);
                    std::vector<Point> adjacentToCurrentTeleport = getAdjacentToPoint(currentTeleport);
                    children.reserve(adjacentToCurrentTeleport.size());
                    children.insert(children.end(), adjacentToCurrentTeleport.begin(), adjacentToCurrentTeleport.end());
                }
            }
        } else {
            children = getAdjacentToPoint(current);
        }

        for (auto& child : children) {
            if (visited.find(child) == visited.end()) {
                child.distance = current.distance + 1;
                parents[child] = current;
                queue.push(child);
                visited.insert(child);
            }
        }
        visited.insert(current);
    }

    return false;
}

bool Maze::isTeleport(const Point& point) const {
    return grid[point.x][point.y] == TELEPORT;
}

bool Maze::isValid(const Point& point) const {
    return point.x >= 0 && point.x < grid.size()
           && point.y >= 0 && point.y < grid[point.x].size();
}

bool Maze::isReachable(const Point& point) const {
    return grid[point.x][point.y] != WALL;
}

std::vector<Point> Maze::getAdjacentToPoint(const Point& point) const {
    std::vector<Point> adjacent;
    int adjByRow[] = {0, 1, 0, -1};
    int adjByCol[] = {1, 0, -1, 0};

    for (int i = 0; i < 4; i++) {
        Point adjacentPoint = Point(point.x + adjByRow[i], point.y + adjByCol[i]);
        if (isValid(adjacentPoint) && isReachable(adjacentPoint)) {
            adjacent.emplace_back(adjacentPoint);
        }
    }

    return adjacent;
}

void Maze::printRecursive(const Point& point) {
    if (point == start) {
        std::cout << start;
        return;
    }

    printRecursive(parents[point]);
    std::cout << "->" << point;
}

void Maze::setMaxDepth(const int maxDepth) {
    this->maxDistance = maxDepth;
}
