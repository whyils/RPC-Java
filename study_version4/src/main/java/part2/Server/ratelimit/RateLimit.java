package part2.Server.ratelimit;


/**
 * @author WYL
 * @date 2025/2/25 15:24
 * @description: TODO
 */
public interface RateLimit {

    // 获取访问令牌
    boolean getToken();

}
