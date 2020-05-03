import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final List<LineSegment> segments;

    public BruteCollinearPoints(Point[] inputPoints)    // finds all line segments containing 4 points
    {
        this.segments = new ArrayList<>();
        if (inputPoints == null)
            throw new IllegalArgumentException();
        List<Point> pointList = new ArrayList<>();
        for (Point p : inputPoints) {
            if (p == null)
                throw new IllegalArgumentException();
            if (pointList.stream().anyMatch(q -> p.compareTo(q) == 0))
                throw new IllegalArgumentException();
            pointList.add(p);
        }
        Point[] points = pointList.toArray(new Point[0]);
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) &&
                                points[j].slopeTo(points[k]) == points[k].slopeTo(points[m])) {
                            this.segments.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return this.segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
