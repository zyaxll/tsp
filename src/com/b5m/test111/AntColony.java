/*----------------------------------------------------------------------
  File    : AntColony.java
  Contents: an ant colony of an ant colony optimization algorithm
            for the traveling salesman problem
  Author  : Christian Borgelt
  History : 19.11.2005 file created
            03.12.2005 trail and inverse distance exponents exchanged
----------------------------------------------------------------------*/
package com.b5m.test111;

import java.util.Random;

public class AntColony {

    protected TSP tsp;     
    protected double[][] dists;   
    protected double[][] nears;   
    protected double[][] trail;   
    protected double[][] delta;   
    protected double[][] quals;   
    protected boolean[] visited; 
    protected int[] tour;    
    protected double len;     
    protected int[] brun;    
    protected double brlen;   
    protected int[] best;    
    protected double bestlen; 
    protected double avglen;  
    private double max;     
    private double avg;     

    protected int antcnt;  
    protected int epoch;   
    protected double exploit; 
    protected double alpha;   
    protected double beta;    
    protected double evap;    
    protected double layexp;  
    protected double elite;   

    private Random rand;    
    private int[] dsts;    
    private double[] sums;    


    public AntColony(TSP tsp, int antcnt, Random rand) {                             
        this.tsp = tsp;         
        this.dists = tsp.dists;   
        int size = tsp.size();  
        this.nears = new double[size][size];
        this.trail = new double[size][size];
        this.delta = new double[size][size];
        this.quals = new double[size][size];
        this.visited = new boolean[size];
        this.tour = new int[size];
        this.len = Double.MAX_VALUE;
        this.brun = new int[size];
        this.brlen = Double.MAX_VALUE;
        this.best = new int[size];
        this.bestlen = Double.MAX_VALUE;
        this.dsts = new int[size];
        this.sums = new double[size];
        this.rand = rand;        
        this.antcnt = antcnt;      
        this.exploit = 0.0;         
        this.alpha = 1.0;         
        this.beta = 1.0;         
        this.evap = 0.1;         
        this.epoch = 0;           
    }  


    public AntColony(TSP tsp, int antcnt) {
        this(tsp, antcnt, new Random());
    }


    public TSP getTSP() {
        return this.tsp;
    }


