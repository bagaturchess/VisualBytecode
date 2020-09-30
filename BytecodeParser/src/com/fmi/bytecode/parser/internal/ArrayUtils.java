package com.fmi.bytecode.parser.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.reflect.Array;
import javax.swing.Action;
import java.util.Comparator;
import java.io.File;

public final class ArrayUtils
{
    public static final int[] INT_ARRAY_0;
    public static final Object[] OBJECT_ARRAY_0;
    public static final String[] STRING_ARRAY_0;
    public static final Class[] CLASS_ARRAY_0;
    public static final File[] FILE_ARRAY_0;
    public static final Class[] CLASS_ARRAY_1_STRING;
    
    static {
        INT_ARRAY_0 = new int[0];
        OBJECT_ARRAY_0 = new Object[0];
        STRING_ARRAY_0 = new String[0];
        CLASS_ARRAY_0 = new Class[0];
        FILE_ARRAY_0 = new File[0];
        CLASS_ARRAY_1_STRING = new Class[] { String.class };
    }
    
    private ArrayUtils() {
    }
    
    /*public static int[] sortByIndices(final int[] a) {
        final IntComparator intComparator = (IntComparator)new ArrayUtils.ArrayUtils$1(a);
        final int[] result = createOmegaArray(a.length);
        sort(result, intComparator);
        return result;
    }
    
    public static int[] sortByIndices(final Comparable[] a) {
        final IntComparator intComparator = (IntComparator)new ArrayUtils.ArrayUtils$2(a);
        final int[] result = createOmegaArray(a.length);
        sort(result, intComparator);
        return result;
    }
    
    public static int[] sortByIndices(final Object[] a, final Comparator comparator) {
        final IntComparator intComparator = (IntComparator)new ArrayUtils.ArrayUtils$3(comparator, a);
        final int[] result = createOmegaArray(a.length);
        sort(result, intComparator);
        return result;
    }*/
    
    private static int[] createOmegaArray(final int n) {
        final int[] r = new int[n];
        for (int i = 0; i < n; ++i) {
            r[i] = i;
        }
        return r;
    }
    
    public static void sort(final int[] a, final IntComparator comparator) {
        sort(a, 0, a.length, comparator);
    }
    
    public static void sort(final int[] a, final int start, final int end, final IntComparator comparator) {
        if (end - start < 8) {
            for (int i = start; i < end; ++i) {
                int k = i;
                int ak = a[k];
                for (int j = i + 1; j < end; ++j) {
                    if (comparator.compare(a[j], ak) < 0) {
                        k = j;
                        ak = a[k];
                    }
                }
                if (k != i) {
                    a[k] = a[i];
                    a[i] = ak;
                }
            }
        }
        else {
            int axisIndex = (start + end) / 2;
            final int length = end - start;
            if (length > 16) {
                if (length > 32) {
                    final int delta = length / 9;
                    axisIndex = medianOf3(a, medianOf3(a, start, start + delta, start + delta + delta, comparator), medianOf3(a, end - 1, end - 1 - delta, end - 1 - delta - delta, comparator), medianOf3(a, axisIndex, axisIndex + delta, axisIndex - delta, comparator), comparator);
                }
                else {
                    axisIndex = medianOf3(a, start, end - 1, axisIndex, comparator);
                }
            }
            final int axisValue = a[axisIndex];
            int left = start;
            int right = end - 1;
            a[axisIndex] = a[left];
            boolean isAxisLeft = true;
        Label_0333:
            while (true) {
                if (isAxisLeft) {
                    while (comparator.compare(axisValue, a[right]) < 0) {
                        --right;
                        if (left >= right) {
                            break Label_0333;
                        }
                    }
                    a[left] = a[right];
                    isAxisLeft = false;
                }
                else {
                    while (comparator.compare(a[left], axisValue) <= 0) {
                        if (++left >= right) {
                            break Label_0333;
                        }
                    }
                    a[right] = a[left];
                    isAxisLeft = true;
                }
            }
            a[left] = axisValue;
            if (left > start) {
                sort(a, start, left, comparator);
            }
            if (left + 1 < end) {
                sort(a, left + 1, end, comparator);
            }
        }
    }
    
