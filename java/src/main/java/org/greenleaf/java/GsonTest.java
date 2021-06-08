package org.greenleaf.java;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class GsonTest {

    @SerializedName("name")
    String a;
    @SerializedName(value = "message", alternate = {"msg"})
    String b;

    public GsonTest(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }

    public static void main(String[] args) {
        GsonTest target = new GsonTest("v1", "v2");
        Gson gson = new Gson();
        System.out.println(gson.toJson(target));

        target = gson.fromJson("{'name':'v1', 'message':'2222'}", GsonTest.class);
        System.out.println(target);
        System.out.println(gson.toJson(target));

        target = gson.fromJson("{'name':'v2', 'msg':'2222'}", GsonTest.class);
        System.out.println(target);
        System.out.println(gson.toJson(target));

        target = gson.fromJson("{'name':'v3', 'message':'2222', 'msg':'221122'}", GsonTest.class);
        System.out.println(target);
        System.out.println(gson.toJson(target));

        target = gson.fromJson("{'name':'v4', 'msg':'2222', 'message':'222222'}", GsonTest.class);
        System.out.println(target);
        System.out.println(gson.toJson(target));
    }
}