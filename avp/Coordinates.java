/**********************************
 * Assignment 5: AVP
 * Coordinates.java
 * Date: 12/4/2011
 *
 * Jon Hamlin: jwh244, 2400666
 * Brooks Hoffecker: bjh83, 2015719
 * Evan Long: erl43, 2300076
 * Richard McCaffrey: rpm77, 2494167
 **********************************/
package avp;

public class Coordinates {
        // Position- the identifier
        public Integer row;
        public Integer col;

        /**
         * An instance stores a row and column coordinate
         * 
         * @param r
         * @param c
         */
        public Coordinates(Integer r, Integer c) {

                row = r;
                col = c;

        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Coordinates other = (Coordinates) obj;
                if (col == null) {
                        if (other.col != null)
                                return false;
                }
                else if (!col.equals(other.col))
                        return false;
                if (row == null) {
                        if (other.row != null)
                                return false;
                }
                else if (!row.equals(other.row))
                        return false;
                return true;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((col == null) ? 0 : col.hashCode());
                result = prime * result + ((row == null) ? 0 : row.hashCode());
                return result;
        }

        @Override
        public String toString() {
                return "(R" + row + ",C" + col + ")";
        }

}
