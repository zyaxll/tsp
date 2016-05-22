package com.b5m.antTest;

/**
 * @description: //TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: zya
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        zya          1.0
 */
public class Edge implements Comparable<Edge> {

        protected int i, j;
        protected int c;

        public Edge () {

        }

        public Edge (int i, int j, int c) {
            this.i = i;
            this.j = j;
            this.c = c;
        }

        public int compareTo (Edge obj) {
            if (this.c < obj.c) {
                return -1;
            }
            if (this.c > obj.c){
                return +1;
            }
            return 0;
        }

}

