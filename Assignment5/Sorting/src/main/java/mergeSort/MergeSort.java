package mergeSort;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MergeSort {
  /**
   * Thread that declares the lambda and then initiates the work
   */

  public static int message_id = 0;

  public static JSONObject init(int[] array) {
    JSONArray arr = new JSONArray();
    for (var i : array) {
      arr.put(i);
    }
    JSONObject req = new JSONObject();
    req.put("method", "init");
    req.put("data", arr);
    return req;
  }

  public static JSONObject peek() {
    JSONObject req = new JSONObject();
    req.put("method", "peek");
    return req;
  }

  public static JSONObject remove() {
    JSONObject req = new JSONObject();
    req.put("method", "remove");
    return req;
  }
  
  public static void Test(int port, String host) {
    int[] a = { 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};//{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};//{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};//{1, 2, 3};//{ 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;
 
      } else{
        break;
      }
    }
  }
  public static void Test1(int port, String host) {
    int[] a = {1, 2, 3};//{ 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;
 
      } else{
        break;
      }
    }
  }
  public static void Test2(int port, String host) {
    int[] a = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};//{1, 2, 3};//{ 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;
 
      } else{
        break;
      }
    }
  }
  public static void Test3(int port, String host) {
    int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};//{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};//{1, 2, 3};//{ 5, 1, 6, 2, 3, 4, 10,634,34,23,653, 23,2 ,6};
    JSONObject response = NetworkUtils.send(host, port, init(a));
    
    System.out.println(response);
    response = NetworkUtils.send(host, port, peek());
    System.out.println(response);

    while (true) {
      response = NetworkUtils.send(host, port, remove());

      if (response.getBoolean("hasValue")) {
        System.out.println(response);;
 
      } else{
        break;
      }
    }
  }

  public static void main(String[] args) {
    Date date = new Date();
    long totalTime1 = 0;
    long totalTime2 = 0;
    long totalTime3 = 0;
    long totalTime4 = 0;
    for(int i = 0; i < 10; i++)
    {
      long time1 = System.currentTimeMillis();
      // use the port of one of the branches to test things
      Test(Integer.valueOf(args[0]), args[1]);
      long time2 = System.currentTimeMillis();
      System.out.println("Time: " + (time2 - time1));
      totalTime1 += (time2 - time1);
    }
    for(int i = 0; i < 10; i++)
    {
      long time1 = System.currentTimeMillis();
      // use the port of one of the branches to test things
      Test1(Integer.valueOf(args[0]), args[1]);
      long time2 = System.currentTimeMillis();
      System.out.println("Time: " + (time2 - time1));
      totalTime2 += (time2 - time1);
    }
    for(int i = 0; i < 10; i++)
    {
      long time1 = System.currentTimeMillis();
      // use the port of one of the branches to test things
      Test2(Integer.valueOf(args[0]), args[1]);
      long time2 = System.currentTimeMillis();
      System.out.println("Time: " + (time2 - time1));
      totalTime3 += (time2 - time1);
    }
    for(int i = 0; i < 10; i++)
    {
      long time1 = System.currentTimeMillis();
      // use the port of one of the branches to test things
      Test3(Integer.valueOf(args[0]), args[1]);
      long time2 = System.currentTimeMillis();
      System.out.println("Time: " + (time2 - time1));
      totalTime4 += (time2 - time1);
    }
    System.out.println("Total time: " + totalTime1);
    System.out.println("Total time: " + totalTime2);
    System.out.println("Total time: " + totalTime3);
    System.out.println("Total time: " + totalTime4);
  }
}
