import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisClusterTest {

    @Test
    public void fun1() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");

        JedisPool jedisPool = ac.getBean(JedisPool.class);
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get("java");
        System.out.println(s);
        jedis.close();
        jedisPool.close();

    }

    @Test
    public void fun2() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisCluster jedisCluster = ac.getBean(JedisCluster.class);
        String s = jedisCluster.hget("e3mall-index", "89");
        System.out.println(s);
    }
}