    public void setExploit(double exploit) {
        this.exploit = exploit;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setEvap(double evap) {
        this.evap = evap;
    }

    public void setTrail(double trail) {
        this.elite = elite;
    }

    public void setElite(double elite) {
        this.elite = elite;
    }


    public double getDist(int i, int j) {
        return this.dists[i][j];
    }

    public double getTrail(int i, int j) {
        return this.trail[i][j];
    }

    public double getTrailAvg() {
        return this.avg;
    }

    public double getTrailMax() {
        return this.max;
    }

    public int[] getBestTour() {
        return this.best;
    }

    public double getBestLen() {
        return this.bestlen;
    }

    public int getEpoch() {
        return this.epoch;
    }


    public void init() {
        this.init(-1);
    }

    public void init(double val) {                             
        int i, j;                
        double sum = 0;             

        for (i = this.tour.length; --i >= 0; ){
            for (j = this.tour.length; --j >= 0; ){
                sum += this.dists[i][j];
            }
        }
        this.avglen = sum / this.tour.length;
        if (val <= 0){
            val = 1;      
        }
        for (i = this.tour.length; --i >= 0; ) {
            for (j = this.tour.length; --j >= 0; ) {
                this.nears[i][j] = Math.pow(this.dists[i][j], -this.beta);
                this.trail[i][j] = val; 
            }                         
        }                           
        this.max = this.avg = val;  
        this.bestlen = Double.MAX_VALUE;
        this.epoch = 0;            
    }               


    private static int find(double vec[], int n, double val) {                 
        int i, k;                   

        for (--n, i = 0; i < n; ) { 
            k = (i + n) >> 1;          
            if (vec[k] < val) {
                i = k + 1;
            }else {
                n = k;  
            }
        }                           
        return i;                   
    } 


    private void placePhero(int[] tour, double amount) {                             
        int i;                  
        int src, dst;           
        boolean sym;                

        src = this.tour[0];         
        for (i = tour.length; --i >= 0; ) {
            dst = src;
            src = tour[i]; 
            this.delta[src][dst] += amount;
        }                           
    }  


    public double runAnt() {                            
        int i, j, n;            
        int src, dst = 0;        
        double emax;               
        double sum;                
        double chg;               

        for (i = this.visited.length; --i >= 0; ){
            this.visited[i] = false;  
        }
        this.len = 0;               

        src = this.rand.nextInt(this.tour.length);
        this.tour[0] = src;    
        this.visited[src] = true;   
        for (i = 0; ++i < this.tour.length; ) {
            if ((this.exploit > 0)    
                    && (rand.nextDouble() < this.exploit)) {
                emax = -1.0;            
                for (j = this.tour.length; --j >= 0; ) {
                    if (!this.visited[j]  
                            && (this.quals[src][j] > emax)) {
                        emax = this.quals[src][j];
                        dst = j;
                    }
                }
            }                     
            else {                    
                sum = 0;                
                for (j = n = 0; j < this.tour.length; j++) {
                    if (this.visited[j]) continue;
                    sum += this.quals[src][j];
                    this.sums[n] = sum; 
                    this.dsts[n++] = j;   
                }                       
                j = find(this.sums, n, sum * rand.nextDouble());
                dst = this.dsts[j];     
            }                         
            this.visited[dst] = true; 
            this.len += this.dists[src][dst];
            this.tour[i] = src = dst; 
        }                           
        this.len += this.dists[src][this.tour[0]];

        chg = this.avglen / this.len; 
        if (this.layexp != 1) chg = Math.pow(chg, this.layexp);
        this.placePhero(this.tour, chg);

        return this.len;            
    } 


    public double runAllAnts() {                             
        int i, j;                
        double t, min;              
        double stick;               

        for (i = this.delta.length; --i >= 0; ) {
            for (j = this.delta.length; --j >= 0; ) {
                this.delta[i][j] = 0;   
                this.quals[i][j] = this.nears[i][j]
                        * ((this.alpha == 1.0) ? this.trail[i][j]
                        : Math.pow(this.trail[i][j], this.alpha));
            }                        
        }                         

        this.brlen = Double.MAX_VALUE;
        for (i = this.antcnt; --i >= 0; ) {
            this.runAnt();            
            if (this.len >= this.brlen){
                continue;
            }
            System.arraycopy(this.tour, 0, this.brun, 0, this.brun.length);
            this.brlen = this.len;    
        }                           
        if (this.brlen < this.bestlen) {
            System.arraycopy(this.brun, 0, this.best, 0, this.best.length);
            this.bestlen = this.brlen;
        }                           
        if (this.elite > 0) {       
            t = this.avglen / this.bestlen;
            if (this.layexp != 1){
                t = Math.pow(t, this.layexp);
            }
            this.placePhero(this.best, this.elite * this.antcnt * t);
        }                           

        min = this.avg / this.tour.length;
        this.max = this.avg = 0;    
        stick = 1 - this.evap;    
        if (this.tsp.isSymmetric()) {
            for (i = this.trail.length; --i >= 0; ) {
                for (j = i; --j >= 0; ) {
                    t = stick * this.trail[i][j]
                            + this.evap * (this.delta[i][j] + this.delta[j][i]);
                    if (t < min) t = min; 
                    this.trail[i][j] =    
                            this.trail[j][i] = t; 
                    if (t > this.max) this.max = t;
                    this.avg += t;        
                }                       
            }                         
            this.avg /= 0.5 * this.tour.length * this.tour.length;
        } else {                      
            for (i = this.trail.length; --i >= 0; ) {
                for (j = this.trail.length; --j >= 0; ) {
                    t = stick * this.trail[i][j]
                            + this.evap * this.delta[i][j];
                    if (t < min){
                        t = min; 
                    }
                    this.trail[i][j] = t; 
                    if (t > this.max){
                        this.max = t;
                    }
                    this.avg += t;        
                }                       
            }                         
            if (this.tour.length > 1) 
                this.avg /= this.tour.length * (this.tour.length - 1);
        }

        this.epoch++;               
        return this.bestlen;        
    }  

}  
