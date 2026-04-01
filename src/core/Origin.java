package core;

public record Origin(int x, int y) {
// bro this works perfectly. my time wasted again!!!!
}
/*
public class Origin {

    final int x;
    final int y;

    public Origin(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int x() {return x;}
    public int y() {return y;}

    /**
     * hash by calculating the sum of x^2 + y^2, hopefully this wont create duplicates
     * @return a hash code based on the coordinates
     */
/*
@Override
public int hashCode() {
    return x * x + y * y;
}

@Override
public String toString() {
    return "(" + x + ", " + y + ")";
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o instanceof Origin origin) {
        return this.x == origin.x && this.y == origin.y;
    }
    return false;
}
}

 */