    private static int medianOf3(final int[] x, final int a, final int b, final int c, final IntComparator comparator) {
        final int xa = x[a];
        final int xb = x[b];
        final int xc = x[c];
        final boolean rab = comparator.compare(xa, xb) < 0;
        final boolean rbc = comparator.compare(xb, xc) < 0;
        if (rab == rbc) {
            return b;
        }
        final boolean rac = comparator.compare(xa, xc) < 0;
        if (rac == rab) {
            return c;
        }
        return a;
    }
    
    public static int[] resize(final int[] a) {
        final int[] r = new int[2 * a.length];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static int[][] resize(final int[][] a) {
        final int[][] r = new int[2 * a.length][];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static Class[] resize(final Class[] a) {
        final Class[] r = new Class[2 * a.length];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static ClassLoader[] resize(final ClassLoader[] a) {
        final ClassLoader[] r = new ClassLoader[2 * a.length];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static String[] resize(final String[] a) {
        final String[] r = new String[2 * a.length];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static String[][] resize(final String[][] a) {
        final String[][] r = new String[2 * a.length][];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static boolean[][] resize(final boolean[][] a) {
        final boolean[][] r = new boolean[2 * a.length][];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static byte[][] resize(final byte[][] a) {
        final byte[][] r = new byte[2 * a.length][];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static Action[][] resize(final Action[][] a) {
        final Action[][] r = new Action[2 * a.length][];
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static Object resize(final Object[] a) {
        final Object r = Array.newInstance(a.getClass().getComponentType(), 2 * a.length);
        System.arraycopy(a, 0, r, 0, a.length);
        return r;
    }
    
    public static URL findOnClasspath(final File[] classpath, final String name) {
        if (classpath == null || name == null) {
            return null;
        }
        for (int nClasspath = classpath.length, i = 0; i < nClasspath; ++i) {
            final File file = new File(classpath[i], name);
            if (file.exists()) {
                try {
                    return file.toURL();
                }
                catch (MalformedURLException mue) {
                    return null;
                }
            }
        }
        return null;
    }
    
    public static byte[] readFully(final InputStream in) throws IOException {
        return readFully(in, null);
    }
    
    public static byte[] readFully(final InputStream in, final byte[] initialBuffer) throws IOException {
        byte[] buffer = (initialBuffer == null) ? new byte[2048] : initialBuffer;
        int nBuffer = 0;
        while (true) {
            final int k = in.read(buffer, nBuffer, buffer.length - nBuffer);
            if (k == -1) {
                break;
            }
            nBuffer += k;
            if (buffer.length != nBuffer) {
                continue;
            }
            final byte[] bufferOld = buffer;
            buffer = new byte[nBuffer * 2 + 8];
            System.arraycopy(bufferOld, 0, buffer, 0, nBuffer);
        }
        final byte[] r = new byte[nBuffer];
        System.arraycopy(buffer, 0, r, 0, nBuffer);
        return r;
    }
    
    public static String toString(final Object[] objects) {
        if (objects == null) {
            return String.valueOf(null);
        }
        if (objects.length == 0) {
            return "[]";
        }
        final StringBuffer buffer = new StringBuffer();
        buffer.append('[').append(objects[0]);
        for (int i = 1; i < objects.length; ++i) {
            buffer.append(',').append(objects[i]);
        }
        buffer.append(']');
        return buffer.toString();
    }
    
    public static String toString(final int[] ints) {
        if (ints == null) {
            return String.valueOf(null);
        }
        final Object[] objects = new Object[ints.length];
        for (int i = 0; i < ints.length; ++i) {
            objects[i] = Integer.toString(ints[i]);
        }
        return toString(objects);
    }
}