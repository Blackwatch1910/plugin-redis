package jedisDemo;

import redis.clients.jedis.Jedis;

public class MyApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connection Successful");
			System.out.println("The server is running " + jedis.ping());
			jedis.set("company-name", "500Rockets.io");
			System.out.println("Stored string in redis: "+ jedis.get("company-name"));



			}catch(Exception e) {
			System.out.println(e);
			}

	}

}
