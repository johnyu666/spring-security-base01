package cn.johnyu.cache;

import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.Map;

public class UserMemCache {
    private static Map<String, User> loginedUsers=new HashMap<>();

    public static void addUser(String token,User user){
        loginedUsers.put(token,user);
    }
    public static void deleteUser(String token){
        loginedUsers.remove(token);
    }
    public static User findUser(String token){
        return  loginedUsers.get(token);
    }
}
