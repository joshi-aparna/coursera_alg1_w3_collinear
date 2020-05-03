import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] inputPoints)     // finds all line segments containing 4 or more points
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
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());
            int start = -1;
            int end = -1;
            double previousSlope = 0.0;
            for (int j = 0; j < points.length; j++) {
                double currentSlope = p.slopeTo(points[j]);
                if (previousSlope == currentSlope) {
                    start = (start < j - 1 && start >= 0) ? start : j - 1;
                    end = (end < j) ? j : end;
                } else {
                    if (end - start >= 2) {
                        this.segments.add(new LineSegment(points[start], points[end]));
                    }
                }
                previousSlope = currentSlope;

            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return this.segments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
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
        StdDraw.setPenRadius(0.02);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}