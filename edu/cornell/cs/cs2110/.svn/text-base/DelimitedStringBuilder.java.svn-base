package edu.cornell.cs.cs2110;

/**
 * Supports the construction of a {@code String} representing a list of items
 * separated by a delimiter, suppressing the delimiter after the last element.
 * Examples:<br>
 * <br>
 * 
 * item1, item2, item3<br>
 * <br>
 * 
 * item1&lt;br&gt;<br>
 * item2&lt;br&gt;<br>
 * item3<br>
 * <br>
 * 
 * @see StringBuilder
 */

public class DelimitedStringBuilder {

        private final StringBuilder sb = new StringBuilder();
        private final String delimiter; // the delimiter--e.g. ", " or "<br>\n"

        /**
         * Constructs a {@code DelimitedStringBuilder} with the given delimiter.
         * 
         * @param delimiter
         *                The delimiter.
         */
        public DelimitedStringBuilder(String delimiter) {
                this.delimiter = delimiter;
        }

        /**
         * Appends the given {@code String} followed by the delimiter.
         * 
         * @param string
         *                The {@code String} to append.
         */
        public void append(String string) {
                sb.append(string);
                sb.append(delimiter);
        }

        /**
         * Resets the length to 0.
         */
        public void reset() {
                sb.setLength(0);
        }

        /**
         * Returns the number of characters appended so far, including
         * delimiters.
         * 
         * @return the number of characters appended so far, including
         *         delimiters
         */

        public int length() {
                return sb.length();
        }

        /**
         * Returns a {@code String} consisting of the appended {@code String}s
         * separated by the delimiter. The final delimiter is suppressed.
         * 
         * @return a {@code String} consisting of the appended {@code String}s
         *         separated by the delimiter
         */
        @Override
        public String toString() {
                if (sb.length() >= delimiter.length())
                        sb.setLength(sb.length() - delimiter.length());
                return sb.toString();
        }

}
