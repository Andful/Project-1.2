package org.lwjglb.Util;

import org.joml.Vector3i;

/**
 * Created by Andrea Nardi on 5/6/2017.
 */
public class Array3D<E>
{
    private final E[][][] memory;

    public Array3D(Vector3i size)
    {
        memory=(E[][][]) new Object[size.x][size.y][size.z];
    }
    public void set(Vector3i pos,E element)
    {
        memory[pos.x][pos.y][pos.z]=element;
    }
    public E get(Vector3i pos)
    {
        return memory[pos.x][pos.y][pos.z];
    }
    public boolean isInBound(Vector3i pos)
    {
        return(pos.x>=0 && pos.y>=0 && pos.z>=0 && pos.x<memory.length && pos.y<memory[0].length && pos.z<memory[0][0].length);
    }

    public static void main(String[] args)
    {
        Array3D<Integer> a= new Array3D<>(new Vector3i(5,5,5));
        a.set(new Vector3i(),5);
        System.out.println(a.get(new Vector3i()));
    }
}
