package edu.cornell.cs.cs2110;

import java.io.InputStream;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Scanner;

/**
 * Provides an interface to a text resource file. The file should reside in a
 * directory accessible from the classpath. Lines in the file are returned as
 * {@code String}s accessed sequentially through the class's {@link Iterator}.
 */
public class TextResourceFile implements Iterable<String> {

        private InputStream is;
        private boolean accessed = false;

        /**
         * Constructs a {@code TextResourceFile} object that will take lines
         * sequentially from the text file with the given name relative to the
         * classpath. If this is going in a {@code .jar} file and you are using
         * Windows, use '/' for the name separator character, not
         * {@link java.io.File#separatorChar}, as '\' doesn't work in
         * {@code .jar} files.
         * 
         * @param filename
         *                the name of the resource file
         * @throws MissingResourceException
         *                 if the resource file is not found or cannot be read
         */
        public TextResourceFile(String filename) {
                is = ClassLoader.getSystemResourceAsStream(filename);
                if (is == null)
                        throw new MissingResourceException(filename, null, null);
        }

        /**
         * {@inheritDoc}
         * 
         * This implementation does not support {@link Iterator#remove()}.
         * 
         * @return an {@link Iterator} that returns the lines of the text file
         *         sequentially
         * @throws IllegalStateException
         *                 if the iterator is accessed more than once
         */
        @Override
        public Iterator<String> iterator() {

                if (accessed)
                        throw new IllegalStateException("Cannot access file iterator more than once");
                accessed = true;

                final Scanner sc = new Scanner(is);

                return new Iterator<String>() {

                        @Override
                        public boolean hasNext() {
                                return sc.hasNextLine();
                        }

                        @Override
                        public String next() {
                                return sc.nextLine();
                        }

                        @Override
                        public void remove() {
                                throw new UnsupportedOperationException();
                        }
                };
        }
}
