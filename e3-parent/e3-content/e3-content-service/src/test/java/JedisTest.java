import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    @Test
    public void fun1() throws Exception {
        Jedis jedis = new Jedis("192.168.25.128", 6379);
        jedis.set("java", "12k");
        String s = jedis.get("java");
        System.out.println(s);
        jedis.close();
    }

    @Test
    public void fun2() throws Exception {
        JedisPool pool = new JedisPool("192.168.25.128", 6379);
        Jedis jedis = pool.getResource();
        jedis.set("110", "521");
        String s = jedis.get("110");
        System.out.println(s);
        jedis.close();
        pool.close();
    }

    @Test
    public void fun3() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.128", 7001));
        nodes.add(new HostAndPort("192.168.25.128", 7002));
        nodes.add(new HostAndPort("192.168.25.128", 7003));
        nodes.add(new HostAndPort("192.168.25.128", 7004));
        nodes.add(new HostAndPort("192.168.25.128", 7005));
        nodes.add(new HostAndPort("192.168.25.128", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("hello", "world");
        String s = jedisCluster.get("hello");
        System.out.println(s);
        jedisCluster.close();
    }
}
