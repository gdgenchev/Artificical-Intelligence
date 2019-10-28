#ifndef HW_01_POINT_H
#define HW_01_POINT_H

#include <iostream>

/**
 * Represents a simple 2D point
 */
struct Point {
    int x;
    int y;
    int distance;

    Point(int x, int y) : Point(x, y, 0) {}

    Point(int x, int y, int distance) {
        this->x = x;
        this->y = y;
        this->distance = distance;
    }

    Point() {
        x = y = -1;
        distance = 0;
    }
    
    bool operator==(const Point& other) const {
        return this->x == other.x && this->y == other.y;
    }

    bool operator!=(const Point& other) const {
        return !(*this == other);
    }

    friend std::ostream& operator<<(std::ostream& out, const Point& point) {
        out << "(" << point.x << "," << point.y << ")";
        return out;
    }
};

struct PointHash {
    std::size_t operator()(const Point& point) const {
        return std::hash<int>()(point.x) ^ std::hash<int>()(point.y);
    }
};

#endif //HW_01_POINT_H
