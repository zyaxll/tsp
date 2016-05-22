package com.b5m.test111;

/**
 * @description: //TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: feiliu
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        feiliu          1.0
 */
public class Edge implements Comparable<Edge> {
        /* --- instance variables --- */
        protected int i, j;           /* indices of connected vertices */
        protected int c;              /* color index */

        public Edge () {

        }

        public Edge (int i, int j, int c) {
            this.i = i;
            this.j = j;
            this.c = c;
        }

        public int compareTo (Edge obj) {                             /* --- compare two edges */
            if (this.c < obj.c) {
                return -1;
            }
            if (this.c > obj.c){
                return +1;
            }
            return 0;                   /* return sign of color difference */
        }

}

