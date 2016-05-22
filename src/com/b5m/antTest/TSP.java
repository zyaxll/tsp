/*----------------------------------------------------------------------
  File    : TSP.java
  Contents: a traveling salesman problem
  Author  : Christian Borgelt
  History : 19.11.2005 file created
----------------------------------------------------------------------*/
package com.b5m.antTest;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Random;
import java.util.Scanner;

public class TSP {

    private static final int BLKSIZE = 16;

    protected int size;
    protected double[] xs, ys;
    protected double[][] dists;
    protected boolean sym;
    protected boolean euclid;
    protected int[] tour;
    private double bbx, bby;
    private double bbw, bbh;
    private boolean valid;


    public TSP() {
        this(BLKSIZE);
    }

    public TSP(int size) {
        this.size = 0;
        this.xs = new double[size];
        this.ys = new double[size];
        this.dists = null;
        this.euclid = true;
        this.sym = true;
        this.tour = null;
        this.valid = false;
    }



    public TSP(int size, Random rand) {
        this(size);
        this.randomize(rand);
        this.makeDists(true);
    }



    private void resize(int size) {
        int k;
        double v[];

        k = this.size;
        if (size < 0) size = k + ((k < BLKSIZE) ? BLKSIZE : (k >> 1));
        if (size < k) k = this.size = size;
        System.arraycopy(this.xs, 0, v = new double[size], 0, k);
        this.xs = v;
        System.arraycopy(this.ys, 0, v = new double[size], 0, k);
        this.ys = v;
    }



    public int add(double x, double y) {
        if (this.size >= this.xs.length)
            this.resize(-1);
        this.valid = false;
        this.xs[this.size] = x;
        this.ys[this.size] = y;
        return this.size++;
    }



    public void randomize(Random rand) {
        for (int i = this.size = this.xs.length; --i >= 0; ) {
            this.xs[i] = rand.nextDouble();
            this.ys[i] = rand.nextDouble();
        }
        this.valid = false;
    }



    public int size() {
        return this.size;
    }

    public double getX(int i) {
        return this.xs[i];
    }

    public double getY(int i) {
        return this.ys[i];
    }

    public void setPos(int i, double x, double y) {
        this.xs[i] = x;
        this.ys[i] = y;
    }



    public void transform(double scale, double xoff, double yoff) {
        for (int i = this.size; --i >= 0; ) {
            this.xs[i] = this.xs[i] * scale + xoff;
            this.ys[i] = this.ys[i] * scale + yoff;
        }
        this.valid = false;
    }


    private void bbox() {
        int i;
        double x, y;
        double xmax, ymax;

        this.bbx = Double.MAX_VALUE;
        xmax = -Double.MAX_VALUE;
        this.bby = Double.MAX_VALUE;
        ymax = -Double.MAX_VALUE;
        for (i = this.xs.length; --i >= 0; ) {
            x = this.xs[i];
            y = this.ys[i];
            if (x < this.bbx) this.bbx = x;
            if (x > xmax) xmax = x;
            if (y < this.bby) this.bby = y;
            if (y > ymax) ymax = y;
        }
        this.bbw = xmax - this.bbx;
        this.bbh = ymax - this.bby;
        this.valid = true;
    }



    public double getX() {
        if (!this.valid) this.bbox();
        return this.bbx;
    }

    public double getY() {
        if (!this.valid) this.bbox();
        return this.bby;
    }

    public double getWidth() {
        if (!this.valid) this.bbox();
        return this.bbw;
    }

    public double getHeight() {
        if (!this.valid) this.bbox();
        return this.bbh;
    }



    public void makeDists(boolean calc) {
        int i, k;
        double dx, dy;
        double v[];

        if (this.size < this.xs.length)
            this.resize(this.size);
        this.dists = new double[this.size][this.size];
        if (!calc) return;
        for (i = this.size; --i >= 0; ) {
            this.dists[i][i] = 0;
            for (k = i; --k >= 0; ) {
                dx = this.xs[i] - this.xs[k];
                dy = this.ys[i] - this.ys[k];
                this.dists[i][k] = this.dists[k][i] = Math.sqrt(dx * dx + dy * dy);
            }
        }
        this.euclid = this.sym = true;
    }


    public boolean isSymmetric() {
        return this.sym;
    }

    public double getDist(int i, int j) {
        return this.dists[i][j];
    }

    public void setDist(int i, int j, double dist) {
        this.dists[i][j] = this.dists[j][i] = dist;
        this.euclid = false;
    }

    public void setDistAsym(int i, int j, double dist) {
        this.dists[i][j] = dist;
        this.sym = false;
        this.euclid = false;
    }


    public int[] getTour() {
        return this.tour;
    }

    public void setTour(int[] tour) {
        if (this.tour == null)
            this.tour = new int[this.size];
        System.arraycopy(tour, 0, this.tour, 0, this.size);
    }


    public String toString() {
        int i, k;
        StringBuffer s;

        s = new StringBuffer("TSP = {\n");
        s.append("  vertices = {");
        s.append("\n    (" + this.xs[0] + ", " + this.ys[0] + ")");
        for (i = 1; i < this.size; i++)
            s.append(",\n    (" + this.xs[i] + ", " + this.ys[i] + ")");
        s.append("\n  };\n");
        if (!this.euclid) {
            s.append("  distances = {");
            for (i = 0; i < this.size; i++) {
                if (i > 0) s.append(",");
                s.append("\n    { " + this.dists[i][0]);
                for (k = 1; k < this.size; k++)
                    s.append(", " + this.dists[i][k]);
                s.append(" }");
            }
            s.append("\n  };\n");
        }
        if (this.tour != null) {
            s.append("  tour = {\n    " + this.tour[0]);
            for (i = 1; i < this.size; i++)
                s.append(", " + this.tour[i]);
            s.append("\n  };\n");
        }
        s.append("};\n");
        return s.toString();
    }



    public TSP(Scanner s) throws IOException {
        this();
        int i, k;
        double x, y;
    }

    public TSP(String desc) throws IOException {
        this(new Scanner(desc));
    }

    public TSP(InputStream in) throws IOException {
        this(new Scanner(in));
    }

    public TSP(Reader reader) throws IOException {
        this(new Scanner(reader));
    }


    public static void main(String args[]) {
        int size;
        double scale = 1.0;
        long seed;
        TSP tsp;

        if (args.length <= 0) {
            System.err.println("usage: TSP <vertcnt> [<scale>] [<seed>]");
            return;
        }
        seed = System.currentTimeMillis();
        size = Integer.parseInt(args[0]);
        if (args.length > 1) {
            scale = Double.parseDouble(args[1]);
        }
        if (args.length > 2) {
            seed = Integer.parseInt(args[2]);
        }
        tsp = new TSP(size, new Random(seed));
        tsp.transform(scale, 0, 0);
        System.out.print(tsp);
    }

}
