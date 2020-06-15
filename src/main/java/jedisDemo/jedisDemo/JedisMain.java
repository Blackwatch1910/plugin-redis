package jedisDemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
 
public class JedisMain  {
 
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
 
    private static JedisPool pool = null;
 
    public JedisMain(String host, int port) {
        pool = new JedisPool(host, port);
 
    }
 
    @SuppressWarnings("deprecation")
	public void addSets() {
        //let us first add some data in our redis server using Redis SET.
        String key = "members";
        String member1 = "Sedarius";
        String member2 = "Richard";
        String member3 = "Joe";
 
        //get a jedis connection jedis connection pool
        Jedis jedis = pool.getResource();
        try {
            //save to redis
            jedis.sadd(key, member1, member2, member3);
 
            //after saving the data, lets retrieve them to be sure that it has really added in redis
            Set members = jedis.smembers(key);
            for (String member : members) {
                System.out.println(member);
            }
        } catch (JedisException e) {
            //if something wrong happen, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }
 
    public void addHash() {
        //add some values in Redis HASH
        String key = "javapointers";
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Blackwatch");
        map.put("domain", "www.metamug.com");
        map.put("description", "Develop REST APIs efficiently");
 
        Jedis jedis = pool.getResource();
        try {
            //save to redis
            jedis.hmset(key, map);
 
            //after saving the data, lets retrieve them to be sure that it has really added in redis
            Map<String, String> retrieveMap = jedis.hgetAll(key);
            for (String keyMap : retrieveMap.keySet()) {
                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
            }
 
        } catch (JedisException e) {
            //if something wrong happen, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }
    
    public InputStream download(String file) throws JedisException {
        return Jedis.get(file);
    }

    public InputStream download(String file, String dir) throws JedisException {
        Jedis.cd(dir);
        return Jedis.get(file);
    }
 
    public static void main(String[] args){
        JedisMain main = new JedisMain();
        main.addSets();
        main.addHash();
    }
}