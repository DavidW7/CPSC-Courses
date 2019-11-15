using System;
using System.Collections.Generic;
using System.Linq;

namespace ConsoleApp1
{
    class Program
    {
        
        static List<int> beginlist = new List<int>() { 1, 2, 3, //0 1 2
            4, 5, 6, //3 4 5
            7, 8, 9 }; //6 7 8
        static List<int> endlist = new List<int>() { 5, 1, 3, 4, 2, 6, 7, 8, 9 };

        static void Main(string[] args)
        {
            int iteration = 11;
            
            List<int> solved = iterate(new List<int>(), iteration, copy(beginlist));
            if (solved == null)
            {
                Console.WriteLine("add iteration pls, currently at: " + iteration);
            } else
            {
                Console.WriteLine("success: ");
                foreach (int i in solved)
                {
                    Console.WriteLine(i);
                }
            }
            do
            {
                while (!Console.KeyAvailable)
                {
                    // Do something
                }
            } while (Console.ReadKey(true).Key != ConsoleKey.Escape);
        }

        private static List<int> iterate(List<int> step, int iteration, List<int> rotation)
        {
            //Console.WriteLine("iteration: " + iteration);
            /*
            Console.Write("failed: ");
            foreach (int i in step)
            {
                Console.Write(i);
            }
            Console.Write(";");
            foreach (int i in rotation)
            {
                Console.Write(i);
            }
            Console.WriteLine(";");
            */
            if (rotation == endlist)
            {
                for (int i = 0; i < rotation.Count; i++)
                {
                    Console.WriteLine("comparison: new " + rotation[i] + ", result " + endlist[i]);
                }
                return step;
            }
            else
            {
                if (iteration < 1)
                {
                    return null;
                }
                else
                {
                    // Console.WriteLine("step size: " + step.Count);
                    List<int> newstep;
                    newstep = copy(step);
                    newstep.Add(0);
                    List<int> r0 = iterate(newstep, iteration - 1, rotatec0(rotation));
                    if (r0 != null)
                    {
                        return r0;
                    } else
                    {
                        r0 = null;
                    }
                    newstep = copy(step);
                    newstep.Add(1);
                    List<int> r1 = iterate(newstep, iteration - 1, rotatec1(rotation));
                    if (r1 != null)
                    {
                        return r1;
                    }
                    else
                    {
                        r1 = null;
                    }
                    newstep = copy(step);
                    newstep.Add(2);
                    List<int> r2 = iterate(newstep, iteration - 1, rotatec2(rotation));
                    if (r2 != null)
                    {
                        return r2;
                    }
                    else
                    {
                        r2 = null;
                    }
                    newstep = copy(step);
                    newstep.Add(3);
                    List<int> r3 = iterate(newstep, iteration - 1, rotater0(rotation));
                    if (r3 != null)
                    {
                        return r3;
                    }
                    else
                    {
                        r3 = null;
                    }
                    newstep = copy(step);
                    newstep.Add(4);
                    List<int> r4 = iterate(newstep, iteration - 1, rotater1(rotation));
                    if (r4 != null)
                    {
                        return r4;
                    }
                    else
                    {
                        r4 = null;
                    }
                    newstep = copy(step);
                    newstep.Add(5);
                    List<int> r5 = iterate(newstep, iteration - 1, rotater2(rotation));
                    return r5;

                    /*List<int> newstep;
                    if (justdid != 0)
                    {
                        newstep = copy(step);
                        newstep.Add(0);
                        List<int> r0 = iterate(newstep, 0, iteration - 1, rotatec0(rotation));
                        if (r0 != null)
                        {
                            return r0;
                        }
                    } 
                    if (justdid != 1)
                    {
                        newstep = copy(step);
                        newstep.Add(1);
                        List<int> r1 = iterate(newstep, 1, iteration - 1, rotatec1(rotation));
                        if (r1 != null)
                        {
                            return r1;
                        }
                    }
                    if (justdid != 2)
                    {
                        newstep = copy(step);
                        newstep.Add(2);
                        List<int> r2 = iterate(newstep, 2, iteration - 1, rotatec2(rotation));
                        if (r2 != null)
                        {
                            return r2;
                        }
                    }
                    if (justdid != 3)
                    {
                        newstep = copy(step);
                        newstep.Add(3);
                        List<int> r3 = iterate(newstep, 3, iteration - 1, rotater0(rotation));
                        if (r3 != null)
                        {
                            return r3;
                        }
                    }
                    if (justdid != 4)
                    {
                        newstep = copy(step);
                        newstep.Add(4);
                        List<int> r4 = iterate(newstep, 4, iteration - 1, rotater1(rotation));
                        if (r4 != null)
                        {
                            return r4;
                        }
                    }
                    if (justdid != 5)
                    {
                        newstep = copy(step);
                        newstep.Add(5);
                        List<int> r5 = iterate(newstep, 5, iteration - 1, rotater2(rotation));
                        return r5;
                    }
                    return null;*/
                }
            }
        }

        private static List<int> rotater2(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[6];
            int t1 = temp[7];
            int t2 = temp[8];
            temp[8] = t1;
            temp[7] = t0;
            temp[6] = t2;
            return temp;
        }

        private static List<int> rotater1(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[3];
            int t1 = temp[4];
            int t2 = temp[5];
            temp[5] = t1;
            temp[4] = t0;
            temp[3] = t2;
            return temp;
        }

        private static List<int> rotater0(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[0];
            int t1 = temp[1];
            int t2 = temp[2]; 
            temp[2] = t1;
            temp[1] = t0;
            temp[0] = t2;
            return temp;
        }

        private static List<int> rotatec2(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[0];
            int t1 = temp[3];
            int t2 = temp[6];
            temp[6] = t1;
            temp[3] = t0;
            temp[0] = t2;
            return temp;
        }

        private static List<int> rotatec1(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[1];
            int t1 = temp[4];
            int t2 = temp[7];
            temp[7] = t1;
            temp[4] = t0;
            temp[1] = t2;
            return temp;
        }

        private static List<int> rotatec0(List<int> rotation)
        {
            List<int> temp = copy(rotation);
            int t0 = temp[2];
            int t1 = temp[5];
            int t2 = temp[8];
            temp[8] = t1;
            temp[5] = t0;
            temp[2] = t2;
            return temp; 
        }

        private static List<int> copy(List<int> old)
        {
            //Console.WriteLine(old.Count);
            return old.Select(i => i).ToList();
        }

    }
}
