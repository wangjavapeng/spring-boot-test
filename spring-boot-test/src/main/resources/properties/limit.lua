-- 限流脚本,具有原子性
local key = KEYS[1]	--限流key
local limit_count = tonumber(ARGV[1])	--每次限流数量
local expire = tonumber(ARGV[2])	--限流key的过期时间
local current_count = tonumber(redis.call('get', key) or '0')	--当前限流大小

if current_count + 1 > limit_count then
	return 0
else
	redis.call("INCRBY", key,"1")
    redis.call("expire", key,expire)
    return 1
end